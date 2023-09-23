package com.programmingtechie.orderservice.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private List<OrderLineItemsDTO> orderLineItemsDTO;
}
