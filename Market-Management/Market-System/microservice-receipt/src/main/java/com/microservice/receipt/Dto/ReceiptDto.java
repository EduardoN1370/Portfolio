package com.microservice.receipt.Dto;

import com.microservice.receipt.Entity.ProductsReceipt;
import lombok.Data;

import java.util.List;
@Data
public class ReceiptDto {
    private List<RequestDto> listProducts;

}
