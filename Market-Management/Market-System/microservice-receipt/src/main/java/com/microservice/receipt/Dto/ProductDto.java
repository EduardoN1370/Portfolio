package com.microservice.receipt.Dto;

import lombok.Data;

@Data
public class ProductDto {
    private int id;
    private String nameProduct;
    private double unitPrice;
}
