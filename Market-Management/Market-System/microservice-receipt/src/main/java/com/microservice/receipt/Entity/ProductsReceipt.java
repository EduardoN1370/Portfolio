package com.microservice.receipt.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "products_receipt")
@AllArgsConstructor
@NoArgsConstructor
public class ProductsReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id ; ;
    private String productName;
    private int quantity;
    private double unitPrice;
    @ManyToOne
    @JoinColumn(name = "receipt_id")  // Esta columna será la clave foránea
    private Receipt receipt;
}
