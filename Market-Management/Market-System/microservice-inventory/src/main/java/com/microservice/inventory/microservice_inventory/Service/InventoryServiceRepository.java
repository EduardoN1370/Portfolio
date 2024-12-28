package com.microservice.inventory.microservice_inventory.Service;

import com.microservice.inventory.microservice_inventory.Entity.Inventory;

public interface InventoryServiceRepository {


    void newInventory(Inventory inventory);

    void addProdct(String nameProduct,int quantity);

    void removeProdct(String nameProduct,int quantity);



}
