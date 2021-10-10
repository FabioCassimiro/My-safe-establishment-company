package br.com.mysafeestablishmentcompany.controller;

import br.com.mysafeestablishmentcompany.api.request.CloseOrderPadRequest;
import br.com.mysafeestablishmentcompany.api.request.CreateOrderPadRequest;
import br.com.mysafeestablishmentcompany.api.request.PaymentOrderPadRequest;
import br.com.mysafeestablishmentcompany.api.response.CloseOrderPadResponse;
import br.com.mysafeestablishmentcompany.api.response.CreateOrderPadResponse;
import br.com.mysafeestablishmentcompany.api.response.PaymentOrderPadResponse;
import br.com.mysafeestablishmentcompany.domain.OrderPad;
import br.com.mysafeestablishmentcompany.service.OrderPadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/private/orderpad")
@CrossOrigin
public class OrderPadController {

    private final OrderPadService orderPadService;

    @Autowired
    public OrderPadController(OrderPadService orderPadService) {
        this.orderPadService = orderPadService;
    }

    @PostMapping("/create")
    public OrderPad createOrderPad(@RequestBody CreateOrderPadRequest createOrderPadRequest) throws Exception {
        return orderPadService.createOrderPad(createOrderPadRequest);
    }

    @PostMapping("/close")
    public OrderPad closeOrderPad(@RequestBody CloseOrderPadRequest closeOrderPadRequest) throws Exception {
        return orderPadService.closeOrderPad(closeOrderPadRequest);
    }

    @PostMapping("/payment")
    public OrderPad paymentOrderPad(@RequestBody PaymentOrderPadRequest paymentOrderPadRequest) throws Exception {
        return orderPadService.paymentOrderPad(paymentOrderPadRequest);
    }
  
  
}
