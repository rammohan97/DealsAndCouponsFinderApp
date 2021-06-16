package com.microservices.coupons.repository;

import com.microservices.coupons.model.Category;
import com.microservices.coupons.model.Coupon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CouponsRepository extends MongoRepository<Coupon, String> {

    public Boolean existsByTitleAndCompanyID(String title, String companyID);

    public Optional<Coupon> findById(String id);

    Boolean existsByTitleAndCompanyIDAndId(String title,String companyID , String id);

    public Set<Coupon> findByCategoryAndCompanyID(Category category, String companyID);

    public Optional<Coupon> findByCategoryAndId(Category category, String id);

}

