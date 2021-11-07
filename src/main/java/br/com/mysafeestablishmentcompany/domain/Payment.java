package br.com.mysafeestablishmentcompany.domain;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Payment extends AbstractEntity {

    private Long orderPadId;
    private String typeCard;
    private String cardNumberIdentifier;
    private double value;
    private String note;
    private LocalDateTime paymentDate;

    public Long getOrderPadId() {
        return orderPadId;
    }

    public void setOrderPadId(Long orderPadId) {
        this.orderPadId = orderPadId;
    }

    public String getTypeCard() {
        return typeCard;
    }

    public void setTypeCard(String typeCard) {
        this.typeCard = typeCard;
    }

    public String getCardNumberIdentifier() {
        return cardNumberIdentifier;
    }

    public void setCardNumberIdentifier(String cardNumberIdentifier) {
        this.cardNumberIdentifier = cardNumberIdentifier;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Payment() {
    }

    public Payment(Long orderPadId, String typeCard, String cardNumberIdentifier, double value, String note) {
        this.orderPadId = orderPadId;
        this.typeCard = typeCard;
        this.cardNumberIdentifier = cardNumberIdentifier;
        this.value = value;
        this.note = note;
        this.paymentDate = LocalDateTime.now();
    }

    public Payment(Long orderPadId, String typeCard, String cardNumberIdentifier, double value) {
        this.orderPadId = orderPadId;
        this.typeCard = typeCard;
        this.cardNumberIdentifier = cardNumberIdentifier;
        this.value = value;
        this.note = null;
        this.paymentDate = LocalDateTime.now();
    }
}
