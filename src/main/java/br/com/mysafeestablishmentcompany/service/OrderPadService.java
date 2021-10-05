package br.com.mysafeestablishmentcompany.service;


import br.com.mysafeestablishmentcompany.api.request.CloseOrderPadRequest;
import br.com.mysafeestablishmentcompany.api.request.CreateOrderPadRequest;
import br.com.mysafeestablishmentcompany.api.request.PaymentOrderPadRequest;
import br.com.mysafeestablishmentcompany.api.response.CloseOrderPadResponse;
import br.com.mysafeestablishmentcompany.api.response.CreateOrderPadResponse;
import br.com.mysafeestablishmentcompany.api.response.PaymentOrderPadResponse;
import br.com.mysafeestablishmentcompany.domain.CompanyUtils;
import br.com.mysafeestablishmentcompany.domain.Customer;
import br.com.mysafeestablishmentcompany.domain.OrderPad;
import br.com.mysafeestablishmentcompany.domain.TableEstablishment;
import br.com.mysafeestablishmentcompany.exception.CustomerNotFoundException;
import br.com.mysafeestablishmentcompany.repository.CustomerRepository;
import br.com.mysafeestablishmentcompany.repository.OrderPadRepository;
import br.com.mysafeestablishmentcompany.repository.TableEstablishmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class OrderPadService {
    private final Logger logger = LoggerFactory.getLogger(OrderPadService.class);

    private final OrderPadRepository orderPadRepository;
    private final TableEstablishmentRepository tableEstablishmentRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public OrderPadService(OrderPadRepository orderPadRepository, TableEstablishmentRepository tableEstablishmentRepository, CustomerRepository customerRepository) {
        this.orderPadRepository = orderPadRepository;
        this.tableEstablishmentRepository = tableEstablishmentRepository;
        this.customerRepository = customerRepository;
    }

    public CreateOrderPadResponse createOrderPad(CreateOrderPadRequest createOrderPadRequest) {
        try {
            Customer customer = findCustomer(createOrderPadRequest.getCustomerId());
            return saveOrderPad(createOrderPadRequest, customer);
        } catch (Exception e) {
            return new CreateOrderPadResponse(e.getMessage());
        }
    }

    private Customer findCustomer(long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId);
        if (Objects.isNull(customer)) {
            throw new CustomerNotFoundException("Customer não encontrado");
        }
        return customer;
    }

    private CreateOrderPadResponse saveOrderPad(CreateOrderPadRequest createOrderPadRequest, Customer customer) throws Exception {
        OrderPad orderPad = new OrderPad();
        OrderPad orderPadDTO = orderPadRepository.findByCustomerIdAndStatus(customer.getId(), CompanyUtils.OPEN);
        if (!Objects.isNull(orderPadDTO)) {
            throw new Exception("Customer já tem OrderPad em aberto");
        }
        orderPad.setCustomerId(customer.getId());
        orderPad.setCustomerName(customer.getName());
        orderPad.setStatus(CompanyUtils.OPEN);
        validateQuantityCustomers(createOrderPadRequest);
        orderPad.setQuantityCustomer(createOrderPadRequest.getQuantityCustomer());
        reservationTable(createOrderPadRequest.getTableId());
        orderPad.setTableId(createOrderPadRequest.getTableId());
        return new CreateOrderPadResponse(orderPadRepository.save(orderPad));
    }

    private void validateQuantityCustomers(CreateOrderPadRequest createOrderPadRequest) throws Exception {
        if (createOrderPadRequest.getQuantityCustomer() <= 0) {
            throw new Exception("Numero de customers deve ser maior que 0");
        }
    }

    public void reservationTable(long tableId) throws Exception {
        TableEstablishment tableEstablishment = getTableEstablishment(tableId);
        tableEstablishment.setStatusTable(CompanyUtils.TABLE_NOT_AVAILABLE_STATUS);
        tableEstablishmentRepository.save(tableEstablishment);
    }

    private TableEstablishment getTableEstablishment(long tableId) throws Exception {
        TableEstablishment tableEstablishment = tableEstablishmentRepository.findByIdAndAndStatusTable(tableId, CompanyUtils.TABLE_AVALIABLE_STATUS);
        if (Objects.isNull(tableEstablishment)) {
            throw new Exception("Table: " + tableId + "Não disponivel");
        }
        return tableEstablishment;
    }

    public CloseOrderPadResponse closeOrderPad(CloseOrderPadRequest closeOrderPadRequest) {

        try {
            Customer customer = findCustomer(closeOrderPadRequest.getCustomerId());
            final OrderPad orderPad = getOrderPad(closeOrderPadRequest);
            return saveClosureOrderPad(closeOrderPadRequest, orderPad);
        } catch (Exception e) {
            return new CloseOrderPadResponse(e.getMessage());
        }

    }

    private CloseOrderPadResponse saveClosureOrderPad(CloseOrderPadRequest closeOrderPadRequest, OrderPad orderPad) throws Exception {
        orderPad.setPayment(validatePaymentOption(closeOrderPadRequest.getPayment()));
        orderPad.setTip(closeOrderPadRequest.getTip());
        orderPad.setStatus(CompanyUtils.AWAITING_PAYMENT);
        calculateRate(orderPad);
        calculateOrdedPad(orderPad);
        return new CloseOrderPadResponse(orderPadRepository.save(orderPad));
    }

    private OrderPad getOrderPad(CloseOrderPadRequest closeOrderPadRequest) throws Exception {
        final OrderPad orderPad = orderPadRepository.findByCustomerIdAndStatus(closeOrderPadRequest.getCustomerId(), CompanyUtils.OPEN);
        if (Objects.isNull(orderPad)) {
            throw new Exception("OrderPad não encontada ou não esta aberta");
        }
        return orderPad;
    }

    private String validatePaymentOption(String paymentOption) throws Exception {
        if (paymentOption.equals(CompanyUtils.CARTAO_DEBITO) || paymentOption.equals(CompanyUtils.CARTAO_CREDITO) ||
                paymentOption.equals(CompanyUtils.DINHEIRO)) {
            return paymentOption;
        }
        throw new Exception("Opção de pagamento invalida");
    }

    public void calculateRate(OrderPad orderPad) {
        orderPad.setRate(orderPad.getValue() * CompanyUtils.TAX_RATE);
    }

    public void calculateOrdedPad(OrderPad orderPad) {
        orderPad.setValue(orderPad.getValue() + orderPad.getRate() + orderPad.getTip());
    }

    public PaymentOrderPadResponse paymentOrderPad(PaymentOrderPadRequest paymentOrderPadRequest) {
        try {
            Customer customer = findCustomer(paymentOrderPadRequest.getCustomerId());
            OrderPad orderPad = findOrderPadAwaitingPayment(customer.getId());
            return savePaymentOrderPad(orderPad, paymentOrderPadRequest);
        } catch (Exception e) {
            return new PaymentOrderPadResponse(e.getMessage());
        }
    }

    private OrderPad findOrderPadAwaitingPayment(long cutomerId) throws Exception {
        OrderPad orderPad = orderPadRepository.findByCustomerIdAndStatus(cutomerId, CompanyUtils.AWAITING_PAYMENT);
        if (Objects.isNull(orderPad)) {
            throw new Exception("Customer não tem OrderPad Aguardando Pagamento");
        }
        return orderPad;
    }

    private PaymentOrderPadResponse savePaymentOrderPad(OrderPad orderPad, PaymentOrderPadRequest paymentOrderPadRequest) throws Exception {
        validateValuePayment(orderPad.getValue(), paymentOrderPadRequest.getValuePayment());
        orderPad.setStatus(CompanyUtils.PAID);
        removeResenvationTable(orderPad.getTableId());
        return new PaymentOrderPadResponse(orderPadRepository.save(orderPad));
    }

    private void validateValuePayment(double orderPadValue, double valuePayment) throws Exception {
        if (orderPadValue != valuePayment) {
            throw new Exception("Valor pago menor que o valor da comanda - Valor comanda: R$ " + orderPadValue);
        }
    }

    public void removeResenvationTable(long tableId) {
        TableEstablishment tableEstablishment = tableEstablishmentRepository.findByIdAndAndStatusTable(tableId, CompanyUtils.TABLE_NOT_AVAILABLE_STATUS);
        tableEstablishment.setStatusTable(CompanyUtils.TABLE_AVALIABLE_STATUS);
        tableEstablishmentRepository.save(tableEstablishment);
    }

}

