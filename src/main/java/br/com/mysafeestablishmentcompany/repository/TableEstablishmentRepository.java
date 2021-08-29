package br.com.mysafeestablishmentcompany.repository;

import br.com.mysafeestablishmentcompany.domain.TableEstablishment;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface TableEstablishmentRepository extends CrudRepository<TableEstablishment,Long> {

    TableEstablishment findByIdAndAndStatusTable(long tableId, String status);

    ArrayList<TableEstablishment> findAll();

    TableEstablishment findTableEstablishmentById(long tableId);
}
