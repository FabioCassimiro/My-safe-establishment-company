package br.com.mysafeestablishmentcompany.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.StringJoiner;

@Entity
@Table(name = "orders")
public class Order extends AbstractEntity{

    private long orderPadId;
    private long productId;
    private String productName;
    private int quantity;
    private double value;
    private String note;
    private String status;

    public long getOrderPadId() {
        return orderPadId;
    }

    public void setOrderPadId(long orderPadId) {
        this.orderPadId = orderPadId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Order(long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Order() {
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]")
                .add("orderPadId=" + orderPadId)
                .add("productId=" + productId)
                .add("productName='" + productName + "'")
                .add("quantity=" + quantity)
                .add("value=" + value)
                .add("note='" + note + "'")
                .add("status='" + status + "'")
                .toString();
    }
}
