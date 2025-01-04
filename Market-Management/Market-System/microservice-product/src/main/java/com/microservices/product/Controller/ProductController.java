package com.microservices.product.Controller;
import com.microservices.product.Dto.InventoryDto;
import com.microservices.product.Dto.ProductDto;
import com.microservices.product.Entity.Product;
import com.microservices.product.Repository.ClientInventory;
import com.microservices.product.Service.ProductServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    ProductServiceRepository productServiceRepository;
    @Autowired
    ClientInventory clientInventory;

    @PostMapping("/create")
    public ResponseEntity<Product> create (@RequestBody ProductDto productdto) {

        System.out.println("Abajo esta el nombre");
        System.out.println(productdto.getNameProduct());
        InventoryDto inventoryDto = new InventoryDto(productdto.getNameProduct(),productdto.getQuantity());
        productServiceRepository.save(Product.builder()
                        .nameProduct(productdto.getNameProduct())
                        .price(productdto.getPrice())
                .build());
        clientInventory.createProduct(inventoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAll () {
        List<Product> productList = productServiceRepository.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        Product product = productServiceRepository.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
    @GetMapping()
    public ResponseEntity<Product> getProductByName(@RequestParam String nameProduct) {
        System.out.println(nameProduct);
        Product product = productServiceRepository.getProductByName(nameProduct);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }



}
