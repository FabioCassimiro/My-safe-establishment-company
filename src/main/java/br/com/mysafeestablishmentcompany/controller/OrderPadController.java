package br.com.mysafeestablishmentcompany.controller;

import br.com.mysafeestablishmentcompany.api.request.CloseOrderPadRequest;
import br.com.mysafeestablishmentcompany.api.request.CreateOrderPadRequest;
import br.com.mysafeestablishmentcompany.api.request.PaymentOrderPadByCardRequest;
import br.com.mysafeestablishmentcompany.api.response.CloseOrderPadResponse;
import br.com.mysafeestablishmentcompany.domain.OrderPad;
import br.com.mysafeestablishmentcompany.service.OrderPadService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CloseOrderPadResponse closeOrderPad(@RequestBody CloseOrderPadRequest closeOrderPadRequest) throws Exception {
        return orderPadService.closeOrderPad(closeOrderPadRequest);
    }

    @PostMapping("/manual/payment/{ordepadId}/{customerId}")
    public OrderPad paymentOrderPad(@PathVariable("ordepadId") long ordepadId, @PathVariable("customerId") long customerId) throws Exception {
        return orderPadService.closeManualPaymentOrderPad(ordepadId, customerId);
    }

    @PostMapping("card/payment")
    public OrderPad paymentOrderPadbyCard(@RequestBody PaymentOrderPadByCardRequest paymentOrderPadByCardRequest) throws Exception {
        return orderPadService.paymentOrderPadbyCard(paymentOrderPadByCardRequest);
    }

    @GetMapping("/{ordepadId}/{customerId}")
    public OrderPad orderPadByIdAndCustomerId(@PathVariable("ordepadId") long ordepadId, @PathVariable("customerId") long customerId) throws Exception {
        return orderPadService.orderPadByIdAndCustomerId(ordepadId, customerId);
    }

}
