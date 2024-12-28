package com.microservice.receipt.Service;

import com.microservice.receipt.Entity.Receipt;
import com.microservice.receipt.Repository.RepoReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceReceiptImpl implements ServiceInterfaceReceipt{

    @Autowired
    private RepoReceipt repoReceipt;

    @Override
    public void createReceipt(Receipt receipt) {
    repoReceipt.save(receipt);
    }

    @Override
    public Receipt getReceipt(Long id) {
        return repoReceipt.findById(id).orElse(null);
    }
}
