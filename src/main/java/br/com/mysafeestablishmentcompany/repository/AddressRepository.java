package br.com.mysafeestablishmentcompany.repository;

import br.com.mysafeestablishmentcompany.domain.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address,Long> {
}
