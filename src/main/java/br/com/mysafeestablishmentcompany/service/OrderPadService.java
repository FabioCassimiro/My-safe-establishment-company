package br.com.mysafeestablishmentcompany.service;


import br.com.mysafeestablishmentcompany.api.request.*;
import br.com.mysafeestablishmentcompany.api.response.CloseOrderPadResponse;
import br.com.mysafeestablishmentcompany.domain.*;
import br.com.mysafeestablishmentcompany.exception.CustomerNotFoundException;
import br.com.mysafeestablishmentcompany.repository.*;
import br.com.mysafeestablishmentcompany.utils.MyEstablishmentUtils;
import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderPadService {
    private final Logger logger = LoggerFactory.getLogger(OrderPadService.class);

    private final OrderPadRepository orderPadRepository;
    private final TableEstablishmentRepository tableEstablishmentRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Autowired
    public OrderPadService(OrderPadRepository orderPadRepository,
                           TableEstablishmentRepository tableEstablishmentRepository,
                           CustomerRepository customerRepository,
                           OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.orderPadRepository = orderPadRepository;
        this.tableEstablishmentRepository = tableEstablishmentRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    public OrderPad createOrderPad(CreateOrderPadRequest createOrderPadRequest) throws Exception {
        Customer customer = findCustomer(createOrderPadRequest.getCustomerId());
        return saveOrderPad(createOrderPadRequest, customer);
    }

    private Customer findCustomer(long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId);
        if (Objects.isNull(customer)) {
            throw new CustomerNotFoundException("Customer não encontrado");
        }
        return customer;
    }

    private OrderPad saveOrderPad(CreateOrderPadRequest createOrderPadRequest, Customer customer) throws Exception {
        OrderPad orderPad = new OrderPad();
        List<OrderPad> orderPadsDTO = orderPadRepository.findByCustomerId(customer.getId());
        if (!orderPadsDTO.isEmpty()) {
            List<OrderPad> orderPadList = orderPadsDTO.stream()
                    .filter(orderPads -> !Objects.equals(orderPads.getStatus(), CompanyUtils.ORDERPAD_STATUS_PAID))
                    .collect(Collectors.toList());
            if (!orderPadList.isEmpty()) {
                return orderPadList.get(0);
            }
        }
        orderPad.setCustomerId(customer.getId());
        orderPad.setCustomerName(customer.getName());
        orderPad.setStatus(CompanyUtils.ORDERPAD_STATUS_OPEN);
        if (createOrderPadRequest.getQuantityCustomer() <= 0) {
            throw new Exception("Numero de customers deve ser maior que 0");
        }
        orderPad.setQuantityCustomer(createOrderPadRequest.getQuantityCustomer());
        reservationTable(createOrderPadRequest.getTableId());
        orderPad.setTableId(createOrderPadRequest.getTableId());
        return orderPadRepository.save(orderPad);
    }

    private void reservationTable(long tableId) throws Exception {
        TableEstablishment tableEstablishment = tableEstablishmentRepository.findByIdAndStatusTable(tableId, CompanyUtils.TABLE_STATUS_AVALIABLE);
        if (Objects.isNull(tableEstablishment)) {
            throw new Exception("Table: " + tableId + "Não disponivel");
        }
        tableEstablishment.setStatusTable(CompanyUtils.TABLE_STATUS_NOT_AVAILABLE);
        tableEstablishmentRepository.save(tableEstablishment);
    }

    public CloseOrderPadResponse closeOrderPad(CloseOrderPadRequest closeOrderPadRequest) throws Exception {
        Customer customer = findCustomer(closeOrderPadRequest.getCustomerId());
        OrderPad orderPad = getOrderPad(closeOrderPadRequest);
        ArrayList<Order> orders = orderRepository.findByOrderPadId(orderPad.getId());
        if (orders.stream().anyMatch(order -> !order.getStatus().equals("3"))) {
            throw new Exception("Ainda ha pedido(s) não entregues");
        }
        orderPad = saveClosureOrderPad(closeOrderPadRequest, orderPad);

        return new CloseOrderPadResponse(orderPad, orders);
    }

    private OrderPad saveClosureOrderPad(CloseOrderPadRequest closeOrderPadRequest, OrderPad orderPad) throws Exception {
        orderPad.setTip(closeOrderPadRequest.getTip());
        orderPad.setStatus(CompanyUtils.ORDERPAD_STATUS_AWAITING_PAYMENT);
        orderPad.setRate(Precision.round(orderPad.getValue() * CompanyUtils.TAX_RATE, 2));
        double orderPadTotalValue = Precision.round(orderPad.getValue() + orderPad.getRate() + orderPad.getTip(), 2);
        orderPad.setValue(orderPadTotalValue);
        orderPad.setPaybleValue(orderPadTotalValue);
        return orderPadRepository.save(orderPad);
    }

    private OrderPad getOrderPad(CloseOrderPadRequest closeOrderPadRequest) throws Exception {
        final OrderPad orderPad = orderPadRepository.findByCustomerIdAndStatus(closeOrderPadRequest.getCustomerId(), CompanyUtils.ORDERPAD_STATUS_OPEN);
        if (Objects.isNull(orderPad)) {
            throw new Exception("OrderPad não encontada ou não esta aberta");
        }
        return orderPad;
    }

    public OrderPad closeManualPaymentOrderPad(Long orderpadId ,Long customerId) throws Exception {
        Customer customer = findCustomer(customerId);
        OrderPad orderPad = findOrderPadAwaitingPayment(customer.getId());
        if (Objects.equals(orderPad.getId(), orderpadId)){
            orderPad.setStatus(CompanyUtils.ORDERPAD_STATUS_AWAITING_MANUAL_PAYMENT);
            return orderPadRepository.save(orderPad);
        }
        throw new Exception("Erro de pagamento manual");
    }

    public OrderPad manualPayment(PaymentOrderPadByManualRequest paymentManualRequest) throws Exception {
        Customer customer = findCustomer(paymentManualRequest.getCustomerId());
        OrderPad orderPad = orderPadRepository.findByCustomerIdAndStatus(paymentManualRequest.getCustomerId(), CompanyUtils.ORDERPAD_STATUS_AWAITING_MANUAL_PAYMENT);
        if (Objects.isNull(orderPad)) {
            throw new Exception("Customer não tem OrderPad Aguardando Pagamento Manual");
        }
        if (CompanyUtils.paymentMethods.stream().noneMatch(payment -> payment.equals(paymentManualRequest.getPaymentMethod()))){
            throw new Exception("Metodo de pagamento Invalido");
        }
        orderPad = savePaymentOrderPad(orderPad, paymentManualRequest.getValuePayment());
        Payment payment = new Payment(
                orderPad.getId(),
                paymentManualRequest.getPaymentMethod(),
                "####",
                paymentManualRequest.getValuePayment(),
                "PAGAMENTO MANUAL"
        );
        paymentRepository.save(payment);
        return orderPad;

    }

    private OrderPad findOrderPadAwaitingPayment(long cutomerId) throws Exception {
        OrderPad orderPad = orderPadRepository.findByCustomerIdAndStatus(cutomerId, CompanyUtils.ORDERPAD_STATUS_AWAITING_PAYMENT);
        if (Objects.isNull(orderPad)) {
            throw new Exception("Customer não tem OrderPad Aguardando Pagamento");
        }
        return orderPad;
    }

    private void removeResenvationTable(long tableId) {
        TableEstablishment tableEstablishment = tableEstablishmentRepository.findByIdAndStatusTable(tableId, CompanyUtils.TABLE_STATUS_NOT_AVAILABLE);
        tableEstablishment.setStatusTable(CompanyUtils.TABLE_STATUS_AVALIABLE);
        tableEstablishmentRepository.save(tableEstablishment);
    }

    public OrderPad paymentOrderPadbyCard(PaymentOrderPadByCardRequest paymentCardRequest) throws Exception {
        Customer customer = findCustomer(paymentCardRequest.getCustomerId());
        OrderPad orderPad = findOrderPadAwaitingPayment(customer.getId());
        MyEstablishmentUtils.validateCardNumber(paymentCardRequest.getCard().getCardNumber());
        orderPad = savePaymentOrderPad(orderPad, paymentCardRequest.getValuePayment());
        Payment payment = new Payment(
                orderPad.getId(),
                paymentCardRequest.getTypeCard(),
                paymentCardRequest.getCard().getCardNumber().substring(12),
                paymentCardRequest.getValuePayment(),
                "PAGO VIA APLICAÇÃO"
        );
        paymentRepository.save(payment);
        return orderPad;
    }

    private OrderPad savePaymentOrderPad(OrderPad orderPad, double valuePayment) throws Exception {
        if (valuePayment <= 0 || valuePayment > orderPad.getPaybleValue()) {
            throw new Exception("Valor pago invalido");
        }
        orderPad.setPaybleValue(Precision.round(orderPad.getPaybleValue() - valuePayment, 2));
        if (orderPad.getPaybleValue() == 0) {
            orderPad.setStatus(CompanyUtils.ORDERPAD_STATUS_PAID);
            removeResenvationTable(orderPad.getTableId());
        }
        return orderPadRepository.save(orderPad);
    }

    public List<OrderPad> orderpadsByStatus(String status){
        return orderPadRepository.findAllByStatus(status);
    }

    public List<OrderPad> orderpads(){
        return (List<OrderPad>) orderPadRepository.findAll();
    }

    public OrderPad orderpadsById(Long orderpadId){
        return orderPadRepository.findOrderPadById(orderpadId);
    }

    public OrderPad orderPadByCustomerId(long customerId) throws Exception {
        List<OrderPad> orderPadsDTO = orderPadRepository.findByCustomerId(customerId);
        if (!orderPadsDTO.isEmpty()) {
            List<OrderPad> orderPadList = orderPadsDTO.stream()
                    .filter(orderPads -> !Objects.equals(orderPads.getStatus(), CompanyUtils.ORDERPAD_STATUS_PAID))
                    .collect(Collectors.toList());
            if (!orderPadList.isEmpty()) {
                return orderPadList.get(0);
            }
        }
        throw new Exception();
    }

    public OrderPad orderPadByIdAndCustomerId(long orderPadId, long customerId) throws Exception {
        OrderPad orderPad = orderPadRepository.findOrderPadByIdAndCustomerId(orderPadId, customerId);
        if (Objects.isNull(orderPad)){
            throw new Exception("OrderPad Não encontrada");
        }
        return orderPad;
    }

}

