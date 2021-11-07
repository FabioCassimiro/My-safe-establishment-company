package br.com.mysafeestablishmentcompany.api.request;

public class CloseOrderPadRequest {

    private long customerId;
    private double tip;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
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
