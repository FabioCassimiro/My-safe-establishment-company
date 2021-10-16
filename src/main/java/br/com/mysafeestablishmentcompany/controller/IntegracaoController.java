package br.com.mysafeestablishmentcompany.controller;

import br.com.mysafeestablishmentcompany.exception.IntegracaoException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/integracao")
public class IntegracaoController {

    @GetMapping("/excepiton")
    public String erroFeign() throws Exception {
        throw new IntegracaoException("Erro de integração");
    }
}
