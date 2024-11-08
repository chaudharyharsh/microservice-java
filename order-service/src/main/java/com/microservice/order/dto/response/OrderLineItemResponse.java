package com.microservice.order.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemResponse {
    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}