package br.com.mysafeestablishmentcompany.controller;

import br.com.mysafeestablishmentcompany.domain.Product;
import br.com.mysafeestablishmentcompany.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController()
@RequestMapping("/private/owner")
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ArrayList<Product> allProducts(){
        return productService.allProducts();
    }

    @GetMapping("/product")
    public Product productById(@RequestParam() Long id){
        return productService.product(id);
    }

    @PostMapping("product/register")
    public Product registerProduct(@RequestBody Product product){
        return productService.register(product);
    }

    @DeleteMapping("/product/delete")
    public String delectProduct(@RequestParam() Long id){
        return productService.delete(id);
    }

    @PutMapping("/product/update")
    public Product updateProduct(@RequestBody Product product){
        return productService.update(product);
    }


}
