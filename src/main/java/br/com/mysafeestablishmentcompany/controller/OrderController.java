package br.com.mysafeestablishmentcompany.controller;

import br.com.mysafeestablishmentcompany.api.request.OrdersRequest;
import br.com.mysafeestablishmentcompany.api.response.MessageResponse;
import br.com.mysafeestablishmentcompany.domain.Order;
import br.com.mysafeestablishmentcompany.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("private/order")
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/register")
    public MessageResponse register(@RequestBody OrdersRequest ordersRequest) throws Exception {
        return orderService.register(ordersRequest);
    }

    @GetMapping("/{customerId}")
    public ArrayList<Order> allOrders(@PathVariable long customerId) throws Exception {
        return orderService.allOrders(customerId);
    }

    @GetMapping("/{customerId}/{orderId}")
    public Order orderById(@PathVariable long customerId, @PathVariable long orderId) throws Exception {
        return orderService.orderById(customerId, orderId);
    }

    @PostMapping("update/{orderId}/{orderpadId}/{quantity}")
    public Order update(@PathVariable long orderId, @PathVariable long orderpadId, @PathVariable int quantity) throws Exception {
        return orderService.updateOrder(orderId, orderpadId, quantity);
    }

    @DeleteMapping("delete")
    public MessageResponse update(@RequestBody Order order) throws Exception {
        return orderService.deleteOrder(order);
    }

//    TODO: ALTERAÇÃO DE QUANTIDADE DE PRODUTOS ENQUANTO O STATUS FOR ACCEPTED
//    TODO: APAGAR PEDIDO ENQUANTO O STATUS FOR ACCEPTED
}
