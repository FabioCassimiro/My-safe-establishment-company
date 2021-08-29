package br.com.mysafeestablishmentcompany.controller;

import br.com.mysafeestablishmentcompany.domain.Product;
import br.com.mysafeestablishmentcompany.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController()
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/private/owner/products")
    public ArrayList<Product> allProducts(){
        return productService.allProducts();
    }

    @GetMapping("/private/owner/product")
    public Product productsById(@RequestParam() Long id){
        return productService.product(id);
    }

    @PostMapping("/private/owner/product/register")
    public ResponseEntity<String> registerProduct(@RequestBody Product product){
        return productService.register(product);
    }

    @DeleteMapping("/private/owner/product/delete")
    public ResponseEntity<String> delectProduct(@RequestBody Product product){
        return productService.delete(product);
    }

    @PutMapping("/private/owner/product/update")
    public ResponseEntity<String> updateProduct(@RequestBody Product product){
        return productService.update(product);
    }


}
