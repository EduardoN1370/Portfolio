package com.microservices.product;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.microservices.product.Dto.ProductDto;
import com.microservices.product.Entity.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MicroserviceProductApplicationTests {
/*
	@Autowired
    TestRestTemplate restTemplate;


	@Test
	void shouldCreateProduct() {
		ProductDto product = new ProductDto("Chocolate",12.45,254);
		ResponseEntity<String> response = restTemplate.postForEntity("/api/products/create", product, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(201);
	}

	@Test
    void shouldGetAllProducts() {
		ResponseEntity<String> response = restTemplate.getForEntity("/api/products/all", String.class);
		DocumentContext	documentContext = JsonPath.parse(response.getBody());
		int legth = documentContext.read("$.length()");
		assertThat(legth).isEqualTo(5);

		List<String> listProduct= documentContext.read("$..productName");
		assertThat(listProduct).contains("Rice","Soap","Detergent","Toilet Paper","Cooking oil");

	}

	@Test
	void shouldUGetAByIdProduct() {
		ResponseEntity<String> response = restTemplate.getForEntity("/products/5", String.class);
		DocumentContext	documentContext = JsonPath.parse(response.getBody());
		String product= documentContext.read("$.nameProduct");
		double price= documentContext.read("$.price");
		assertThat(product).isEqualTo("Rice");
		assertThat(price).isEqualTo(14.22);
	}
	@Test
	void shouldUGetAByNameProduct() {
		ResponseEntity<String> response = restTemplate.getForEntity("/products?nameProduct=Soap", String.class);
		DocumentContext	documentContext = JsonPath.parse(response.getBody());
		String product= documentContext.read("$.productName");
		double price= documentContext.read("$.price");
		assertThat(product).isEqualTo("Soap");
		assertThat(price).isEqualTo(1.25);
	}
*/

}
