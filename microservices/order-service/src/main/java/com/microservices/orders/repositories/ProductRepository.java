package com.microservices.orders.repositories;

import com.microservices.orders.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    public Product findByCategory(String category);

    public Optional<Product> findById(String productId);
}
