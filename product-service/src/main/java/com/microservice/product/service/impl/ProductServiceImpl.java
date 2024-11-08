package com.microservice.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.product.dto.request.ProductRequest;
import com.microservice.product.dto.response.ProductResponse;
import com.microservice.product.mapper.ProductMapper;
import com.microservice.product.model.Product;
import com.microservice.product.repository.ProductRepository;
import com.microservice.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	@Autowired
	private final ProductRepository productRepository;

	@Autowired
	private final ProductMapper productMapper;

	@Override
	public ProductResponse addProduct(ProductRequest productRequest) {
		Product product = this.productMapper.productRequestToProduct(productRequest);
		this.productRepository.save(product);
		return this.productMapper.productToProductResponse(product);
	}

	@Override
	public ProductResponse updateProduct(ProductRequest productRequest) {
		ProductResponse productResponse;
		Product existingProduct;
		Optional<Product> product = this.productRepository.findById(productRequest.getId());
		if (product.isPresent()) {
			existingProduct = product.get();
			existingProduct.setDescription(productRequest.getDescription());
			existingProduct.setName(productRequest.getName());
			existingProduct.setPrice(productRequest.getPrice());
			this.productRepository.save(existingProduct);
			productResponse = this.productMapper.productToProductResponse(existingProduct);
		} else {
			throw new RuntimeException("Game not found with id :" + productRequest.getId());
		}
		return productResponse;
	}

	@Override
	public ProductResponse getProduct(String productId) {
		ProductResponse productResponse;
		Optional<Product> product = this.productRepository.findById(productId);
		if (product.isPresent()) {
			productResponse = this.productMapper.productToProductResponse(product.get());
		} else {
			throw new RuntimeException("Product not found with id : " + productId);
		}
		return productResponse;
	}

	@Override
	public List<ProductResponse> getAllProducts() {
		List<Product> listofProducts = this.productRepository.findAll();
		List<ProductResponse> listifProductResponses = listofProducts.stream().map(product -> {
			return this.productMapper.productToProductResponse(product);
		}).toList();
		return listifProductResponses;
	}

	@Override
	public void deleteProduct(String productId) {
		Optional<Product> product = this.productRepository.findById(productId);
        if (product.isPresent()) {
            this.productRepository.deleteById(productId);
        } else {
            throw new RuntimeException("Product not found with id :" + productId);
        }
	}

}
