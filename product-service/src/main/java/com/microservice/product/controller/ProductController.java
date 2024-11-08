package com.microservice.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.product.dto.request.ProductRequest;
import com.microservice.product.dto.response.ProductResponse;
import com.microservice.product.response.ApiResponse;
import com.microservice.product.service.ProductService;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

	@Autowired
	private final ProductService productService;

	@Autowired
	private  final ApiResponse apiResponse;

	@PostMapping("/products")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Object> createProduct(@RequestBody ProductRequest productRequest) {
		ProductResponse productResponse = this.productService.addProduct(productRequest);
		return apiResponse.commonResponse(true, "New product successfully added.", HttpStatus.CREATED, productResponse);	
	}

	@GetMapping("/products")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> getProducts(){
		List<ProductResponse> productResponses = this.productService.getAllProducts();
		return apiResponse.commonResponse(true, "All products successfully fetched.", HttpStatus.OK, productResponses);
	}

	@GetMapping("/products/{productId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> getProduct(@PathParam(value = "productId") String productId){
		ProductResponse productResponses = this.productService.getProduct(productId);
		return apiResponse.commonResponse(true, "Product with "+productId+" successfully fetched.", HttpStatus.OK, productResponses);
	}

	@PutMapping("/products")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> updateProduct(@RequestBody ProductRequest productRequest) {
		ProductResponse productResponse = this.productService.updateProduct(productRequest);
		return apiResponse.commonResponse(true, "Product with "+productRequest.getId()+" successfully updated.", HttpStatus.CREATED, productResponse);	
	}

	@DeleteMapping("/products/{productId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> deleteProduct(@PathParam(value = "productId") String productId) {
		this.productService.deleteProduct(productId);
		return apiResponse.commonResponse(true, "Product with "+productId+" successfully deleted.", HttpStatus.CREATED, null);	
	}
	
}
