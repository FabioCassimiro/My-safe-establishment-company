package br.com.mysafeestablishmentcompany.api.request;

import br.com.mysafeestablishmentcompany.domain.Card;

public class PaymentOrderPadByCardRequest {

    private Long customerId;
    private String typeCard;
    private double valuePayment;
    private Card card;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getTypeCard() {
        return typeCard;
    }

    public void setTypeCard(String typeCard) {
        this.typeCard = typeCard;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public double getValuePayment() {
        return valuePayment;
    }

    public void setValuePayment(double valuePayment) {
        this.valuePayment = valuePayment;
    }

    public PaymentOrderPadByCardRequest() {
    }

    @Override
    public String toString() {
        return "PaymentOrderPadByCardRequest{" +
                "customerId=" + customerId +
                ", typeCard='" + typeCard + '\'' +
                ", card=" + card +
                '}';
    }
}
