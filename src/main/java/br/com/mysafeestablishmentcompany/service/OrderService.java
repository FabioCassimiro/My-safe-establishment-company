package br.com.mysafeestablishmentcompany.service;

import br.com.mysafeestablishmentcompany.api.request.OrdersRequest;
import br.com.mysafeestablishmentcompany.domain.Order;
import br.com.mysafeestablishmentcompany.domain.OrderPad;
import br.com.mysafeestablishmentcompany.domain.Product;
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

    public ResponseEntity<String> register(OrdersRequest ordersRequest){
        try{
            OrderPad orderPad = searchOrderPad(ordersRequest.getCustomerId());
            for (Order order : ordersRequest.getOrders()) {
                order.setOrderPadId(orderPad.getId());
                calculateOrder(order);
                orderRepository.save(order);
            }
            updateOrderPad(orderPad);
        }catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Pedido(s) criado(s)", HttpStatus.CREATED);
    }

    public OrderPad searchOrderPad(long customerId) throws Exception{
        OrderPad orderPad = orderPadRepository.findByCustomerIdAndStatus(customerId,"0");
        if (orderPad == null){
            throw new Exception("Não ha comanda aberta para esse usuario");
        }
        return orderPad;
    }

    public void calculateOrder(Order order){
        Product product = productRepository.findProductById(order.getProductId());
        order.setProductName(product.getName());
        order.setValue(product.getValue() * order.getQuantity());
    }

    public void updateOrderPad(OrderPad orderPad){
        double valueOrders = 0;
        final ArrayList<Order> orders = orderRepository.findByOrderPadId(orderPad.getId());
        for (Order order: orders) {
            valueOrders += order.getValue();
        }
        orderPad.setValue(valueOrders);
        orderPadRepository.save(orderPad);
    }
}
