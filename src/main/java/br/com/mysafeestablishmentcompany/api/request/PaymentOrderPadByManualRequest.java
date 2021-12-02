package br.com.mysafeestablishmentcompany.api.request;

import java.util.StringJoiner;

public class PaymentOrderPadByManualRequest {

    private Long customerId;
    private String paymentMethod;
    private double valuePayment;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public double getValuePayment() {
        return valuePayment;
    }

    public void setValuePayment(double valuePayment) {
        this.valuePayment = valuePayment;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentOrderPadByManualRequest() {
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PaymentOrderPadByManualRequest.class.getSimpleName() + "[", "]")
                .add("customerId=" + customerId)
                .add("paymentMethod='" + paymentMethod + "'")
                .add("valuePayment=" + valuePayment)
                .toString();
    }
}
