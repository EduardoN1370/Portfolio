package com.microservice.receipt.Repository;

import com.microservice.receipt.Entity.Receipt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoReceipt extends CrudRepository<Receipt, Long> {
}
