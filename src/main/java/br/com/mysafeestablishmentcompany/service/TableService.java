package br.com.mysafeestablishmentcompany.service;

import br.com.mysafeestablishmentcompany.domain.TableEstablishment;
import br.com.mysafeestablishmentcompany.repository.TableEstablishmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class TableService {

    final TableEstablishmentRepository tableEstablishmentRepository;

    @Autowired
    public TableService(TableEstablishmentRepository tableEstablishmentRepository) {
        this.tableEstablishmentRepository = tableEstablishmentRepository;
    }

    public TableEstablishment register(TableEstablishment tableEstablishment) {
        return tableEstablishmentRepository.save(tableEstablishment);
    }

    public String delete(Long id) {
        TableEstablishment tableDTO = tableEstablishmentRepository.findTableEstablishmentById(id);
        if (tableDTO != null) {
            tableEstablishmentRepository.delete(tableDTO);
            return "A mesa: " + tableDTO.getId() + " foi deletado com sucesso!";
        }
        return "A mesa: " + id + " não foi encontrado!";
    }

    public String update(TableEstablishment tableEstablishment) {
        TableEstablishment tableDTO = tableEstablishmentRepository.findTableEstablishmentById(tableEstablishment.getId());
        if (Objects.isNull(tableDTO)){
            return "A mesa: " + tableEstablishment.getId() + " não foi encontrado!";
        }
        tableEstablishmentRepository.save(tableEstablishment);
        return "A mesa: " + tableEstablishment.getId() + " foi alterado com sucesso!";
    }

    public ArrayList<TableEstablishment> allTableEstablishments() {
        return tableEstablishmentRepository.findAll();
    }

    public TableEstablishment table(Long id) {
        return tableEstablishmentRepository.findTableEstablishmentById(id);
    }

}
