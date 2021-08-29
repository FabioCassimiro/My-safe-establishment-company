package br.com.mysafeestablishmentcompany.api.request;

import br.com.mysafeestablishmentcompany.domain.Customer;

public class CustomerRequest {

    private Customer customer;

    public CustomerRequest(Customer customer) {
        this.customer = customer;
    }

    public CustomerRequest() {
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "CustomerRequest{" +
                "customer=" + customer +
                '}';
    }
}
