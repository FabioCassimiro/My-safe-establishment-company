package br.com.mysafeestablishmentcompany.service;

import br.com.mysafeestablishmentcompany.domain.TableEstablishment;
import br.com.mysafeestablishmentcompany.repository.TableEstablishmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TableService {

    final TableEstablishmentRepository tableEstablishmentRepository;

    @Autowired
    public TableService(TableEstablishmentRepository tableEstablishmentRepository) {
        this.tableEstablishmentRepository = tableEstablishmentRepository;
    }

    public ResponseEntity<String> register(TableEstablishment tableEstablishment) {
        tableEstablishmentRepository.save(tableEstablishment);
        return new ResponseEntity<>("A mesa : " + tableEstablishment.getId() + " foi criada com sucesso!", HttpStatus.CREATED);
    }

    public ResponseEntity<String> delete(Long id) {
        TableEstablishment tableEstablishment = tableEstablishmentRepository.findTableEstablishmentById(id);
        if (tableEstablishment != null) {
            tableEstablishmentRepository.delete(tableEstablishment);
            return new ResponseEntity<>("A mesa: " + tableEstablishment.getId() + " foi deletado com sucesso!", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("A mesa: " + id + " não foi encontrado!", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> update(TableEstablishment tableEstablishment) {
        tableEstablishmentRepository.save(tableEstablishment);
        return new ResponseEntity<>("A mesa: " + tableEstablishment.getId() + " foi alterado com sucesso!", HttpStatus.ACCEPTED);
    }

    public ArrayList<TableEstablishment> allTableEstablishments() {
        return tableEstablishmentRepository.findAll();
    }

    public TableEstablishment table(Long id) {
        return tableEstablishmentRepository.findTableEstablishmentById(id);
    }

}
