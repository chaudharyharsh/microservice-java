package com.microservice.inventory.mapper;

import org.springframework.stereotype.Component;

import com.microservice.inventory.dto.request.InventoryRequest;
import com.microservice.inventory.dto.response.InventoryResponse;
import com.microservice.inventory.model.Inventory;

@Component
public class InventoryMapper {
    
    public InventoryResponse inventoryToDto(Inventory inventory) {
        return InventoryResponse.builder()
        .id(inventory.getId())
        .skuCode(inventory.getSkuCode())
        .quantity(inventory.getQuantity())
        .isInStock(inventory.getQuantity()>0)
        .build();
    }

    public Inventory dtoToInventory(InventoryRequest inventoryRequest){
        return Inventory.builder()
        .id(inventoryRequest.getId())
        .skuCode(inventoryRequest.getSkuCode())
        .quantity(inventoryRequest.getQuantity())
        .build();
    }

}
