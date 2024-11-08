package com.microservice.order.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microservice.order.dto.request.OrderRequest;
import com.microservice.order.dto.response.OrderResponse;
import com.microservice.order.model.Order;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class OrderMapper {

    @Autowired
    private OrderLineItemMapper orderLineItemMapper;
    
    public Order dtoToOrder(OrderRequest orderRequest){
        return Order.builder()
        .orderLineItemsList(orderRequest.getOrderLineItemsDtoList().stream().map(orderLineItem->orderLineItemMapper.orderLineItemRequestToDto(orderLineItem)).toList())
        .build();
    }

    public OrderResponse orderToOrderResponse(Order order){
        return OrderResponse.builder()
        .orderLineItemsDtoList(order.getOrderLineItemsList().stream().map(orderLineItem -> orderLineItemMapper.orderLineItemsToDto(orderLineItem)).toList())        
        .build();
    }
}
