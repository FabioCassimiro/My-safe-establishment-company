package br.com.mysafeestablishmentcompany.controller;

import br.com.mysafeestablishmentcompany.api.request.OrdersRequest;
import br.com.mysafeestablishmentcompany.domain.Order;
import br.com.mysafeestablishmentcompany.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("private/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/register")
    public String register(@RequestBody OrdersRequest ordersRequest){
        return orderService.register(ordersRequest);
    }

    @GetMapping("/list/{customerId}")
    public ArrayList<Order> allOrders(@PathVariable long customerId) throws Exception {
        return orderService.allOrders(customerId);
    }

    @GetMapping("/list/{customerId}/{orderId}")
    public Order orderById(@PathVariable long customerId, @PathVariable long orderId) throws Exception {
        return orderService.orderById(customerId, orderId);
    }
}
