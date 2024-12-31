package com.microservice.receipt.Dto;

import lombok.Data;

@Data
public class RequestDto {
    private String productName;
    private int quantity;
}
