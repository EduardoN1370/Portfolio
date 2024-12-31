package com.microservice.receipt.Client;

import com.microservice.receipt.Dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="mscv-product",url="localhost:8090/api/products")
public interface ReceiptClient {
    @GetMapping
    ProductDto getProduct(@RequestParam("name") String name );
}
