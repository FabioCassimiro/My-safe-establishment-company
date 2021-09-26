package br.com.mysafeestablishmentcompany.controller;

import br.com.mysafeestablishmentcompany.api.request.CloseOrderPadRequest;
import br.com.mysafeestablishmentcompany.api.request.CreateOrderPadRequest;
import br.com.mysafeestablishmentcompany.api.request.PaymentOrderPadRequest;
import br.com.mysafeestablishmentcompany.api.response.CloseOrderPadResponse;
import br.com.mysafeestablishmentcompany.api.response.CreateOrderPadResponse;
import br.com.mysafeestablishmentcompany.api.response.PaymentOrderPadResponse;
import br.com.mysafeestablishmentcompany.service.OrderPadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/private/customer/orderPad")
@CrossOrigin
public class OrderPadController {


    private final OrderPadService orderPadService;

    @Autowired
    public OrderPadController(OrderPadService orderPadService) {
        this.orderPadService = orderPadService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateOrderPadResponse> createOrderPad(@RequestBody CreateOrderPadRequest createOrderPadRequest) {
        return orderPadService.createOrderPad(createOrderPadRequest);
    }

    @PostMapping("/close")
    public ResponseEntity<CloseOrderPadResponse> closeOrderPad(@RequestBody CloseOrderPadRequest closeOrderPadRequest) {
        return orderPadService.closeOrderPad(closeOrderPadRequest);
    }

    @PostMapping("/payment")
    public ResponseEntity<PaymentOrderPadResponse> paymentOrderPad(@RequestBody PaymentOrderPadRequest paymentOrderPadRequest){
        return orderPadService.paymentOrderPad(paymentOrderPadRequest);
    }


}
