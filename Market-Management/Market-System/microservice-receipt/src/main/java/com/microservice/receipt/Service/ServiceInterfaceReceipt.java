package com.microservice.receipt.Service;

import com.microservice.receipt.Dto.InventoryDto;
import com.microservice.receipt.Dto.ReceiptDto;
import com.microservice.receipt.Entity.ProductsReceipt;
import com.microservice.receipt.Entity.Receipt;

import java.util.List;
import java.util.Optional;

public interface ServiceInterfaceReceipt {

    public void createReceipt(ReceiptDto receipt);

    public Receipt getReceipt(Long id);

    public List<Optional<String>> getInventory(List<ProductsReceipt> productsReceiptList);

}
