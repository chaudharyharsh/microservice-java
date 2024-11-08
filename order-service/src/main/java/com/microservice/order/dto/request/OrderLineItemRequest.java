package com.microservice.order.dto.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemRequest {
    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}