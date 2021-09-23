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

import java.util.Objects;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public ResponseEntity<CustomerResponse> register(CustomerRequest customerRequest) {
        try {
            hasRegister(customerRequest);
            CustomerResponse customerResponse = saveCustomer(customerRequest);
            return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Não foi possivel criar o customer");
            return new ResponseEntity<>(new CustomerResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<CustomerResponse> login(CustomerRequest customerRequest) {
        Customer customerDTO = customerRepository.findByCpf(customerRequest.getCpf());
        if (Objects.isNull(customerDTO)) {
            return new ResponseEntity<>(new CustomerResponse("Customer não cadastrado"), HttpStatus.BAD_REQUEST);
        }

        if (!isCredentialsValid(customerRequest, customerDTO)) {
            return new ResponseEntity<>(new CustomerResponse("Customer não encontrado ou credenciais invalidas"), HttpStatus.BAD_REQUEST);
        }

        logger.info("Customer logado com sucesso - Customer='{}'", customerDTO.getCpf());
        return new ResponseEntity<>(new CustomerResponse(customerDTO.getName(), customerDTO.getId(), ""), HttpStatus.OK);
    }

    public void hasRegister(CustomerRequest customerRequest) throws RegisteredUserException {
        logger.info("Verificando se customer ja existe na base");
        Customer customers = customerRepository.findCustomerByCpfOrPhoneNumber(customerRequest.getCpf(), customerRequest.getPhoneNumber());
        if (customers != null) {
            logger.info("customer ja existe na base - Customer='{}'", customerRequest.getCpf());
            throw new RegisteredUserException("Usuario ja cadastrado");
        }
    }

    public CustomerResponse saveCustomer(CustomerRequest customerRequest) throws RegisteredUserException {
        Customer customer = customerRepository.save(
                new Customer(customerRequest.getName(), customerRequest.getPhoneNumber(), customerRequest.getCpf()));
        if (Objects.isNull(customer.getId())) {
            throw new RegisteredUserException("Não foi possivel criar o customer");
        }
        logger.info("Customer criado com sucesso - Customer='{}'", customerRequest.getCpf());
        return new CustomerResponse(customer.getName(), customer.getId(), "token");
    }

    boolean isCredentialsValid(CustomerRequest customerRequest, Customer customerDTO) {
        return Objects.equals(customerRequest.getPhoneNumber(), customerDTO.getPhoneNumber());
    }

}
