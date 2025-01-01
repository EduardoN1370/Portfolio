package com.microservice.receipt.Client;

import com.microservice.receipt.Dto.InventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="mscv-inventory",url="localhost:7090/api/inventory")
public interface InventoryClient {
    @GetMapping
    InventoryDto getInventory(@RequestParam String name);
}
