package br.com.mysafeestablishmentcompany.repository;

import br.com.mysafeestablishmentcompany.domain.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment,Long> {

}
