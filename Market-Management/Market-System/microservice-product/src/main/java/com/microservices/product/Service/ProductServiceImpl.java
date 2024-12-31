package com.microservices.product.Service;

import com.microservices.product.Entity.Product;
import com.microservices.product.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductServiceRepository {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id).orElseThrow(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product getProductByName(String name) {
        return productRepository.findByNameProduct(name);
    }
}
