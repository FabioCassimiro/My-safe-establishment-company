package br.com.mysafeestablishmentcompany.service;

import br.com.mysafeestablishmentcompany.api.response.MessageResponse;
import br.com.mysafeestablishmentcompany.api.response.TableEstablishmentResponse;
import br.com.mysafeestablishmentcompany.domain.CompanyUtils;
import br.com.mysafeestablishmentcompany.domain.TableEstablishment;
import br.com.mysafeestablishmentcompany.exception.TableEstablishmentNotFoundException;
import br.com.mysafeestablishmentcompany.repository.TableEstablishmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class TableService {

    final TableEstablishmentRepository tableEstablishmentRepository;

    @Autowired
    public TableService(TableEstablishmentRepository tableEstablishmentRepository) {
        this.tableEstablishmentRepository = tableEstablishmentRepository;
    }

    public TableEstablishment register(TableEstablishment tableEstablishment) throws Exception {
        if (tableEstablishment.getNumberSeats() < 0) {
            throw new Exception("A quantidade de assentos deve ser maior que 0");
        }
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


        System.out.println();
        if (Objects.nonNull(tableDTO)) {
            throw new Exception(String.format("Mesa: %s não foi deletada", id));
        }
        return new MessageResponse("Mesa: " + id + " foi deletada com sucesso!");
    }

    public TableEstablishment update(TableEstablishment tableEstablishment) throws Exception {
        if (tableEstablishment.getNumberSeats() < 0) {
            throw new Exception("A quantidade de assentos deve ser maior que 0");
        }
        TableEstablishment tableDTO = tableEstablishmentRepository.findTableEstablishmentById(tableEstablishment.getId());
        if (Objects.isNull(tableDTO)) {
            throw new TableEstablishmentNotFoundException(String.format("Mesa %s não encontrada", tableEstablishment.getId()));
        }
        return tableEstablishmentRepository.save(tableEstablishment);
    }

    public TableEstablishmentResponse allTableEstablishments() throws Exception {
        ArrayList<TableEstablishment> tablesDTO = tableEstablishmentRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        if (Objects.isNull(tablesDTO)) {
            throw new TableEstablishmentNotFoundException("Nenhuma mesa encontrada");
        }
        int avaliable  = tableEstablishmentRepository.findTableEstablishmentByStatusTable(CompanyUtils.TABLE_STATUS_NOT_AVAILABLE).stream().mapToInt(TableEstablishment::getNumberSeats).sum();
        int total = tablesDTO.stream().mapToInt(TableEstablishment::getNumberSeats).sum();
        return new TableEstablishmentResponse(tablesDTO,total,avaliable);
    }

    public TableEstablishment tableById(Long id) throws Exception {
        TableEstablishment tableDTO = tableEstablishmentRepository.findTableEstablishmentById(id);
        if (Objects.isNull(tableDTO)) {
            throw new TableEstablishmentNotFoundException(String.format("Mesa %s não encontrada", id));
        }
        return tableDTO;
    }

    public List<TableEstablishment> tableByNumberSeats(Integer numberSeats) throws Exception {
        List<TableEstablishment> tableEstablishments =
                tableEstablishmentRepository.findTableEstablishmentByNumberSeatsInAndStatusTable(
                        Arrays.asList(numberSeats, numberSeats + 1),
                        CompanyUtils.TABLE_STATUS_AVALIABLE
                );
        if (tableEstablishments.isEmpty()) {
            throw new Exception("Não ha mesa disponivel");
        }
        return tableEstablishments;
    }

}
