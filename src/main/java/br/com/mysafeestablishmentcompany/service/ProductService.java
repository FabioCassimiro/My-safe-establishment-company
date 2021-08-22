package br.com.mysafeestablishmentcompany.service;


import br.com.mysafeestablishmentcompany.domain.Product;
import br.com.mysafeestablishmentcompany.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {

    ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public ResponseEntity<String> register(Product product){
        productRepository.save(product);
        return new ResponseEntity<>("O produto: " + product.getId()+ " - " + product.getName() + " foi criado com sucesso!", HttpStatus.ACCEPTED);
    }

    public ResponseEntity<String> delete(Product product){
        productRepository.delete(product);
        return new ResponseEntity<>("O produto: " + product.getId()+ " - " + product.getName() + " foi deletado com sucesso!", HttpStatus.ACCEPTED);
    }

    public ResponseEntity<String> update(Product product){
        productRepository.save(product);
        return new ResponseEntity<>("O produto: " + product.getId()+ " - " + product.getName() + " foi alterado com sucesso!", HttpStatus.ACCEPTED);
    }

    public ArrayList<Product> allProducts(){
        return productRepository.findAll();
    }

    public Product product(Long id){
        return productRepository.findProductById(id);
    }




}
