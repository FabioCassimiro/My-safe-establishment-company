package br.com.mysafeestablishmentcompany.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tables")
public class TableEstablishment extends AbstractEntity{

    private String statusTable;
    private String locationArea;

    public String getStatusTable() {
        return statusTable;
    }

    public void setStatusTable(String statusTable) {
        this.statusTable = statusTable;
    }

    public String getLocationArea() {
        return locationArea;
    }

    public void setLocationArea(String locationArea) {
        this.locationArea = locationArea;
    }

    public TableEstablishment() {
    }

    @Override
    public String toString() {
        return "TableEstablishment{" +
                "statusTable='" + statusTable + '\'' +
                ", locationArea='" + locationArea + '\'' +
                '}';
    }
}
