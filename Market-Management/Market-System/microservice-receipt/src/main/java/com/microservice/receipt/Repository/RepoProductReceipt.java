package com.microservice.receipt.Repository;

import com.microservice.receipt.Entity.ProductsReceipt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoProductReceipt extends CrudRepository<ProductsReceipt,Long> {
}
