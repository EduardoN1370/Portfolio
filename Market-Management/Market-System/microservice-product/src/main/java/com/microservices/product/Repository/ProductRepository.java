package com.microservices.product.Repository;

import com.microservices.product.Entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    Product findByNameProduct(String name);
}
