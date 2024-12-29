package com.microservice.receipt.Service;

import com.microservice.receipt.Dto.ReceiptDto;
import com.microservice.receipt.Entity.ProductsReceipt;
import com.microservice.receipt.Entity.Receipt;
import com.microservice.receipt.Repository.RepoReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceReceiptImpl implements ServiceInterfaceReceipt{

    @Autowired
    private RepoReceipt repoReceipt;
    @Autowired
    private ServiceProductsReceipt serviceProductsReceipt;

    @Override
    public void createReceipt(ReceiptDto receiptDto) {

    Receipt newReceipt = new Receipt(null,0,0,LocalDateTime.now(),null);
    List<ProductsReceipt> receiptDtoList = receiptDto.getListProducts();
    for(ProductsReceipt productsReceipt : receiptDtoList){
        productsReceipt.setReceipt(newReceipt);
    }
    double totalPrice=serviceProductsReceipt.calculateTotalPrice(receiptDtoList);
    int numberProoducts=serviceProductsReceipt.calculateNumberProducts(receiptDtoList);
    newReceipt.setTotalPrice(totalPrice);
    newReceipt.setNumberItems(numberProoducts);
    newReceipt.setListProducts(receiptDtoList);
    System.out.println("Hasta aca todo bien");


    repoReceipt.save(newReceipt);


    }

    @Override
    public void testRecipe(Receipt receipt) {
        repoReceipt.save(receipt);
    }


    @Override
    public Receipt getReceipt(Long id) {
        return repoReceipt.findById(id).orElse(null);
    }
}
