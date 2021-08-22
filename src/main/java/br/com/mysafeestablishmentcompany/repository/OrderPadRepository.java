package br.com.mysafeestablishmentcompany.repository;

import br.com.mysafeestablishmentcompany.domain.OrderPad;
import org.springframework.data.repository.CrudRepository;

public interface OrderPadRepository extends CrudRepository<OrderPad,Long> {
    OrderPad findByCustomerIdAndStatus(long CustomerId, String status);
}
