package com.programmingtechie.orderservice.controller;

import com.programmingtechie.orderservice.dto.OrderRequest;
import com.programmingtechie.orderservice.srvice.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public record OrderController(OrderService orderService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return "Order placed successfully";
    }
}
