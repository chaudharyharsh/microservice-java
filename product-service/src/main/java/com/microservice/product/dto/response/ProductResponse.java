package com.microservice.product.dto.response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductResponse {

    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
