package com.microservice.inventory.Service;

import com.microservice.inventory.Entity.Inventory;

public interface InventoryServiceRepository {


    void newInventory(Inventory inventory);

    void addProdct(String nameProduct,int quantity);

    void removeProdct(String nameProduct,int quantity);

    Inventory getInventory(String nameProduct);


}
