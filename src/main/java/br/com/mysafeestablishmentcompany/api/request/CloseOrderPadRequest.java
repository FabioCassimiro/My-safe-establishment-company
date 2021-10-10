package br.com.mysafeestablishmentcompany.api.request;

public class CloseOrderPadRequest {

    private long customerId;
    private String paymentMethod;
    private double tip;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
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
