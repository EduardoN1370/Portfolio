package com.microservice.receipt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroserviceReceiptApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceReceiptApplication.class, args);
	}

}
