package br.com.mysafeestablishmentcompany.controller;

import br.com.mysafeestablishmentcompany.domain.Order;
import br.com.mysafeestablishmentcompany.domain.OrderPad;
import br.com.mysafeestablishmentcompany.repository.OrderRepository;
import br.com.mysafeestablishmentcompany.service.OrderPadService;
import br.com.mysafeestablishmentcompany.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("private/management")
@CrossOrigin
public class ManagementController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final OrderPadService orderPadService;

    public ManagementController(OrderRepository orderRepository, OrderService orderService, OrderPadService orderPadService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.orderPadService = orderPadService;
    }

    @GetMapping("order")
    public Order orderById(@RequestParam(required = false, name = "id") Long orderId,
                                  @RequestParam(required = false) Long orderpad) {

        return orderRepository.findOrderByIdAndOrderPadId(orderId, orderpad);
    }

    @GetMapping("orders")
    public List<Order> listOrdersByOrderPad(@RequestParam(required = true) Long orderpad) {
        return orderRepository.findByOrderPadId(orderpad);
    }

    @PostMapping("change/order")
    public Order changeStatusOrder(@RequestParam(name = "id") Long orderId,
                                   @RequestParam() String status,
                                   @RequestParam(required = false) Long customerId) throws Exception {
        return orderService.changeOrderStatus(orderId, status, customerId);
    }

    @GetMapping("orderpad")
    public OrderPad orderpadById(@RequestParam(required = false, name = "id") Long orderPad) {
        return orderPadService.orderpadsById(orderPad);
    }

    @GetMapping("orderpads")
    public List<OrderPad> listOrderpad(@RequestParam(defaultValue = "0") String status) {
        return orderPadService.orderpadsByStatus(status);
    }


}
