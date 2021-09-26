package br.com.mysafeestablishmentcompany.api.request;

public class PaymentOrderPadRequest {

    private long customerId;
    private double valuePayment;
    private double tip;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public double getValuePayment() {
        return valuePayment;
    }

    public void setValuePayment(double valuePayment) {
        this.valuePayment = valuePayment;
    }

    public double getTip() {
        return tip;
    }

    public void setTip(double tip) {
        this.tip = tip;
    }

    public PaymentOrderPadRequest() {
    }
}
