package br.com.mysafeestablishmentcompany.service;


import br.com.mysafeestablishmentcompany.api.request.CloseOrderPadRequest;
import br.com.mysafeestablishmentcompany.api.request.CreateOrderPadRequest;
import br.com.mysafeestablishmentcompany.api.request.PaymentOrderPadRequest;
import br.com.mysafeestablishmentcompany.domain.Customer;
import br.com.mysafeestablishmentcompany.domain.OrderPad;
import br.com.mysafeestablishmentcompany.domain.TableEstablishment;
import br.com.mysafeestablishmentcompany.exception.TableEstablishmentUnavailableException;
import br.com.mysafeestablishmentcompany.repository.CustomerRepository;
import br.com.mysafeestablishmentcompany.repository.OrderPadRepository;
import br.com.mysafeestablishmentcompany.repository.TableEstablishmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderPadService {
    private final Logger logger = LoggerFactory.getLogger(OrderPadService.class);

    private final OrderPadRepository orderPadRepository;
    private final TableEstablishmentRepository tableEstablishmentRepository;
    private final CustomerRepository customerRepository;

    private final double TAX_RATE = 0.10;
    private final String AWAIT_PAYMENT_STATUS = "1";
    private final String OPEN_STATUS = "0";

    @Autowired
    public OrderPadService(OrderPadRepository orderPadRepository, TableEstablishmentRepository tableEstablishmentRepository, CustomerRepository customerRepository) {
        this.orderPadRepository = orderPadRepository;
        this.tableEstablishmentRepository = tableEstablishmentRepository;
        this.customerRepository = customerRepository;
    }

    public ResponseEntity<String> createOrderPad(CreateOrderPadRequest createOrderPadRequest) {
        Customer customer = customerRepository.findById(createOrderPadRequest.getCustomerId());
        OrderPad orderPad = new OrderPad();
        if (customer == null) {
            return new ResponseEntity<String>("Customer não encontrado!", HttpStatus.NOT_FOUND);
        }
        try {
            orderPad.setCustomerId(customer.getId());
            orderPad.setCustomerName(customer.getName());
            orderPad.setStatus(OPEN_STATUS);
            orderPad.setQuantityCustomer(createOrderPadRequest.getQuantityCustomer());
            reservationTable(createOrderPadRequest.getTableId());
            orderPad.setTableId(createOrderPadRequest.getTableId());
            orderPadRepository.save(orderPad);
        } catch (Exception e) {
            if(e instanceof TableEstablishmentUnavailableException){
                return new ResponseEntity<String>("Mesa selecionada esta indisponivel", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("comanda criada com sucesso!", HttpStatus.CREATED);
    }

    public ResponseEntity<OrderPad> closeOrderPad(CloseOrderPadRequest closeOrderPadRequest) {
        final OrderPad orderPad = orderPadRepository.findByCustomerIdAndStatus(closeOrderPadRequest.getCustomerId(), "0");
        if(orderPad == null){
            return new ResponseEntity<OrderPad>(new OrderPad(), HttpStatus.NOT_FOUND);
        }
        orderPad.setPayment(closeOrderPadRequest.getPayment());
        orderPad.setTip(closeOrderPadRequest.getTip());
        orderPad.setStatus(AWAIT_PAYMENT_STATUS);
        calculateRate(orderPad);
        calculateOrdedPad(orderPad);
        return new ResponseEntity<OrderPad>(orderPadRepository.save(orderPad), HttpStatus.OK);
    }

    public ResponseEntity<String> paymentOrderPad(PaymentOrderPadRequest paymentOrderPadRequest) {
        OrderPad orderPad = orderPadRepository.findByCustomerIdAndStatus(paymentOrderPadRequest.getCustomerId(), "1");
        if (orderPad == null) {
            return new ResponseEntity<String>("Comanda em aberto, solicite o fechamento", HttpStatus.BAD_REQUEST);
        }
        if (orderPad.getValue() != paymentOrderPadRequest.getValuePayment()) {
            return new ResponseEntity<String>("Valor pago menor que o valor da comanda - Valor comanda:R$ " + orderPad.getValue(), HttpStatus.BAD_REQUEST);
        }
        orderPad.setStatus("2");
        leaveTable(orderPad.getTableId());
        orderPadRepository.save(orderPad);

        return new ResponseEntity<String>("comanda encerrada com sucesso!", HttpStatus.OK);
    }

    public TableEstablishment reservationTable(long tableId) throws TableEstablishmentUnavailableException {
        TableEstablishment tableEstablishment = tableEstablishmentRepository.findByIdAndAndStatusTable(tableId, "0");
        if (tableEstablishment == null) {
            throw new TableEstablishmentUnavailableException("Mesa indisponivel");
        }
        tableEstablishment.setStatusTable("1");
        return tableEstablishmentRepository.save(tableEstablishment);
    }

    public void leaveTable(long tableId){
        TableEstablishment tableEstablishment = tableEstablishmentRepository.findByIdAndAndStatusTable(tableId, "1");
        tableEstablishment.setStatusTable("0");
        tableEstablishmentRepository.save(tableEstablishment);
    }

    public void calculateRate(OrderPad orderPad) {
        orderPad.setRate(orderPad.getValue() * TAX_RATE);
    }

    public void calculateOrdedPad(OrderPad orderPad) {
        orderPad.setValue(orderPad.getValue() + orderPad.getRate() + orderPad.getTip());
    }

}

