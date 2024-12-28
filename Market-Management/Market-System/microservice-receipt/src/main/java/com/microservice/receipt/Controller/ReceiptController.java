package com.microservice.receipt.Controller;

import com.microservice.receipt.Service.ServiceInterfaceReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/receipt")
public class ReceiptController {

    @Autowired
    private ServiceInterfaceReceipt serviceInterfaceReceipt;

    @PostMapping("/purchase")
    public ResponseEntity purchaseProducts(){
        
        return ResponseEntity.ok().build();
    }





}
