package com.microservice.receipt.Service;

import com.microservice.receipt.Dto.ProductDto;
import com.microservice.receipt.Dto.ReceiptDto;
import com.microservice.receipt.Dto.RequestDto;
import com.microservice.receipt.Entity.ProductsReceipt;
import com.microservice.receipt.Entity.Receipt;
import com.microservice.receipt.Repository.RepoReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceReceiptImpl implements ServiceInterfaceReceipt{

    @Autowired
    private RepoReceipt repoReceipt;
    @Autowired
    private ServiceProductsReceipt serviceProductsReceipt;

    @Override
    public void createReceipt(ReceiptDto receiptDto) {
    List<RequestDto> receiptDtoList = receiptDto.getListProducts();
    List<ProductsReceipt> listProductReceipt = serviceProductsReceipt.getListProducts(receiptDtoList);
    Receipt receipt = serviceProductsReceipt.buildReceipt(listProductReceipt);
    repoReceipt.save(receipt);

    }




    @Override
    public Receipt getReceipt(Long id) {
        return repoReceipt.findById(id).orElse(null);
    }
}
