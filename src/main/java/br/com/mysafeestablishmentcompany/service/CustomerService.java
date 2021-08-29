package br.com.mysafeestablishmentcompany.service;

import br.com.mysafeestablishmentcompany.api.request.CustomerRequest;
import br.com.mysafeestablishmentcompany.api.response.CustomerResponse;
import br.com.mysafeestablishmentcompany.domain.Customer;
import br.com.mysafeestablishmentcompany.exception.RegisteredUserException;
import br.com.mysafeestablishmentcompany.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final OrderPadService orderPadService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, OrderPadService orderPadService) {
        this.customerRepository = customerRepository;
        this.orderPadService = orderPadService;
    }

    public ResponseEntity<CustomerResponse> register(CustomerRequest customerRequest) {
        Customer customer = customerRequest.getCustomer();
        try {
            hasRegister(customer);
            saveCustomer(customer);
        } catch (Exception e) {
            logger.error("Não foi possivel criar o customer");
            return new ResponseEntity<>(new CustomerResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        logger.info("Customer criado com sucesso - Customer='{}'", customer.getCpf());
        return new ResponseEntity<>(new CustomerResponse(customer, ""), HttpStatus.CREATED);
    }

    public ResponseEntity<CustomerResponse> login(CustomerRequest customerRequest) {
        Customer customer = customerRepository.findCustomerByCpfAndPhoneNumber(customerRequest.getCustomer().getCpf(), customerRequest.getCustomer().getPhoneNumber());
        if (customer == null) {
            return new ResponseEntity<>(new CustomerResponse("Customer não cadastrado"), HttpStatus.BAD_REQUEST);
        }
        logger.info("Customer logado com sucesso - Customer='{}'", customer.getCpf());
        return new ResponseEntity<>(new CustomerResponse(customer,""), HttpStatus.OK);
    }

    public void hasRegister(Customer customer) throws RegisteredUserException {
        logger.info("Verificando se customer ja existe na base");
        Customer customers = customerRepository.findCustomerByCpfOrPhoneNumber(customer.getCpf(), customer.getPhoneNumber());
        if (customers != null) {
            logger.info("customer ja existe na base - Customer='{}'", customer.getCpf());
            throw new RegisteredUserException("Usuario ja cadastrado");
        }
    }

    public void saveCustomer(Customer customer) throws RegisteredUserException{
        customer = customerRepository.save(customer);
        if (customer.getId()  == null){
            throw new RegisteredUserException("Não foi possivel criar o customer");
        }
    }

}
