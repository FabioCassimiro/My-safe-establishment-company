package br.com.mysafeestablishmentcompany.api.request;

import br.com.mysafeestablishmentcompany.domain.Order;

import java.util.ArrayList;

public class OrdersRequest {

    private long customerId;
    private ArrayList<Order> orders;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public OrdersRequest() {
    }
}
