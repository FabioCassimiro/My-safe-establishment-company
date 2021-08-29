package br.com.mysafeestablishmentcompany.controller;

import br.com.mysafeestablishmentcompany.domain.Customer;
import br.com.mysafeestablishmentcompany.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/public/customer")
public class CustomerController {

    CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Customer customer) {
        return customerService.register(customer);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Customer customer) {
        return customerService.login(customer);
    }

}
