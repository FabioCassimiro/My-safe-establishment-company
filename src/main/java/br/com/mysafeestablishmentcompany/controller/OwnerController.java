package br.com.mysafeestablishmentcompany.controller;

import br.com.mysafeestablishmentcompany.api.request.RegisterCompany;
import br.com.mysafeestablishmentcompany.domain.Owner;
import br.com.mysafeestablishmentcompany.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/owner")
@CrossOrigin
public class OwnerController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createCompany(@RequestBody RegisterCompany registerCompany) {
        return ownerService.register(registerCompany);
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody Owner owner) throws Exception {
        return ownerService.login(owner);
    }

}
