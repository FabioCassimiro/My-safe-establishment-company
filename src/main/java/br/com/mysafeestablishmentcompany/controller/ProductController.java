package br.com.mysafeestablishmentcompany.controller;

import br.com.mysafeestablishmentcompany.domain.Product;
import br.com.mysafeestablishmentcompany.exception.ProductNotFoundException;
import br.com.mysafeestablishmentcompany.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController()
@RequestMapping("/private/owner")
@CrossOrigin
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ArrayList<Product> allProducts() throws Exception {
        return productService.allProducts();
    }

    @GetMapping("/product/{id}")
    public Product productById(@PathVariable() Long id) throws ProductNotFoundException {
        return productService.product(id);
    }

    @PostMapping("product/register")
    public Product registerProduct(@RequestBody Product product) throws Exception {
        return productService.register(product);
    }

    @DeleteMapping("/product/delete/{id}")
    public String delectProduct(@PathVariable() Long id) throws Exception {
        return productService.delete(id);
    }

    @PutMapping("/product/update")
    public Product updateProduct(@RequestBody Product product) throws Exception {
        return productService.update(product);
    }


}
