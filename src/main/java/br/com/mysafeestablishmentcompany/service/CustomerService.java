package br.com.mysafeestablishmentcompany.service;

import br.com.mysafeestablishmentcompany.api.request.CustomerRequest;
import br.com.mysafeestablishmentcompany.api.response.CustomerResponse;
import br.com.mysafeestablishmentcompany.domain.Customer;
import br.com.mysafeestablishmentcompany.exception.CustomerNotFoundException;
import br.com.mysafeestablishmentcompany.exception.InvalidCredentialsException;
import br.com.mysafeestablishmentcompany.exception.RegisteredUserException;
import br.com.mysafeestablishmentcompany.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            logger.info("Customer criado com sucesso - CustomerResponse='{}'", customerResponse);
            return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Não foi possivel criar o customer - erro='{}'", e.getMessage());
            return new ResponseEntity<>(new CustomerResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<CustomerResponse> login(CustomerRequest customerRequest) {
        try {
            Customer customerDTO = hasLogin(customerRequest);
            validateCredentials(customerRequest, customerDTO);
            logger.info("Customer logado com sucesso - Customer='{}'", customerDTO.getCpf());
            return new ResponseEntity<>(new CustomerResponse(customerDTO.getName(), customerDTO.getId(), "Acesso Liberado"), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Não foi possivel criar o customer - erro='{}'", e.getMessage());
            return new ResponseEntity<>(new CustomerResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    private Customer hasLogin(CustomerRequest customerRequest) throws CustomerNotFoundException {
        Customer customerDTO = customerRepository.findByCpf(customerRequest.getCpf());
        if (Objects.isNull(customerDTO)) {
            throw new CustomerNotFoundException("Customer não Encontrado");
        }
        return customerDTO;
    }

    public void hasRegister(CustomerRequest customerRequest) throws RegisteredUserException {
        logger.info("Verificando se customer ja existe na base");
        Customer customer = customerRepository.findCustomerByCpfOrPhoneNumber(customerRequest.getCpf(), customerRequest.getPhoneNumber());
        if (customer != null) {
            logger.info("customer ja existe na base - Customer='{}'", customerRequest.getCpf());
            throw new RegisteredUserException("Customer ja cadastrado");
        }
    }

    public CustomerResponse saveCustomer(CustomerRequest customerRequest) throws Exception {
        Customer customer = customerRepository.save(
                new Customer(customerRequest.getName(), validatePhoneNumber(customerRequest.getPhoneNumber()), validateCpf(customerRequest.getCpf())));
        if (Objects.isNull(customer.getId())) {
            throw new RegisteredUserException("Não foi possivel criar o customer");
        }
        logger.info("Customer criado com sucesso - Customer='{}'", customerRequest.getCpf());
        return new CustomerResponse(customer.getName(), customer.getId(), "Acesso Liberado");
    }

    public void validateCredentials(CustomerRequest customerRequest, Customer customerDTO) throws InvalidCredentialsException {
        if (!Objects.equals(customerRequest.getPhoneNumber(), customerDTO.getPhoneNumber())) {
            throw new InvalidCredentialsException("Credenciais não conferem");
        }
    }

    public String validatePhoneNumber(String phoneNumber) throws Exception {
        Pattern patternPhone = Pattern.compile("^[1-9]{2}(?:[2-8]|9[1-9])[0-9]{3}[0-9]{4}$");
        Matcher matcher = patternPhone.matcher(phoneNumber);
        if (!matcher.find()) {
            throw new Exception("phoneNumber informado é invalido");
        }
        return phoneNumber;
    }

    public static String validateCpf(String cpf) throws Exception {
        Pattern patternCpf = Pattern.compile("(^(\\d{11})$)");
        Matcher matcher = patternCpf.matcher(cpf);
        if (!matcher.find()) {
            throw new Exception("cpf informado é invalido");
        }
        return cpf;
    }

}
