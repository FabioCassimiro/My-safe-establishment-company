package br.com.mysafeestablishmentcompany.repository;

import br.com.mysafeestablishmentcompany.domain.Establishment;
import org.springframework.data.repository.CrudRepository;

public interface EstablishmentRepository extends CrudRepository<Establishment,Long> {
    Establishment findByCompanyNameOrCnpj(String companyName, String cnpj);
}
