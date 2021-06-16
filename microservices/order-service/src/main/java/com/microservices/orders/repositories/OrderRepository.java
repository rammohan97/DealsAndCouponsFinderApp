package com.microservices.orders.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.microservices.orders.entity.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String>{


    Order findByOrderId(String orderId);
}
