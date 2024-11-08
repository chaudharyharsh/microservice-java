package com.microservice.product.service;

import java.util.List;

import com.microservice.product.dto.request.ProductRequest;
import com.microservice.product.dto.response.ProductResponse;

public interface ProductService {
		ProductResponse addProduct(ProductRequest productRequest);
		ProductResponse updateProduct(ProductRequest productRequest);
		ProductResponse getProduct (String productId);
		List<ProductResponse> getAllProducts();
		void deleteProduct(String productId);
}
