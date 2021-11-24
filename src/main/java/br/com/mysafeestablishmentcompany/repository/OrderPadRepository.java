package br.com.mysafeestablishmentcompany.repository;

import br.com.mysafeestablishmentcompany.domain.OrderPad;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderPadRepository extends CrudRepository<OrderPad,Long> {
    OrderPad findByCustomerIdAndStatus(long CustomerId, String status);

    List<OrderPad> findByCustomerId(long CustomerId);

    List<OrderPad> findAllByStatus(String status);

    OrderPad findOrderPadByIdAndCustomerId(long id, long customerId);

    OrderPad findOrderPadById(long id);


}
