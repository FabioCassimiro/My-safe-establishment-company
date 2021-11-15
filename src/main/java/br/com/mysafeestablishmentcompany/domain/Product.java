package br.com.mysafeestablishmentcompany.domain;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product extends AbstractEntity {

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;
    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String typeProduct;
    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String description;
    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String ingredients;
    @Column(
            nullable = false
    )
    private double value;
    @OneToOne(
            cascade = CascadeType.PERSIST
    )
    private ProductDetails productDetails;

    public Product() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(String typeProduct) {
        this.typeProduct = typeProduct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }

    public Product(String name, String typeProduct, String description, String ingredients, double value) {
        this.name = name;
        this.typeProduct = typeProduct;
        this.description = description;
        this.ingredients = ingredients;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", typeProduct='" + typeProduct + '\'' +
                ", description='" + description + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", value=" + value +
                '}';
    }
}
