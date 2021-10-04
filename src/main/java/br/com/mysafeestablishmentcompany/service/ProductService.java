package br.com.mysafeestablishmentcompany.service;


import br.com.mysafeestablishmentcompany.domain.Product;
import br.com.mysafeestablishmentcompany.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<Product> register(Product product) {
        Product productDTO = productRepository.save(product);
        if (Objects.isNull(productDTO.getId())) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<String> delete(long productId) {
        Product productDTO = productRepository.findProductById(productId);
        if (Objects.isNull(productDTO)) {
            return new ResponseEntity<>("O produto: " + productId + " não foi encontrado!", HttpStatus.NOT_FOUND);
        }
        productRepository.delete(productDTO);
        productDTO = productRepository.findProductById(productId);
        if (Objects.nonNull(productDTO)) {
            return new ResponseEntity<>("O produto: " + productId + " foi deletado!", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("O produto: " + productId + " foi deletado com sucesso!", HttpStatus.ACCEPTED);
    }

    public ResponseEntity<Product> update(Product product) {
        Product productDTO = productRepository.findProductById(product.getId());
        if (Objects.isNull(productDTO)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        productRepository.save(product);
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<ArrayList<Product>> allProducts() {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.ACCEPTED);
    }

    public ResponseEntity<Product> product(Long id) {
        return new ResponseEntity<>(productRepository.findProductById(id), HttpStatus.ACCEPTED);
    }

}
