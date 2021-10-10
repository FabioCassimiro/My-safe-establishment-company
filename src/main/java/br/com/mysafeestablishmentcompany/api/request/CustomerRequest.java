package br.com.mysafeestablishmentcompany.api.request;

public class CustomerRequest {

    private String name;
    private String phoneNumber;
    private String cpf;

    public CustomerRequest(String name, String phoneNumber, String cpf) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.cpf = cpf;
    }

    public CustomerRequest() {
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

    @Override
    public String toString() {
        return "CustomerRequest{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}
