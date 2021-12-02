package br.com.mysafeestablishmentcompany.service;

import br.com.mysafeestablishmentcompany.api.request.OrdersRequest;
import br.com.mysafeestablishmentcompany.api.response.MessageResponse;
import br.com.mysafeestablishmentcompany.domain.CompanyUtils;
import br.com.mysafeestablishmentcompany.domain.Order;
import br.com.mysafeestablishmentcompany.domain.OrderPad;
import br.com.mysafeestablishmentcompany.domain.Product;
import br.com.mysafeestablishmentcompany.exception.OrderNotFoundException;
import br.com.mysafeestablishmentcompany.exception.OrderPadClosedException;
import br.com.mysafeestablishmentcompany.exception.OrderPadNotFoundException;
import br.com.mysafeestablishmentcompany.exception.ProductNotFoundException;
import br.com.mysafeestablishmentcompany.repository.OrderPadRepository;
import br.com.mysafeestablishmentcompany.repository.OrderRepository;
import br.com.mysafeestablishmentcompany.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class OrderService {

    OrderRepository orderRepository;
    OrderPadRepository orderPadRepository;
    ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, OrderPadRepository orderPadRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderPadRepository = orderPadRepository;
    }

    public MessageResponse register(OrdersRequest ordersRequest) throws Exception {
        OrderPad orderPad = searchOrderPad(ordersRequest.getCustomerId());
        for (Order order : ordersRequest.getOrders()) {
            order.setOrderPadId(orderPad.getId());
            order.setStatus(CompanyUtils.ORDER_STATUS_IN_PROGRESS);
            calculateOrder(order);
            orderRepository.save(order);
        }
        updateOrderPad(orderPad);
        return new MessageResponse("Pedido(s) criado(s)");
    }

    private OrderPad searchOrderPad(long customerId) throws Exception {
        OrderPad orderPad = orderPadRepository.findByCustomerIdAndStatus(customerId, CompanyUtils.ORDERPAD_STATUS_OPEN);
        if (orderPad == null) {
            throw new OrderPadNotFoundException("Não ha comanda aberta para esse usuario");
        }
        return orderPad;
    }

    private void calculateOrder(Order order) throws ProductNotFoundException {
        Product product = productRepository.findProductById(order.getProductId());
        if (Objects.isNull(product)) {
            throw new ProductNotFoundException("Produto não encontrado");
        }
        order.setProductName(product.getName());
        order.setValue(product.getValue() * order.getQuantity());
    }

    private void updateOrderPad(OrderPad orderPad) {
        double valueOrders = 0;
        final ArrayList<Order> orders = orderRepository.findByOrderPadId(orderPad.getId());
        for (Order order : orders) {
            valueOrders += order.getValue();
        }
        orderPad.setValue(valueOrders);
        orderPad.setPaybleValue(valueOrders);
        orderPadRepository.save(orderPad);
    }

    public ArrayList<Order> allOrders(long customerId) throws Exception {
        OrderPad orderPadDTO = searchOrderPad(customerId);
        ArrayList<Order> orders = orderRepository.findByOrderPadId(orderPadDTO.getId());
        if (Objects.isNull(orders)) {
            throw new OrderPadClosedException(
                    String.format("Nenhuma Order encontrada para a OrderPad %s", orderPadDTO.getId())
            );
        }
        return orders;
    }

    public Order orderById(long customerId, long orderId) throws Exception {
        OrderPad orderPadDTO = searchOrderPad(customerId);
        Order order = orderRepository.findOrderByIdAndOrderPadId(orderId, orderPadDTO.getId());
        if (Objects.isNull(order)) {
            throw new OrderNotFoundException(
                    String.format("Order %s não encontrada para Customer %s", orderId, customerId)
            );
        }
        return order;
    }

    public Order changeOrderStatus(Long orderId, String status, Long customerId) throws Exception {
        Order order = orderById(customerId, orderId);
        if (CompanyUtils.ordersStatus.stream().anyMatch(s -> s.equals(status))) {
            if (!CompanyUtils.ORDER_STATUS_DELIVERED.equals(order.getStatus())) {
                order.setStatus(status);
                return orderRepository.save(order);
            }
        }
        throw new Exception("Não foi possivel alterar o status");
    }

    public Order updateOrder(long orderId, long orderpadId, int quantity) throws Exception {
        Order order = orderRepository.findOrderByIdAndOrderPadId(orderId, orderpadId);
        if (Objects.isNull(order) || quantity < 0 ||
                order.getStatus().equals(CompanyUtils.ORDER_STATUS_DELIVERED) ||
                order.getStatus().equals(CompanyUtils.ORDER_STATUS_IN_DELIVERY)) {
            throw new Exception(String.format("Pedido com Status: %s - Não é possivel alterar", order.getStatus()));
        }
        order.setQuantity(quantity);
        calculateOrder(order);
        updateOrderPad(orderPadRepository.findOrderPadById(orderpadId));
        return orderRepository.save(order);
    }

    public MessageResponse deleteOrder(Order order) throws Exception {
        Order orderDTO = orderRepository.findOrderById(order.getId());
        if (Objects.isNull(orderDTO) ||
                order.getStatus().equals(CompanyUtils.ORDER_STATUS_DELIVERED) ||
                order.getStatus().equals(CompanyUtils.ORDER_STATUS_IN_DELIVERY)){
            throw new Exception(String.format("Pedido com Status: %s - Não é possivel deletar", order.getStatus()));
        }
        orderRepository.delete(order);
        return new MessageResponse("Order deletada com sucesso");
    }
}
