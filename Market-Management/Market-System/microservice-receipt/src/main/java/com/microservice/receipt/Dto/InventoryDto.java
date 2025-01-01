package com.microservice.receipt.Dto;

import lombok.Data;

@Data
public class InventoryDto {
    private int id;
    private String nameProduct;
    private int quantity;
}
