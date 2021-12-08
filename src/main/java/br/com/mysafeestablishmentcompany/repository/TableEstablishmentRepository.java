package br.com.mysafeestablishmentcompany.repository;

import br.com.mysafeestablishmentcompany.domain.TableEstablishment;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface TableEstablishmentRepository extends CrudRepository<TableEstablishment,Long> {

    TableEstablishment findByIdAndStatusTable(long tableId, String status);

    List<TableEstablishment> findTableEstablishmentByStatusTable(String status);

    ArrayList<TableEstablishment> findAll();

    TableEstablishment findTableEstablishmentById(long tableId);

    List<TableEstablishment> findTableEstablishmentByNumberSeatsInAndStatusTable(List<Integer> numberSeats, String tableStatusAvaliable);

    ArrayList<TableEstablishment> findAll(Sort seatNumber);
}
