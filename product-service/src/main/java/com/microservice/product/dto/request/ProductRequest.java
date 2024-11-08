package com.microservice.product.dto.request;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductRequest {

	private String id;
	private String name;
	private String description;
	private BigDecimal price;
}
