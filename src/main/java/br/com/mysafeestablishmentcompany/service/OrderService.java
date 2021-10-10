package br.com.mysafeestablishmentcompany.service;

import br.com.mysafeestablishmentcompany.api.request.OrdersRequest;
import br.com.mysafeestablishmentcompany.domain.Order;
import br.com.mysafeestablishmentcompany.domain.OrderPad;
import br.com.mysafeestablishmentcompany.domain.Product;
import br.com.mysafeestablishmentcompany.exception.OrderPadNotFoundException;
import br.com.mysafeestablishmentcompany.repository.OrderPadRepository;
import br.com.mysafeestablishmentcompany.repository.OrderRepository;
import br.com.mysafeestablishmentcompany.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderService {

    OrderRepository orderRepository;
    OrderPadRepository orderPadRepository;
    ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository,OrderPadRepository orderPadRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderPadRepository = orderPadRepository;
    }

    public String register(OrdersRequest ordersRequest){
        try{
            OrderPad orderPad = searchOrderPad(ordersRequest.getCustomerId());
            for (Order order : ordersRequest.getOrders()) {
                order.setOrderPadId(orderPad.getId());
                calculateOrder(order);
                orderRepository.save(order);
            }
            updateOrderPad(orderPad);
        }catch (Exception e) {
            return e.getMessage();
        }
        return "Pedido(s) criado(s)";
    }

    private OrderPad searchOrderPad(long customerId) throws Exception{
        OrderPad orderPad = orderPadRepository.findByCustomerIdAndStatus(customerId,"0");
        if (orderPad == null){
            throw new OrderPadNotFoundException("Não ha comanda aberta para esse usuario");
        }
        return orderPad;
    }

    private void calculateOrder(Order order){
        Product product = productRepository.findProductById(order.getProductId());
        order.setProductName(product.getName());
        order.setValue(product.getValue() * order.getQuantity());
    }

    private void updateOrderPad(OrderPad orderPad){
        double valueOrders = 0;
        final ArrayList<Order> orders = orderRepository.findByOrderPadId(orderPad.getId());
        for (Order order: orders) {
            valueOrders += order.getValue();
        }
        orderPad.setValue(valueOrders);
        orderPadRepository.save(orderPad);
    }

    public ArrayList<Order> allOrders(long customerId) throws Exception {
        OrderPad orderPadDTO = searchOrderPad(customerId);
        return orderRepository.findByOrderPadId(orderPadDTO.getId());
    }

    public Order orderById(long customerId,long orderId) throws Exception {
        OrderPad orderPadDTO = searchOrderPad(customerId);
        return orderRepository.findOrderById(orderId);
    }
}
