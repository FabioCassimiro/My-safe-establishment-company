package br.com.mysafeestablishmentcompany.service;

import br.com.mysafeestablishmentcompany.api.response.MessageResponse;
import br.com.mysafeestablishmentcompany.domain.TableEstablishment;
import br.com.mysafeestablishmentcompany.exception.TableEstablishmentNotFoundException;
import br.com.mysafeestablishmentcompany.repository.TableEstablishmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public TableEstablishment register(TableEstablishment tableEstablishment) throws Exception {
        TableEstablishment tableDTO = tableEstablishmentRepository.save(tableEstablishment);
        if (Objects.isNull(tableDTO.getId())) {
            throw new Exception("Não foi possivel cadastrar a mesa");
        }
        return tableEstablishmentRepository.save(tableEstablishment);
    }

    public MessageResponse delete(Long id) throws Exception {
        TableEstablishment tableDTO = tableEstablishmentRepository.findTableEstablishmentById(id);
        if (Objects.isNull(tableDTO.getId())) {
            throw new TableEstablishmentNotFoundException(String.format("Mesa %s não encontrada", id));
        }
        tableEstablishmentRepository.delete(tableDTO);
        tableDTO = tableEstablishmentRepository.findTableEstablishmentById(id);
        if (Objects.nonNull(tableDTO)) {
            throw new Exception(String.format("Mesa: %s não foi deletada", id));
        }
        return new MessageResponse("Mesa: " + id + " foi deletada com sucesso!");
    }

    public TableEstablishment update(TableEstablishment tableEstablishment) throws Exception {
        TableEstablishment tableDTO = tableEstablishmentRepository.findTableEstablishmentById(tableEstablishment.getId());
        if (Objects.isNull(tableDTO)) {
            throw new TableEstablishmentNotFoundException(String.format("Mesa %s não encontrada", tableEstablishment.getId()));
        }
        return tableEstablishmentRepository.save(tableEstablishment);
    }

    public ArrayList<TableEstablishment> allTableEstablishments() throws Exception {
        ArrayList<TableEstablishment> tablesDTO = tableEstablishmentRepository.findAll();
        if (Objects.isNull(tablesDTO)) {
            throw new TableEstablishmentNotFoundException("Nenhuma mesa encontrada");
        }
        return tablesDTO;
    }

    public TableEstablishment tableById(Long id) throws Exception {
        TableEstablishment tableDTO = tableEstablishmentRepository.findTableEstablishmentById(id);
        if (Objects.isNull(tableDTO)) {
            throw new TableEstablishmentNotFoundException(String.format("Mesa %s não encontrada", id));
        }
        return tableDTO;
    }

}
