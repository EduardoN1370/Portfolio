package com.microservice.receipt.Service;

import com.microservice.receipt.Entity.Receipt;
import org.springframework.stereotype.Service;

public interface ServiceInterfaceReceipt {

    public void createReceipt(Receipt receipt);

    public Receipt getReceipt(Long id);

}
