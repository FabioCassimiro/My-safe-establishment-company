package br.com.mysafeestablishmentcompany.controller;

import br.com.mysafeestablishmentcompany.domain.TableEstablishment;
import br.com.mysafeestablishmentcompany.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/private/owner/tables")
public class TableController {

    final TableService tableService;

    @Autowired
    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping()
    public ArrayList<TableEstablishment> allTables() {
        return tableService.allTableEstablishments();
    }

    @GetMapping("/{id}")
    public TableEstablishment tableById(@PathVariable Long id) {
        return tableService.table(id);
    }

    @PostMapping()
    public ResponseEntity<String> registerTable(@RequestBody TableEstablishment tableEstablishment) {
        return tableService.register(tableEstablishment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTable(@PathVariable Long id) {
        return tableService.delete(id);
    }

    @PutMapping()
    public ResponseEntity<String> updateTable(@RequestBody TableEstablishment tableEstablishment) {
        return tableService.update(tableEstablishment);
    }

}
