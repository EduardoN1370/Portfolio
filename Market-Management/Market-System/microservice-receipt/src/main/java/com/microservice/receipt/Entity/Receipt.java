package com.microservice.receipt.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "receipt")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Receipt {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "receipt_id")
    private Long receiptId;

    private int numberItems;
    private double totalPrice;

    private LocalDateTime date ;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductsReceipt> listProducts;



}
