package com.microservice.receipt.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private String id ;
    private String productName;
    private int quantity;
    private double unitPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id")  // Esta columna será la clave foránea
    @JsonIgnore
    private Receipt receipt;
}
