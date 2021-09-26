package br.com.mysafeestablishmentcompany.repository;


import br.com.mysafeestablishmentcompany.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findCustomerByCpfOrPhoneNumber(String cpf, String phoneNumber);

    Customer findById(long id);

    Customer findByCpf(String cpf);
}
