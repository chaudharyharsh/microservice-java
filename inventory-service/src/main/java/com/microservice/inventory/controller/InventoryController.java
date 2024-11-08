package com.microservice.inventory.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.inventory.dto.request.InventoryRequest;
import com.microservice.inventory.dto.response.InventoryResponse;
import com.microservice.inventory.response.ApiResponse;
import com.microservice.inventory.service.InventoryService;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class InventoryController {

    @Autowired
    private final InventoryService inventoryService;

    @Autowired 
    private final ApiResponse apiResponse;


    @PostMapping("/inventories")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Object> addItem(@RequestBody InventoryRequest inventoryRequest) {
		InventoryResponse inventoryResponse = this.inventoryService.addItem(inventoryRequest);
		return apiResponse.commonResponse(true, "New product successfully added.", HttpStatus.CREATED, inventoryResponse);	
	}

	@GetMapping("/inventories")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> getAllItems(){
		List<InventoryResponse> inventoryResponses = this.inventoryService.getAllItems();
		return apiResponse.commonResponse(true, "All products successfully fetched.", HttpStatus.OK, inventoryResponses);
	}

	@GetMapping("/inventories/{skuCode}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> getItemBySkuCode(@PathParam(value = "skuCode") String skuCode){
		InventoryResponse inventoryResponse = this.inventoryService.getItemBySkuCode(skuCode);
		return apiResponse.commonResponse(true, "Product with "+skuCode+" successfully fetched.", HttpStatus.OK, inventoryResponse);
	}

	@GetMapping("/inventories/inStock")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> isInStock(@RequestParam List<String> skuCode){
		List<InventoryResponse> inventoryResponse = this.inventoryService.isInStock(skuCode);
		return apiResponse.commonResponse(true, "Product with "+skuCode+" successfully fetched.", HttpStatus.OK, inventoryResponse);
	}

	@PutMapping("/inventories")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> updateItem(@RequestBody InventoryRequest inventoryRequest) {
		InventoryResponse inventoryResponse = this.inventoryService.updateItem(inventoryRequest);
		return apiResponse.commonResponse(true, "Product with  successfully updated.", HttpStatus.CREATED, inventoryResponse);	
	}

	@PutMapping("/inventories/")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> updateItems(@RequestBody List<InventoryRequest> inventoryRequest) {
		List<InventoryResponse> inventoryResponse = this.inventoryService.updateItems(inventoryRequest);
		return apiResponse.commonResponse(true, "Product with  successfully updated.", HttpStatus.CREATED, inventoryResponse);	
	}

	@DeleteMapping("/inventories/{skuCode}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> cancelOrder(@PathParam(value = "skuCode") String skuCode) {
		this.inventoryService.deleteItem(skuCode);
		return apiResponse.commonResponse(true, "Product with "+skuCode+" successfully deleted.", HttpStatus.CREATED, null);	
	}

}

