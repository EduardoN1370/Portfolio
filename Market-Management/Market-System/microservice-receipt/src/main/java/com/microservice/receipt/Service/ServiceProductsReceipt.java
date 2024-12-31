package com.microservice.receipt.Service;

import com.microservice.receipt.Client.ReceiptClient;
import com.microservice.receipt.Dto.ProductDto;
import com.microservice.receipt.Dto.RequestDto;
import com.microservice.receipt.Entity.ProductsReceipt;
import com.microservice.receipt.Entity.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceProductsReceipt {
    @Autowired
    private ReceiptClient receiptClient;




    public Receipt buildReceipt(List<ProductsReceipt> productsReceiptList) {


        Receipt newReceipt = new Receipt(null,0,0, LocalDateTime.now(),null);
        for(ProductsReceipt productsReceipt : productsReceiptList){
            productsReceipt.setReceipt(newReceipt);
        }
        double totalPrice=calculateTotalPrice(productsReceiptList);
        int numberProoducts=calculateNumberProducts(productsReceiptList);
        newReceipt.setTotalPrice(totalPrice);
        newReceipt.setNumberItems(numberProoducts);
        newReceipt.setListProducts(productsReceiptList);
        return newReceipt;
    }

    public List<ProductsReceipt> getListProducts(List<RequestDto> listProduct){
        List<ProductsReceipt> products = new ArrayList<>();
        for(RequestDto iterator: listProduct){
            ProductDto productDto =receiptClient.getProduct(iterator.getProductName());
            if(productDto!=null){
                ProductsReceipt productsReceipt = mapToProductReceipt(productDto, iterator.getQuantity());
                products.add(productsReceipt);
            }
        }
        return products;
    }

    public ProductsReceipt mapToProductReceipt(ProductDto productDto,int quantity) {
        ProductsReceipt productsReceipt = new ProductsReceipt(
                null,
                productDto.getNameProduct(),
                quantity,
                productDto.getUnitPrice(),
                null);

        return productsReceipt;

    }

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
