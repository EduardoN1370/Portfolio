package com.microservice.receipt.Controller;

import com.microservice.receipt.Dto.ReceiptDto;
import com.microservice.receipt.Entity.Receipt;
import com.microservice.receipt.Service.ServiceInterfaceReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/receipt")
public class ReceiptController {

    @Autowired
    private ServiceInterfaceReceipt serviceInterfaceReceipt;

    @PostMapping("/purchase")
    public ResponseEntity purchaseProducts(@RequestBody ReceiptDto receipt) {
      serviceInterfaceReceipt.createReceipt(receipt);
      return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Receipt> getReceipt(@PathVariable("id") Long id) {
        Receipt receipt= serviceInterfaceReceipt.getReceipt(id);
/*
*/
        return ResponseEntity.status(HttpStatus.OK).body(receipt);

    }





}
