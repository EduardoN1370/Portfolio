package com.microservice.receipt.Service;

import com.microservice.receipt.Client.InventoryClient;
import com.microservice.receipt.Client.ReceiptClient;
import com.microservice.receipt.Dto.InventoryDto;
import com.microservice.receipt.Dto.ProductDto;
import com.microservice.receipt.Dto.RequestDto;
import com.microservice.receipt.Entity.ProductsReceipt;
import com.microservice.receipt.Entity.Receipt;
import com.microservice.receipt.Exceptions.BusinessException;
import com.microservice.receipt.Exceptions.RequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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
        int numberProducts=calculateNumberProducts(productsReceiptList);
        newReceipt.setTotalPrice(totalPrice);
        newReceipt.setNumberItems(numberProducts);
        newReceipt.setListProducts(productsReceiptList);
        return newReceipt;
    }

    public List<ProductsReceipt> getListProducts(List<RequestDto> listProduct){
        List<ProductsReceipt> products = new ArrayList<>();
        for(RequestDto iterator: listProduct){
            ProductDto productDto =receiptClient.getProduct(iterator.getProductName());
            if(productDto!=null){
                ProductsReceipt productsReceipt = mapToProductReceipt(productDto, iterator.getQuantity());
                products.add(productsReceipt);
            }
            else{
                throw new RequestException("P-400","The product "+ iterator.getProductName() +" does not exist");
            }
        }
        return products;
    }


    public void getInventory(List<ProductsReceipt> productsReceiptList) {
        for (ProductsReceipt productsReceipt : productsReceiptList) {
            InventoryDto inventoryDto = inventoryClient.getInventory(productsReceipt.getProductName());
            if (inventoryDto != null) {
                if (productsReceipt.getQuantity()>= inventoryDto.getQuantity()) {
                    String requiredProduct = "There is just " + inventoryDto.getQuantity() + " from " + productsReceipt.getProductName();
                    throw new BusinessException("P-400", HttpStatus.BAD_REQUEST, requiredProduct);
                }

            }
        }
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
