package br.com.mysafeestablishmentcompany.api.response;

import br.com.mysafeestablishmentcompany.domain.TableEstablishment;

import java.util.List;

public class TableEstablishmentResponse {

    List<TableEstablishment> tables;
    Integer numberTotalSeats;
    Integer numberUnavailableSeats;

    public TableEstablishmentResponse(List<TableEstablishment> tables, Integer numberTotalSeats, Integer numberUnavailableSeats) {
        this.tables = tables;
        this.numberTotalSeats = numberTotalSeats;
        this.numberUnavailableSeats = numberUnavailableSeats;
    }

    public List<TableEstablishment> getTables() {
        return tables;
    }

    public void setTables(List<TableEstablishment> tables) {
        this.tables = tables;
    }

    public Integer getNumberTotalSeats() {
        return numberTotalSeats;
    }

    public void setNumberTotalSeats(Integer numberTotalSeats) {
        this.numberTotalSeats = numberTotalSeats;
    }

    public Integer getNumberUnavailableSeats() {
        return numberUnavailableSeats;
    }

    public void setNumberUnavailableSeats(Integer numberUnavailableSeats) {
        this.numberUnavailableSeats = numberUnavailableSeats;
    }

    public TableEstablishmentResponse() {
    }
}
