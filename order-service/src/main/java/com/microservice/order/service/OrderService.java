package com.microservice.order.service;

import java.util.List;

import com.microservice.order.dto.request.OrderRequest;
import com.microservice.order.dto.response.OrderResponse;

public interface OrderService {
        OrderResponse placeOrder(OrderRequest orderRequest);
		OrderResponse updateOrder(OrderRequest orderRequest);
		OrderResponse getOrder (String orderId);
		List<OrderResponse> getAllOrders();
		void cancelOrder(String orderId);
}
