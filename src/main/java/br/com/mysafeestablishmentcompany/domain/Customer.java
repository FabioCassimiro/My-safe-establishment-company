package br.com.mysafeestablishmentcompany.domain;

import javax.persistence.Entity;

@Entity
public class Customer extends AbstractEntity {

    private String name;
    private String phoneNumber;
    private String cpf;
    private String role;

    public Customer(String name, String phoneNumber, String cpf, String role) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.cpf = cpf;
        this.role = role;
    }

    public Customer(String name, String phoneNumber, String cpf) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.cpf = cpf;
    }

    public Customer(String phoneNumber, String cpf) {
        this.phoneNumber = phoneNumber;
        this.cpf = cpf;
    }

    public Customer() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}
