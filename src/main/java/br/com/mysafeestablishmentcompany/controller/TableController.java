package br.com.mysafeestablishmentcompany.controller;

import br.com.mysafeestablishmentcompany.api.response.MessageResponse;
import br.com.mysafeestablishmentcompany.domain.TableEstablishment;
import br.com.mysafeestablishmentcompany.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/private")
@CrossOrigin
public class TableController {

    final TableService tableService;

    @Autowired
    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping("/tables")
    public ArrayList<TableEstablishment> allTables() throws Exception {
        return tableService.allTableEstablishments();
    }

    @GetMapping("/table/{id}")
    public TableEstablishment tableById(@PathVariable Long id) throws Exception {
        return tableService.tableById(id);
    }

    @PostMapping("/table/register")
    public TableEstablishment registerTable(@RequestBody TableEstablishment tableEstablishment) throws Exception {
        return tableService.register(tableEstablishment);
    }

    @DeleteMapping("/table/delete/{id}")
    public MessageResponse deleteTable(@PathVariable Long id) throws Exception {
        return tableService.delete(id);
    }

    @PutMapping("/table/update")
    public TableEstablishment updateTable(@RequestBody TableEstablishment tableEstablishment) throws Exception {
        return tableService.update(tableEstablishment);
    }

    @GetMapping("/table")
    public List<TableEstablishment> tableByNumberSeats(@RequestParam(name = "numberSeats") Integer numberSeats) throws Exception {
        return tableService.tableByNumberSeats(numberSeats);
    }

}
