package com.microservices.product.Repository;
import com.microservices.product.Dto.InventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="mscv-inventory",url="localhost:9090/api/inventory")
public interface ClientInventory {

        @PostMapping("/createProduct")
        void createProduct(@RequestBody InventoryDto inventoryDto);

}
