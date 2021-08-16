package br.com.mysafeestablishmentcompany.repository;


import br.com.mysafeestablishmentcompany.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ProductRepository extends CrudRepository<Product,Long> {

    Product findProductById(Long id);

    ArrayList<Product> findAll();
}
