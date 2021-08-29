package br.com.mysafeestablishmentcompany.service;


import br.com.mysafeestablishmentcompany.api.request.RegisterCompany;
import br.com.mysafeestablishmentcompany.domain.Address;
import br.com.mysafeestablishmentcompany.domain.Establishment;
import br.com.mysafeestablishmentcompany.domain.Owner;
import br.com.mysafeestablishmentcompany.repository.AddressRepository;
import br.com.mysafeestablishmentcompany.repository.EstablishmentRepository;
import br.com.mysafeestablishmentcompany.repository.OwnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {

    private static final Logger logger = LoggerFactory.getLogger(OwnerService.class);

    private final OwnerRepository ownerRepository;
    private final EstablishmentRepository establishmentRepository;
    private final AddressRepository addressRepository;

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
            logger.error("Não foi possivel criar o estabelecimento");
            return  new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        logger.info("Estabelecimento criado com sucesso - Establishment='{}'", establishment.getCnpj());
        return new  ResponseEntity<String>("Cadastrado com sucesso!", HttpStatus.CREATED);
    }

    public ResponseEntity<String> login(Owner requestOwner) throws Exception {
        Owner owner = ownerRepository.findByEmailAndPassword(requestOwner.getEmail(),requestOwner.getPassword());
        if (owner != null) {
            logger.info("Customer logado com sucesso - Owner='{}'", owner.getCpf());
            return new ResponseEntity<String>("Logado com sucesso!",HttpStatus.OK);
        }
        return new ResponseEntity<String>("Não foi possivel logar!",HttpStatus.BAD_REQUEST);
    }

    public Address saveAddress(Address address) {
        logger.info("Salvando endereço do estabelecimento!");
        return addressRepository.save(address);
    }

    public Establishment saveEstablishment(Establishment establishment) throws Exception {
        logger.info("Salvando estabelecimento!");
        if(establishmentRepository.findByCompanyNameOrCnpj(establishment.getCompanyName(),establishment.getCnpj()) != null){
            logger.error("Não foi possivel salvar o Estabelecimento!");
            throw new Exception("Não foi possivel salvar o Estabelecimento!");
        }
        logger.info("Estabelecimento criado com sucesso!");
        return establishmentRepository.save(establishment);
    }

    public Owner saveOwner(Owner owner) throws Exception {
        logger.info("Salvando Proprietario!");
        if (ownerRepository.findByEmailOrCpfOrPhoneNumber(owner.getEmail(),owner.getCpf(),owner.getPhoneNumber()) != null){
            logger.error("Não foi possivel salvar o Proprietario!");
            throw new Exception("Não foi possivel salvar o Proprietario!");
        }
        logger.info("Proprietario criado com sucesso!");
        return ownerRepository.save(owner);
    }
}
