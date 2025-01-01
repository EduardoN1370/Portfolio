package com.microservice.inventory.microservice_inventory.Controller;

import com.microservice.inventory.microservice_inventory.Entity.Inventory;
import com.microservice.inventory.microservice_inventory.Service.InventoryServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
   private InventoryServiceRepository inventoryServiceRepository;
    @PostMapping("/createProduct")
    public void newInventory(@RequestBody Inventory inventory) {
        inventoryServiceRepository.newInventory(inventory);
    }


    @PutMapping("/addProduct")
    public  void addProduct(@RequestBody Inventory inventory){
        inventoryServiceRepository.addProdct(inventory.getNameProduct(), inventory.getQuantity());
    }
    @PutMapping("/removeProduct")
    public  void removeProduct(@RequestBody Inventory inventory){
        inventoryServiceRepository.removeProdct(inventory.getNameProduct(), inventory.getQuantity());
    }
    @GetMapping()
    public Inventory getInventory(@RequestParam String nameProduct) {
        return inventoryServiceRepository.getInventory(nameProduct);
    }


}
