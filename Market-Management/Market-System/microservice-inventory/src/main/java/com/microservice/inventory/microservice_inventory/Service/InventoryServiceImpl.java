package com.microservice.inventory.microservice_inventory.Service;

import com.microservice.inventory.microservice_inventory.Entity.Inventory;
import com.microservice.inventory.microservice_inventory.Repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryServiceRepository{
    @Autowired
    private InventoryRepository inventoryRepository;


    @Override
    public void newInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
    }

    @Override
    public void addProdct(String nameProduct,int quantity) {
        Inventory oldInventory= inventoryRepository.findByNameProduct(nameProduct);
        if(oldInventory!=null){
            oldInventory.setQuantity(oldInventory.getQuantity()+quantity);
            inventoryRepository.save(oldInventory);
        }

    }

    @Override
    public void removeProdct(String nameProduct, int quantity) {
        Inventory oldInventory= inventoryRepository.findByNameProduct(nameProduct);
        if(oldInventory!=null){
            oldInventory.setQuantity(oldInventory.getQuantity()-quantity);
            inventoryRepository.save(oldInventory);
        }

    }
}
