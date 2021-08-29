package br.com.mysafeestablishmentcompany.api.request;

public class CloseOrderPadRequest {

    private long customerId;
    private String payment;
    private double tip;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public double getTip() {
        return tip;
    }

    public void setTip(double tip) {
        this.tip = tip;
    }

    public CloseOrderPadRequest() {
    }
}
