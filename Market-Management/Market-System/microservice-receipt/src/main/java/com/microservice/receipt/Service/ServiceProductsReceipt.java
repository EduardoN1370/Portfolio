package com.microservice.receipt.Service;

import com.microservice.receipt.Client.InventoryClient;
import com.microservice.receipt.Client.ReceiptClient;
import com.microservice.receipt.Dto.InventoryDto;
import com.microservice.receipt.Dto.ProductDto;
import com.microservice.receipt.Dto.RequestDto;
import com.microservice.receipt.Entity.ProductsReceipt;
import com.microservice.receipt.Entity.Receipt;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceProductsReceipt {
    @Autowired
    private ReceiptClient receiptClient;

    @Autowired
    private InventoryClient inventoryClient;




    public Receipt buildReceipt(List<ProductsReceipt> productsReceiptList) {
        Receipt newReceipt = new Receipt(null,0,0, LocalDateTime.now(),null);
        for(ProductsReceipt productsReceipt : productsReceiptList){
            productsReceipt.setReceipt(newReceipt);
        }
        double totalPrice=calculateTotalPrice(productsReceiptList);
        int numberProoducts=calculateNumberProducts(productsReceiptList);
        newReceipt.setTotalPrice(totalPrice);
        newReceipt.setNumberItems(numberProoducts);
        newReceipt.setListProducts(productsReceiptList);
        return newReceipt;
    }

    public List<ProductsReceipt> getListProducts(List<RequestDto> listProduct){
        List<ProductsReceipt> products = new ArrayList<>();
        for(RequestDto iterator: listProduct){
            ProductDto productDto =receiptClient.getProduct(iterator.getProductName());
            if(productDto!=null){
                System.out.println("Abajo esta el producto");
                System.out.println(productDto);
                ProductsReceipt productsReceipt = mapToProductReceipt(productDto, iterator.getQuantity());
                products.add(productsReceipt);
            }
        }
        return products;
    }

    public List<Optional<String>> inventoryList(List<ProductsReceipt> productsReceiptList){
        List<Optional<String>> inventoryList = new ArrayList<>();
        for(ProductsReceipt productsReceipt : productsReceiptList) {
            Optional<String> requiredProduct=  getInventory(productsReceipt.getProductName(),productsReceipt.getQuantity());
            requiredProduct.ifPresent(value -> inventoryList.add(requiredProduct));
        }
        return inventoryList;

    }


    public Optional<String> getInventory(String productName, int quantityRequired){
            String  requiredProduct;
            InventoryDto inventoryDto = inventoryClient.getInventory(productName);
            if (inventoryDto != null) {
                if (inventoryDto.getQuantity() >= quantityRequired) {

                }
                 requiredProduct= "There is just "+inventoryDto.getQuantity()+" from "+productName;
                return Optional.of(requiredProduct);
            }

            requiredProduct ="We don't find the product "+productName;
        return Optional.of(requiredProduct);

        }



    public ProductsReceipt mapToProductReceipt(ProductDto productDto,int quantity) {
        ProductsReceipt productsReceipt = new ProductsReceipt(
                null,
                productDto.getNameProduct(),
                quantity,
                productDto.getPrice(),
                null);

        return productsReceipt;

    }

    public double calculateTotalPrice(List<ProductsReceipt> list){
        double sum = 0;
        for(ProductsReceipt pr:list){
            sum= sum + (pr.getUnitPrice()* pr.getQuantity());
        }
        return sum;

    }

    public int calculateNumberProducts(List<ProductsReceipt> list){
        int sum = 0;
        for(ProductsReceipt pr:list){
            sum= sum + pr.getQuantity();
        }
        return sum;
    }
}
