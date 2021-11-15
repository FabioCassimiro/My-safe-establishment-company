package br.com.mysafeestablishmentcompany.api.request;

import br.com.mysafeestablishmentcompany.domain.Product;

public class CreateProductRequest {

    private Product product;
    private String imageEncoded;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getImageEncoded() {
        return imageEncoded;
    }

    public void setImageEncoded(String imageEncoded) {
        this.imageEncoded = imageEncoded;
    }

    public CreateProductRequest() {
    }
}
