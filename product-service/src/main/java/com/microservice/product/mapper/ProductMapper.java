package com.microservice.product.mapper;

import org.springframework.stereotype.Component;

import com.microservice.product.dto.request.ProductRequest;
import com.microservice.product.dto.response.ProductResponse;
import com.microservice.product.model.Product;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    public ProductResponse productToProductResponse(Product product){
        return ProductResponse.builder().id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice())
        .build();
    }
    public Product productRequestToProduct(ProductRequest productRequest){
        return Product.builder().id(productRequest.getId())
        .name(productRequest.getName())
        .description(productRequest.getDescription())
        .price(productRequest.getPrice())
        .build();
    }

}
