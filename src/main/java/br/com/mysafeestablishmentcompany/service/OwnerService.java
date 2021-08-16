package br.com.mysafeestablishmentcompany.service;


import br.com.mysafeestablishmentcompany.api.request.RegisterCompany;
import br.com.mysafeestablishmentcompany.domain.Address;
import br.com.mysafeestablishmentcompany.domain.Establishment;
import br.com.mysafeestablishmentcompany.domain.Owner;
import br.com.mysafeestablishmentcompany.repository.AddressRepository;
import br.com.mysafeestablishmentcompany.repository.EstablishmentRepository;
import br.com.mysafeestablishmentcompany.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {

    OwnerRepository ownerRepository;
    EstablishmentRepository establishmentRepository;
    AddressRepository addressRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository, EstablishmentRepository establishmentRepository, AddressRepository addressRepository) {
        this.ownerRepository = ownerRepository;
        this.establishmentRepository = establishmentRepository;
        this.addressRepository = addressRepository;
    }

    public ResponseEntity<String> register(RegisterCompany newCompany){
        Address address = newCompany.getAddress();
        Establishment establishment = newCompany.getEstablishment();
        Owner owner =  newCompany.getOwner();
        try {
            establishment.setAddressId(saveAddress(address).getId());
            owner.setEstablishmentId(saveEstablishment(establishment).getId());
            saveOwner(owner);
        } catch (Exception e){
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new  ResponseEntity<>("Cadastrado com sucesso!", HttpStatus.CREATED);
    }

    public ResponseEntity<String> login(Owner requestOwner) throws Exception {
        Owner owner = ownerRepository.findByEmailAndPassword(requestOwner.getEmail(),requestOwner.getPassword());
        if (owner == null) {
            return new ResponseEntity<>("Não foi possivel logar!",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Logado com sucesso!",HttpStatus.OK);
    }

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public Establishment saveEstablishment(Establishment establishment) throws Exception {
        if(establishmentRepository.findByCompanyNameOrCnpj(establishment.getCompanyName(),establishment.getCnpj()) != null){
            throw new Exception("Não foi possivel salvar o Estabelecimento!");
        }
        return establishmentRepository.save(establishment);
    }

    public Owner saveOwner(Owner owner) throws Exception {
        if (ownerRepository.findByEmailOrCpfOrPhoneNumber(owner.getEmail(),owner.getCpf(),owner.getPhoneNumber()) != null){
            throw new Exception("Não foi possivel salvar o Proprietario!");
        }
        return ownerRepository.save(owner);
    }
}
