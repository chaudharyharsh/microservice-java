package com.microservice.order.mapper;

import org.springframework.stereotype.Component;

import com.microservice.order.dto.request.OrderLineItemRequest;
import com.microservice.order.dto.response.OrderLineItemResponse;
import com.microservice.order.model.OrderLineItems;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderLineItemMapper {

    public OrderLineItemResponse orderLineItemsToDto (OrderLineItems orderLineItems){
        return OrderLineItemResponse.builder()
        .id(orderLineItems.getId())
        .skuCode(orderLineItems.getSkuCode())
        .price(orderLineItems.getPrice())
        .quantity(orderLineItems.getQuantity())
        .build();
    }
    public OrderLineItems orderLineItemRequestToDto(OrderLineItemRequest orderLineItemRequest){
        return OrderLineItems.builder()
        .id(orderLineItemRequest.getId())
        .skuCode(orderLineItemRequest.getSkuCode())
        .price(orderLineItemRequest.getPrice())
        .quantity(orderLineItemRequest.getQuantity())
        .build();
    }
}
