package com.programmingtechie.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmingtechie.productservice.dto.ProductRequest;
import com.programmingtechie.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Slf4j
class ProductServiceApplicationTests {

	@Container
	static PostgreSQLContainer postgres = new PostgreSQLContainer<>("postgres:15");

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private MockMvc mockMvc;

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductRepository productRepository;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.datasource.url", postgres::getJdbcUrl);
		dynamicPropertyRegistry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
		dynamicPropertyRegistry.add("spring.datasource.username", postgres::getUsername);
		dynamicPropertyRegistry.add("spring.datasource.password", postgres::getPassword);
	}

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productRequestString))
				.andExpect(status().isCreated());
		Assertions.assertEquals(1, productRepository.findAll().size());
	}

	@Test
	void shouldGetProduct() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("Iphone 15")
				.description("New mobile 2023 years")
				.price(BigDecimal.valueOf(220000))
				.build();
	}

}
