package br.com.mysafeestablishmentcompany.repository;

import br.com.mysafeestablishmentcompany.domain.OrderPad;
import org.springframework.data.repository.CrudRepository;
import java.util.ArrayList;

public interface OrderPadRepository extends CrudRepository<OrderPad,Long> {
    OrderPad findByCustomerIdAndStatus(long CustomerId, String status);

    ArrayList<OrderPad> findByCustomerId(long CustomerId);

    OrderPad findOrderPadByIdAndCustomerId(long id, long customerId);
}
