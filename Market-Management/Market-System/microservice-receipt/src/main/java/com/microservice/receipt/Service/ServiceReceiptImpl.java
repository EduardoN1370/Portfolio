package com.microservice.receipt.Service;

import com.microservice.receipt.Dto.ReceiptDto;
import com.microservice.receipt.Entity.Receipt;
import com.microservice.receipt.Repository.RepoReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ServiceReceiptImpl implements ServiceInterfaceReceipt{

    @Autowired
    private RepoReceipt repoReceipt;
    @Autowired
    private ServiceProductsReceipt serviceProductsReceipt;

    @Override
    public void createReceipt(ReceiptDto receipt) {

    double totalPrice=serviceProductsReceipt.calculateTotalPrice(receipt.getListProducts());
    int numberProoducts=serviceProductsReceipt.calculateNumberProducts(receipt.getListProducts());
    System.out.println("Hasta aca todo bien");

    Receipt newReceipt = Receipt.builder()
            .totalPrice(totalPrice)
            .date(LocalDateTime.now())
            .numberItems(numberProoducts)
            .listproducts(receipt.getListProducts())
            .build();
    

    repoReceipt.save(newReceipt);
    }

    @Override
    public Receipt getReceipt(Long id) {
        return repoReceipt.findById(id).orElse(null);
    }
}
