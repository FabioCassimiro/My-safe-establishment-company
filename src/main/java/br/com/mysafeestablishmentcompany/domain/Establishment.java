package br.com.mysafeestablishmentcompany.domain;

import javax.persistence.Entity;

@Entity
public class Establishment extends AbstractEntity {

    private String companyName;
    private String tradingName;
    private String cnpj;
    private String typeEstablishment;
    private String phoneNumber;
    private long addressId;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTradingName() {
        return tradingName;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTypeEstablishment() {
        return typeEstablishment;
    }

    public void setTypeEstablishment(String typeEstablishment) {
        this.typeEstablishment = typeEstablishment;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public Establishment(String companyName, String tradingName, String cnpj, String typeEstablishment, String phoneNumber) {
        this.companyName = companyName;
        this.tradingName = tradingName;
        this.cnpj = cnpj;
        this.typeEstablishment = typeEstablishment;
        this.phoneNumber = phoneNumber;
    }

    public Establishment() {
    }

    @Override
    public String toString() {
        return "Establishment{" +
                "companyName='" + companyName + '\'' +
                ", tradingName='" + tradingName + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", typeEstablishment='" + typeEstablishment + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", addressId=" + addressId +
                '}';
    }
}
