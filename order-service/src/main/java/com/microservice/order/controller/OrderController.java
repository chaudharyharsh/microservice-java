package com.microservice.order.controller;

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

import com.microservice.order.dto.request.OrderRequest;
import com.microservice.order.dto.response.OrderResponse;
import com.microservice.order.response.ApiResponse;
import com.microservice.order.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderController {

    @Autowired 
    private final OrderService orderService;


    @Autowired
    private final ApiResponse apiResponse;

    @PostMapping("/orders")
	@ResponseStatus(HttpStatus.CREATED)
	@CircuitBreaker(name = "order-service", fallbackMethod = "defaultFallbackMethod")
	public ResponseEntity<Object> placeOrder(@RequestBody OrderRequest orderRequest) {
		OrderResponse orderResponse = this.orderService.placeOrder(orderRequest);
		return apiResponse.commonResponse(true, "New product successfully added.", HttpStatus.CREATED, orderResponse);	
	}

	@GetMapping("/orders")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> getOrders(){
		List<OrderResponse> orderResponses = this.orderService.getAllOrders();
		return apiResponse.commonResponse(true, "All products successfully fetched.", HttpStatus.OK, orderResponses);
	}

	@GetMapping("/orders/{orderId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> getOrder(@PathParam(value = "orderId") String orderId){
		OrderResponse orderResponses = this.orderService.getOrder(orderId);
		return apiResponse.commonResponse(true, "Product with "+orderId+" successfully fetched.", HttpStatus.OK, orderResponses);
	}

	@PutMapping("/orders")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> updateOrder(@RequestBody OrderRequest orderRequest) {
		OrderResponse OrderResponse = this.orderService.updateOrder(orderRequest);
		return apiResponse.commonResponse(true, "Product with  successfully updated.", HttpStatus.CREATED, OrderResponse);	
	}

	@DeleteMapping("/orders/{orderId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> cancelOrder(@PathParam(value = "orderId") String orderId) {
		this.orderService.cancelOrder(orderId);
		return apiResponse.commonResponse(true, "Product with "+orderId+" successfully deleted.", HttpStatus.CREATED, null);	
	}

	public ResponseEntity<Object> defaultFallbackMethod(OrderRequest orderRequest, RuntimeException exception){
		return apiResponse.commonResponse(true, "oops! something went wrong, please try again later.", HttpStatus.INTERNAL_SERVER_ERROR, null );
	}

}
