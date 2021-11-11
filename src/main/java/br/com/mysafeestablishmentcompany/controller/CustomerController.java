package br.com.mysafeestablishmentcompany.controller;

import br.com.mysafeestablishmentcompany.api.imgur.ImgurClient;
import br.com.mysafeestablishmentcompany.api.request.CustomerRequest;
import br.com.mysafeestablishmentcompany.api.response.CustomerResponse;
import br.com.mysafeestablishmentcompany.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/public/customer")
@CrossOrigin
public class CustomerController {

    final CustomerService customerService;
    ImgurClient imgurClient = new ImgurClient();

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/image")
    public String image(@RequestPart(value = "file") MultipartFile file) throws IOException {
        System.out.println(imgurClient.uploadImage(file));
        return "";
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
