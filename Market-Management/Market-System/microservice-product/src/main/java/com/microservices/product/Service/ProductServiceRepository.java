package com.microservices.product.Service;

import com.microservices.product.Entity.Product;

import java.util.List;

public interface ProductServiceRepository {

    void save(Product product);
    Product getProductById(int id);
    List<Product> getAllProducts();
    Product getProductByName(String name);
}
