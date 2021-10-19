package br.com.mysafeestablishmentcompany.service;


import br.com.mysafeestablishmentcompany.domain.Product;
import br.com.mysafeestablishmentcompany.exception.ProductNotFoundException;
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

    public Product register(Product product) throws Exception {
        Product productDTO = productRepository.save(product);
        if (Objects.isNull(productDTO.getId())) {
            throw new Exception(String.format("Não foi possivel criar o produto %s", product.getName()));
        }
        return product;
    }

    public String delete(long productId) throws Exception {
        Product productDTO = productRepository.findProductById(productId);
        if (Objects.isNull(productDTO)) {
            throw new ProductNotFoundException(String.format("Produto %s não encontrado ", productId));
        }
        productRepository.delete(productDTO);
        productDTO = productRepository.findProductById(productId);
        if (Objects.nonNull(productDTO)) {
            throw new Exception(String.format("O produto %s não foi deletado", productDTO.getName()));
        }
        return "O produto: " + productId + " foi deletado com sucesso!";
    }

    public Product update(Product product) throws Exception {
        Product productDTO = productRepository.findProductById(product.getId());
        if (Objects.isNull(productDTO)) {
            throw new ProductNotFoundException(String.format("Produto %s - %s não foi encontrado ", product.getId(), product.getName()));
        }
        productRepository.save(product);
        return product;
    }

    public ArrayList<Product> allProducts() throws Exception {
        ArrayList<Product> products = productRepository.findAll();
        if (Objects.isNull(products)) {
            throw new ProductNotFoundException("Nenhum produto não foi encontrado");
        }
        return products;
    }

    public Product product(Long id) throws ProductNotFoundException {
        Product product = productRepository.findProductById(id);
        if (Objects.isNull(product)) {
            throw new ProductNotFoundException("Produto não encontrado");
        }
        return product;
    }

}
