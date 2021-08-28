package br.com.mysafeestablishmentcompany.controller;

import br.com.mysafeestablishmentcompany.api.request.RegisterCompany;
import br.com.mysafeestablishmentcompany.domain.Owner;
import br.com.mysafeestablishmentcompany.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin; //TODO: Verificar se a importação está correta

@CrossOrigin
@RestController
public class OwnerController {

    OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping("/public/owner/register")
    public ResponseEntity createCompany(@RequestBody RegisterCompany registerCompany) {
        return ownerService.register(registerCompany);
    }

    @PostMapping("/public/owner/login")
    public ResponseEntity login(@RequestBody Owner owner) throws Exception {
        return ownerService.login(owner);
    }


}
