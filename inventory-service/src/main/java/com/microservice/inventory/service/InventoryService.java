package com.microservice.inventory.service;

import java.util.List;

import com.microservice.inventory.dto.request.InventoryRequest;
import com.microservice.inventory.dto.response.InventoryResponse;

public interface InventoryService {
        InventoryResponse addItem(InventoryRequest inventoryRequest);
		InventoryResponse updateItem(InventoryRequest inventoryRequest);
		InventoryResponse getItemBySkuCode(String skuCode);
		List<InventoryResponse> getAllItems();
		void deleteItem(String skuCode);
		List<InventoryResponse> isInStock (List<String> skuCode);
        List<InventoryResponse> updateItems(List<InventoryRequest> inventoryRequest);
}
