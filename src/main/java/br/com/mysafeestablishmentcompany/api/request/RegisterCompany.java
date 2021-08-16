package br.com.mysafeestablishmentcompany.api.request;

import br.com.mysafeestablishmentcompany.domain.Address;
import br.com.mysafeestablishmentcompany.domain.Establishment;
import br.com.mysafeestablishmentcompany.domain.Owner;

public class RegisterCompany {

    private Owner owner;
    private Establishment establishment;
    private Address address;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public RegisterCompany(Owner owner, Establishment establishment, Address address) {
        this.owner = owner;
        this.establishment = establishment;
        this.address = address;
    }

    public RegisterCompany() {
    }

    @Override
    public String toString() {
        return "RegisterCompany{" +
                "owner=" + owner +
                ", establishment=" + establishment +
                ", address=" + address +
                '}';
    }
}
