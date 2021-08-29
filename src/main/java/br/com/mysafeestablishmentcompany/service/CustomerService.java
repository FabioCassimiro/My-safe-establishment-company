package br.com.mysafeestablishmentcompany.service;

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

    public ResponseEntity<String> register(Customer newCustomer) {
        try {
            hasRegister(newCustomer);
            customerRepository.save(newCustomer);
        } catch (Exception e) {
            logger.error("Não foi possivel criar o customer");
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        logger.info("Customer criado com sucesso - Customer='{}'", newCustomer.getCpf());
        return new ResponseEntity<String>("Custumer criado com sucesso", HttpStatus.CREATED);
    }

    public ResponseEntity<String> login(Customer requestCustomer) {
        Customer customer = customerRepository.findCustomerByCpfAndPhoneNumber(requestCustomer.getCpf(), requestCustomer.getPhoneNumber());
        if (customer == null) {
            return new ResponseEntity<String>("Usuario não cadastrado", HttpStatus.BAD_REQUEST);
        }
        logger.info("Customer logado com sucesso - Customer='{}'", customer.getCpf());
        return new ResponseEntity<String>("Logado com sucesso", HttpStatus.OK);
    }

    public void hasRegister(Customer customer) throws RegisteredUserException {
        logger.info("Verificando se customer ja existe na base");
        Customer customers = customerRepository.findCustomerByCpfOrPhoneNumber(customer.getCpf(), customer.getPhoneNumber());
        if (customers != null) {
            logger.info("customer ja existe na base - Customer='{}'", customer.getCpf());
            throw new RegisteredUserException("Usuario ja cadastrado");
        }
    }

}
