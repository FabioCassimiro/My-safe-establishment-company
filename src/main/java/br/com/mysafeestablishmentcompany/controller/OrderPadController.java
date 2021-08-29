package br.com.mysafeestablishmentcompany.controller;

import br.com.mysafeestablishmentcompany.api.request.CloseOrderPadRequest;
import br.com.mysafeestablishmentcompany.api.request.CreateOrderPadRequest;
import br.com.mysafeestablishmentcompany.api.request.PaymentOrderPadRequest;
import br.com.mysafeestablishmentcompany.domain.OrderPad;
import br.com.mysafeestablishmentcompany.service.OrderPadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("private/orderpad")
public class OrderPadController {

    private final OrderPadService orderPadService;

    @Autowired
    public OrderPadController(OrderPadService orderPadService) {
        this.orderPadService = orderPadService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createOrderPad(@RequestBody CreateOrderPadRequest createOrderPadRequest) {
        return orderPadService.createOrderPad(createOrderPadRequest);
    }

    @PostMapping("/close")
    public ResponseEntity closeOrderPad(@RequestBody CloseOrderPadRequest closeOrderPadRequest) {
        return orderPadService.closeOrderPad(closeOrderPadRequest);
    }

    @PostMapping("/payment")
    public ResponseEntity<String> paymentOrderPad(@RequestBody PaymentOrderPadRequest paymentOrderPadRequest) {
        return orderPadService.paymentOrderPad(paymentOrderPadRequest);
    }


}
