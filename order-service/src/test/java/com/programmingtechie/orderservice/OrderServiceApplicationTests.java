package com.programmingtechie.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmingtechie.orderservice.dto.OrderLineItemsDTO;
import com.programmingtechie.orderservice.dto.OrderRequest;
import com.programmingtechie.orderservice.model.Order;
import com.programmingtechie.orderservice.model.OrderLineItems;
import com.programmingtechie.orderservice.repository.OrderRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Slf4j
class OrderServiceApplicationTests {

	@Container
	static PostgreSQLContainer postgres = new PostgreSQLContainer<>("postgres:15");

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private MockMvc mockMvc;

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private ObjectMapper objectMapper;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.datasource.url", postgres::getJdbcUrl);
		dynamicPropertyRegistry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
		dynamicPropertyRegistry.add("spring.datasource.username", postgres::getUsername);
		dynamicPropertyRegistry.add("spring.datasource.password", postgres::getPassword);
	}

	@Test
	void placeOrderPost() throws Exception {
		OrderRequest orderRequest = getOrderRequest();
		String orderRequestString = objectMapper.writeValueAsString(orderRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
						.contentType(MediaType.APPLICATION_JSON)
						.content(orderRequestString))
				.andExpect(status().isCreated());
	}

	private OrderRequest getOrderRequest() {
		OrderRequest orderRequest = new OrderRequest();
		List<OrderLineItemsDTO> orderLineItemsDTO = List.of(
				new OrderLineItemsDTO(
						1L,
						"Iphone 13",
						BigDecimal.valueOf(240000),
						1
				),
				new OrderLineItemsDTO(
						2L,
						"Iphone 14",
						BigDecimal.valueOf(250000),
						1
				),
				new OrderLineItemsDTO(
						3L,
						"Iphone 15",
						BigDecimal.valueOf(260000),
						1
				)
		);
		orderRequest.setOrderLineItemsDTO(orderLineItemsDTO);
		return orderRequest;
	}
}
