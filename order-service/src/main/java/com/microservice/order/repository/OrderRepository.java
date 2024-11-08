package com.microservice.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.order.model.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
