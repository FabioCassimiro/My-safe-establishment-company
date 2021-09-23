package br.com.mysafeestablishmentcompany.controller;

import br.com.mysafeestablishmentcompany.api.request.CustomerRequest;
import br.com.mysafeestablishmentcompany.api.response.CustomerResponse;
import br.com.mysafeestablishmentcompany.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/customer")
@CrossOrigin
public class CustomerController {

    final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> register(@RequestBody CustomerRequest customer) {
        return customerService.register(customer);
    }

    @PostMapping("/login")
    public ResponseEntity<CustomerResponse> login(@RequestBody CustomerRequest customer) {
        return customerService.login(customer);
    }

}
