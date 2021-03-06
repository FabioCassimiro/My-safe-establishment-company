package br.com.mysafeestablishmentcompany.repository;

import br.com.mysafeestablishmentcompany.domain.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface OrderRepository extends CrudRepository<Order,Long> {
    ArrayList<Order> findByOrderPadId(long orderPadId);

    Order findOrderByIdAndOrderPadId(long id, long orderpaId);

    Order findOrderById(long id);
}
