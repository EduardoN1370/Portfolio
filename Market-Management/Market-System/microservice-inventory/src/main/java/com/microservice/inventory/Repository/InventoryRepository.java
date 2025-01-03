package com.microservice.inventory.Repository;

import com.microservice.inventory.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    Inventory findByNameProduct(String name);

}
