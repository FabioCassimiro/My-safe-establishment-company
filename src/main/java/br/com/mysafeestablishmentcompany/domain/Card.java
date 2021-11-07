package br.com.mysafeestablishmentcompany.domain;

public class Card {

    private String cardNumber;
    private String dateExpiry;
    private String cvv;
    private String nameCard;
    private String flagCard;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getDateExpiry() {
        return dateExpiry;
    }

    public void setDateExpiry(String dateExpiry) {
        this.dateExpiry = dateExpiry;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getNameCard() {
        return nameCard;
    }

    public void setNameCard(String nameCard) {
        this.nameCard = nameCard;
    }

    public String getFlagCard() {
        return flagCard;
    }

    public void setFlagCard(String flagCard) {
        this.flagCard = flagCard;
    }

    public Card() {
    }

}
