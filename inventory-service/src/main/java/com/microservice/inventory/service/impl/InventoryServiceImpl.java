package com.microservice.inventory.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.inventory.dto.request.InventoryRequest;
import com.microservice.inventory.dto.response.InventoryResponse;
import com.microservice.inventory.mapper.InventoryMapper;
import com.microservice.inventory.model.Inventory;
import com.microservice.inventory.repository.InventoryRepository;
import com.microservice.inventory.service.InventoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public InventoryResponse addItem(InventoryRequest inventoryRequest) {
        Inventory inventory = this.inventoryMapper.dtoToInventory(inventoryRequest);
        this.inventoryRepository.save(inventory);
        return this.inventoryMapper.inventoryToDto(inventory);
    }

    @Override
    public InventoryResponse updateItem(InventoryRequest inventoryRequest) {
        InventoryResponse inventoryResponse;
        Inventory existinginventory;
        Optional<Inventory> inventory = this.inventoryRepository.findById(inventoryRequest.getId());
        if (inventory.isPresent()) {
            existinginventory = inventory.get();
            existinginventory.setSkuCode(inventoryRequest.getSkuCode());
            existinginventory.setQuantity(inventoryRequest.getQuantity());
            this.inventoryRepository.save(existinginventory);
            inventoryResponse = this.inventoryMapper.inventoryToDto(inventory.get());
        } else {
            throw new RuntimeException("Inventory not found with id : " + inventoryRequest.getId());
        }
        return inventoryResponse;
    }

    @Override
    public InventoryResponse getItemBySkuCode(String skuCode) {
        Optional<Inventory> inventory = this.inventoryRepository.findBySkuCode(skuCode);
        InventoryResponse inventoryResponse;
        if (inventory.isPresent()) {
            inventoryResponse = this.inventoryMapper.inventoryToDto(inventory.get());
        } else {
            throw new RuntimeException("Item not found with Sku Code : " + skuCode);
        }
        return inventoryResponse;
    }

    @Override
    public List<InventoryResponse> getAllItems() {
        List<Inventory> listOfInventory = this.inventoryRepository.findAll();
        List<InventoryResponse> listofInventoryResponses = listOfInventory.stream()
                .map(inventory -> this.inventoryMapper.inventoryToDto(inventory)).toList();
        return listofInventoryResponses;
    }

    @Override
    public void deleteItem(String skuCode) {
        this.inventoryRepository.deleteBySkuCode(skuCode);
    }

    @Override
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return this.inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .filter(inventory -> inventory.getQuantity() > 0)
                .map(inventory -> this.inventoryMapper.inventoryToDto(inventory)).toList();

    }

    @Override
    public List<InventoryResponse> updateItems(List<InventoryRequest> inventoryRequest) {
        List<InventoryResponse> inventoryResponses = new ArrayList<>();
        InventoryResponse inventoryResponse;
        for (InventoryRequest inventoryReq : inventoryRequest) {
            Inventory existinginventory;
            Optional<Inventory> inventory = this.inventoryRepository.findById(inventoryReq.getId());
            if (inventory.isPresent()) {
                existinginventory = inventory.get();
                existinginventory.setSkuCode(inventoryReq.getSkuCode());
                existinginventory.setQuantity(inventoryReq.getQuantity());
                this.inventoryRepository.save(existinginventory);
                inventoryResponse = this.inventoryMapper.inventoryToDto(inventory.get());
                inventoryResponses.add(inventoryResponse);

            } else {
                throw new RuntimeException("Inventory not found with id : " + inventoryReq.getId());
            }
        }
        return inventoryResponses;
    }
}
