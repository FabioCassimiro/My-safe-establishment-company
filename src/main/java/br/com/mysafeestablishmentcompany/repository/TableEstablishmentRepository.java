package br.com.mysafeestablishmentcompany.repository;

import br.com.mysafeestablishmentcompany.domain.TableEstablishment;
import org.springframework.data.repository.CrudRepository;

public interface TableEstablishmentRepository extends CrudRepository<TableEstablishment,Long> {
    TableEstablishment findByIdAndAndStatusTable(long tableId, String status);
}
