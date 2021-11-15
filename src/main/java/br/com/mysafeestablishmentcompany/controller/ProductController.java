package br.com.mysafeestablishmentcompany.controller;

import br.com.mysafeestablishmentcompany.api.imgur.ImgurClient;
import br.com.mysafeestablishmentcompany.api.request.CreateProductRequest;
import br.com.mysafeestablishmentcompany.api.response.MessageResponse;
import br.com.mysafeestablishmentcompany.domain.Product;
import br.com.mysafeestablishmentcompany.exception.ProductNotFoundException;
import br.com.mysafeestablishmentcompany.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController()
@RequestMapping("/private")
@CrossOrigin
public class ProductController {

    private ProductService productService;
    ImgurClient imgurClient = new ImgurClient();

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

    @PostMapping(value = "product/register")
    public Product registerProduct(@RequestBody CreateProductRequest productRequest) throws Exception {
        return productService.registerProduct(productRequest);
    }

    @DeleteMapping("/product/delete/{id}")
    public MessageResponse delectProduct(@PathVariable() Long id) throws Exception {
        return productService.delete(id);
    }

    @PutMapping("/product/update")
    public Product updateProduct(@RequestBody Product product) throws Exception {
        return productService.update(product);
    }

}
