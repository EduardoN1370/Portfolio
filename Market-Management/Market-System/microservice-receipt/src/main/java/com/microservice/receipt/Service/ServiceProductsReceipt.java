package com.microservice.receipt.Service;

import com.microservice.receipt.Entity.ProductsReceipt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceProductsReceipt {



    public double calculateTotalPrice(List<ProductsReceipt> list){
        double sum = 0;
        for(ProductsReceipt pr:list){
            sum= sum + (pr.getUnitPrice()* pr.getQuantity());
        }
        return sum;

    }

    public int calculateNumberProducts(List<ProductsReceipt> list){
        int sum = 0;
        for(ProductsReceipt pr:list){
            sum= sum + pr.getQuantity();
        }
        return sum;
    }
}
