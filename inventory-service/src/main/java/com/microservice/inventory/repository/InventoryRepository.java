package com.microservice.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.inventory.model.Inventory;
import java.util.List;
import java.util.Optional;


public interface InventoryRepository extends JpaRepository<Inventory,Long>{

    List<Inventory> findBySkuCodeIn(List<String> skuCode);
    Optional<Inventory> findBySkuCode(String skuCode);
    void deleteBySkuCode(String skuCode);
}
