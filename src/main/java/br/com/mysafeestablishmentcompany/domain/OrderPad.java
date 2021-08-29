package br.com.mysafeestablishmentcompany.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "orderpads")
public class OrderPad extends AbstractEntity{

    private long customerId;
    private String customerName;
    private long tableId;
    private int quantityCustomer;
    private String payment;
    private String status;
    private double tip;
    private double rate;
    private double value;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public int getQuantityCustomer() {
        return quantityCustomer;
    }

    public void setQuantityCustomer(int quantityCustomer) {
        this.quantityCustomer = quantityCustomer;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTip() {
        return tip;
    }

    public void setTip(double tip) {
        this.tip = tip;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public OrderPad() {
    }

    @Override
    public String toString() {
        return "OrderPad{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", tableId=" + tableId +
                ", quantityCustomer=" + quantityCustomer +
                ", payment='" + payment + '\'' +
                ", status='" + status + '\'' +
                ", tip=" + tip +
                ", rate=" + rate +
                ", value=" + value +
                '}';
    }
}
