package br.com.mysafeestablishmentcompany.domain;

import javax.persistence.Entity;

@Entity
public class Address extends AbstractEntity {

    private String publicPlace;
    private String number;
    private String district;
    private String city;

    public String getPublicPlace() {
        return publicPlace;
    }

    public void setPublicPlace(String publicPlace) {
        this.publicPlace = publicPlace;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Address(String publicPlace, String number, String district, String city) {
        this.publicPlace = publicPlace;
        this.number = number;
        this.district = district;
        this.city = city;
    }

    public Address() {
    }

    @Override
    public String toString() {
        return "Address{" +
                "publicPlace='" + publicPlace + '\'' +
                ", number='" + number + '\'' +
                ", district='" + district + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
