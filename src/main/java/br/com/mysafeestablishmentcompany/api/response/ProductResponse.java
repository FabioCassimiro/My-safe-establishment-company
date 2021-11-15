package br.com.mysafeestablishmentcompany.api.response;

import br.com.mysafeestablishmentcompany.domain.Product;
import br.com.mysafeestablishmentcompany.domain.ProductDetails;

public class ProductResponse {

    private Product product;
    private ProductDetails productDetails;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }

    public ProductResponse() {
    }

    public ProductResponse(Product product, ProductDetails productDetails) {
        this.product = product;
        this.productDetails = productDetails;
    }
}
