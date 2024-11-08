package com.microservice.order.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.order.dto.request.InventoryRequest;
import com.microservice.order.dto.request.OrderRequest;
import com.microservice.order.dto.response.OrderResponse;
import com.microservice.order.mapper.OrderLineItemMapper;
import com.microservice.order.mapper.OrderMapper;
import com.microservice.order.model.Order;
import com.microservice.order.model.OrderLineItems;
import com.microservice.order.repository.OrderRepository;
import com.microservice.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private final OrderLineItemMapper orderLineItemMapper;

    @Autowired
    private final OrderMapper orderMapper;

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final RestTemplate restTemplate;

    @Override
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        boolean allItemInStock = false;
        StringBuilder outOfStock = new StringBuilder();
        int updatedInventorQuantity;
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream()
                .map(orderLineItem -> orderLineItemMapper.orderLineItemRequestToDto(orderLineItem)).toList();
        order.setOrderLineItemsList(orderLineItems);

        List<String> requestedSkuCodes = orderLineItems.stream().map(OrderLineItems::getSkuCode).toList();
        List<Integer> requestQuantities = orderLineItems.stream().map(OrderLineItems::getQuantity).toList();

        String inventoryServiceBaseURL = "http://inventory-service/api/v1/inventories";
        String getInventoryUrl = inventoryServiceBaseURL + "/inStock?skuCode={requestedSkuCodes}";

        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> responseEntity = restTemplate.exchange(getInventoryUrl, HttpMethod.GET, null, Map.class,
                String.join(",", requestedSkuCodes));

        JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        List<InventoryRequest> inventoryRequests = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            int requestQuantity = requestQuantities.get(i);
            String requestSkuCode = requestedSkuCodes.get(i);
            int inStockQuantity = jsonArray.getJSONObject(i).getInt("quantity");
            int inventoryId = jsonArray.getJSONObject(i).getInt("id");
            if (requestQuantity < inStockQuantity) {
                allItemInStock = true;
                updatedInventorQuantity = inStockQuantity - requestQuantity;
                inventoryRequests.add(InventoryRequest.builder().id(Integer.toUnsignedLong(inventoryId)).skuCode(requestSkuCode).quantity(updatedInventorQuantity).build());
            } else {
                allItemInStock = false;
                outOfStock.append("Items - " + requestSkuCode);
            }
        }
        outOfStock.append("are out of stock. ");
        //TODO : update inventory quantity in inventory service
        if (allItemInStock && outOfStock.length()<27 ) {
            this.orderRepository.save(order);
        }
        return this.orderMapper.orderToOrderResponse(order);
    }

    @Override
    public OrderResponse updateOrder(OrderRequest orderRequest) {
        throw new UnsupportedOperationException("Unimplemented method 'updateOrder'");
    }

    @Override
    public OrderResponse getOrder(String orderId) {
        OrderResponse orderResponse;
        Optional<Order> order = this.orderRepository.findById(Long.parseLong(orderId));
        if (order.isPresent()) {
            orderResponse = this.orderMapper.orderToOrderResponse(order.get());
        } else {
            throw new RuntimeException("Order not found with id : " + orderId);
        }
        return orderResponse;
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> listofOrders = this.orderRepository.findAll();
        List<OrderResponse> orderResponses = listofOrders.stream()
                .map(order -> this.orderMapper.orderToOrderResponse(order)).toList();
        return orderResponses;
    }

    @Override
    public void cancelOrder(String orderId) {
        // TODO: cancel flag in db;

    }

    // public OrderResponse defaultFallbackMethod(OrderRequest orderRequest, RuntimeException exception){
    //     return OrderResponse.builder().orderLineItemsDtoList(null).build();
    // }



}
