package br.com.mysafeestablishmentcompany.api.response;

import br.com.mysafeestablishmentcompany.domain.Customer;

public class CustomerResponse extends ErrorResponse {

    private Customer customer;
    private String token;

    public CustomerResponse(Customer customer, String token) {
        this.customer = customer;
        this.token = token;
    }

    public CustomerResponse(String errorMessage) {
        super(errorMessage);
        this.customer = null;
        this.token = null;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "CustomerResponse{" +
                "customer=" + customer +
                ", token='" + token + '\'' +
                '}';
    }
}
