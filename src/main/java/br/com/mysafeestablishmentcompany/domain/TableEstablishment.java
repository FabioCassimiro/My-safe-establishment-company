package br.com.mysafeestablishmentcompany.domain;

import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.StringJoiner;

@Entity
@Table(name = "tables")
public class TableEstablishment extends AbstractEntity{

    @Column(
            nullable = false
    )
    @NonNull
    private String statusTable;
    @Column(
            nullable = false
    )
    @NonNull
    private String locationArea;
    @Column(
            nullable = true
    )
    @NonNull
    private Integer numberSeats;

    @NonNull
    public String getStatusTable() {
        return statusTable;
    }

    public void setStatusTable(String statusTable) {
        this.statusTable = statusTable;
    }

    @NonNull
    public String getLocationArea() {
        return locationArea;
    }

    public void setLocationArea(String locationArea) {
        this.locationArea = locationArea;
    }

    @NonNull
    public Integer getNumberSeats() {
        return numberSeats;
    }

    public void setNumberSeats(Integer numberSeats) {
        this.numberSeats = numberSeats;
    }

    public TableEstablishment() {
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TableEstablishment.class.getSimpleName() + "[", "]")
                .add("statusTable='" + statusTable + "'")
                .add("locationArea='" + locationArea + "'")
                .add("numberSeats=" + numberSeats)
                .toString();
    }
}
