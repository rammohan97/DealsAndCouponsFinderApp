package com.microservices.coupons.repository;

import com.microservices.coupons.model.Company;
import com.microservices.coupons.model.Coupon;
import com.microservices.coupons.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CustomersRepository extends MongoRepository<Customer, String> {

    public Customer findByEmail(String email);

    public Boolean existsByEmail(String email);

    public Optional<Customer> findById(String id);

    public Customer findCustomerByEmailAndId(String email, String id);
}
