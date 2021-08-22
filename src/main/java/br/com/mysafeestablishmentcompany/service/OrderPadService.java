package br.com.mysafeestablishmentcompany.service;


import br.com.mysafeestablishmentcompany.api.request.CloseOrderPadRequest;
import br.com.mysafeestablishmentcompany.api.request.CreateOrderPadRequest;
import br.com.mysafeestablishmentcompany.api.request.PaymentOrderPadRequest;
import br.com.mysafeestablishmentcompany.domain.Customer;
import br.com.mysafeestablishmentcompany.domain.OrderPad;
import br.com.mysafeestablishmentcompany.domain.TableEstablishment;
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

    OrderPadRepository orderPadRepository;
    TableEstablishmentRepository tableEstablishmentRepository;
    CustomerRepository customerRepository;

    private final double TAX_RATE = 0.10;
    private final String AWAIT_PAYMENT_STATUS = "1";
    private final String OPEN_STATUS = "0";

    @Autowired
    public OrderPadService(OrderPadRepository orderPadRepository, TableEstablishmentRepository tableEstablishmentRepository, CustomerRepository customerRepository) {
        this.orderPadRepository = orderPadRepository;
        this.tableEstablishmentRepository = tableEstablishmentRepository;
        this.customerRepository = customerRepository;
    }

    public ResponseEntity<String> createOrderPad(CreateOrderPadRequest createOrderPadRequest){
        OrderPad orderPad = new OrderPad();
        Customer customer = customerRepository.findById(createOrderPadRequest.getCustomerId());

        orderPad.setCustomerId(customer.getId());
        orderPad.setCustomerName(customer.getName());
        orderPad.setStatus(OPEN_STATUS);
        orderPad.setQuantityCustomer(createOrderPadRequest.getQuantityCustomer());
        reservationTable(createOrderPadRequest.getTableId());
        orderPad.setTableId(createOrderPadRequest.getTableId());
        orderPadRepository.save(orderPad);

        return new ResponseEntity<String>("comanda criada com sucesso!", HttpStatus.CREATED);
    }

    public ResponseEntity<OrderPad> closeOrderPad(CloseOrderPadRequest closeOrderPadRequest){

        final OrderPad orderPad = orderPadRepository.findByCustomerIdAndStatus(closeOrderPadRequest.getCustomerId(),"0");

        orderPad.setPayment(closeOrderPadRequest.getPayment());
        orderPad.setTip(closeOrderPadRequest.getTip());
        orderPad.setStatus(AWAIT_PAYMENT_STATUS);
        calculateRate(orderPad);
        calculateOrdedPad(orderPad);
        return new ResponseEntity<OrderPad>(orderPadRepository.save(orderPad), HttpStatus.OK);
    }

    public ResponseEntity<String> paymentOrderPad(PaymentOrderPadRequest paymentOrderPadRequest){
        OrderPad orderPad = orderPadRepository.findByCustomerIdAndStatus(paymentOrderPadRequest.getCustomerId(),"1");
        if (orderPad == null){
            return new ResponseEntity<String>("Comanda em aberto, solicite o fechamento", HttpStatus.BAD_REQUEST);
        }
        TableEstablishment tableEstablishment = tableEstablishmentRepository.findByIdAndAndStatusTable(orderPad.getTableId(),"1");
        if(orderPad.getValue() != paymentOrderPadRequest.getValuePayment()){
            return new ResponseEntity<String>("Valor pago menor que o valor da comanda - Valor comanda:R$ " + orderPad.getValue(), HttpStatus.BAD_REQUEST);
        }
        orderPad.setStatus("2");
        tableEstablishment.setStatusTable("0");
        orderPadRepository.save(orderPad);
        tableEstablishmentRepository.save(tableEstablishment);

        return new ResponseEntity<String>("comanda encerrada com sucesso!", HttpStatus.CREATED);
    }

    public void reservationTable(long tableId){
        TableEstablishment tableEstablishment = tableEstablishmentRepository.findByIdAndAndStatusTable(tableId,"0");
        tableEstablishment.setStatusTable("1");
        tableEstablishmentRepository.save(tableEstablishment);
    }

    public void calculateRate(OrderPad orderPad){
        orderPad.setRate(orderPad.getValue() * TAX_RATE);
    }

    public void calculateOrdedPad(OrderPad orderPad){
        orderPad.setValue(orderPad.getValue() + orderPad.getRate() + orderPad.getTip());
    }

}

