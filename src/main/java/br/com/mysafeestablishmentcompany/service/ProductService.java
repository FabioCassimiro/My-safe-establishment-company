package br.com.mysafeestablishmentcompany.service;


import br.com.mysafeestablishmentcompany.domain.Product;
import br.com.mysafeestablishmentcompany.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class ProductService {

    ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product register(Product product) {
        Product productDTO = productRepository.save(product);
        if (Objects.isNull(productDTO.getId())) {
            return null;
        }
        return product;
    }

    public String delete(long productId) {
        Product productDTO = productRepository.findProductById(productId);
        if (Objects.isNull(productDTO)) {
            return "O produto: " + productId + " não foi encontrado!";
        }
        productRepository.delete(productDTO);
        productDTO = productRepository.findProductById(productId);
        if (Objects.nonNull(productDTO)) {
            return "O produto: " + productId + " foi deletado!";
        }
        return "O produto: " + productId + " foi deletado com sucesso!";
    }

    public Product update(Product product) {
        Product productDTO = productRepository.findProductById(product.getId());
        if (Objects.isNull(productDTO)) {
            return null;
        }
        productRepository.save(product);
        return product;
    }

    public ArrayList<Product> allProducts() {
        return productRepository.findAll();
    }

    public Product product(Long id) {
        return productRepository.findProductById(id);
    }

}
