package com.microservice.receipt.Service;

import com.microservice.receipt.Dto.ReceiptDto;
import com.microservice.receipt.Entity.Receipt;

public interface ServiceInterfaceReceipt {

    public void createReceipt(ReceiptDto receipt);

    public void testRecipe(Receipt receipt);

    public Receipt getReceipt(Long id);

}
