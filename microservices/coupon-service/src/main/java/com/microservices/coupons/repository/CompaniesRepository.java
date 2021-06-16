package com.microservices.coupons.repository;

import com.microservices.coupons.model.Company;
import com.microservices.coupons.model.Coupon;
import com.microservices.coupons.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CompaniesRepository extends MongoRepository<Company, String> {

	/*@Query("select id from Company where email = ?1 and password =?2")
	public Integer findByEmailAndPassword(String email, String password);*/

   /* @Query("{'id': ?#{ [0] ? {$exists :true} : [1] }}")
	public Boolean existsByNameOrEmail(String name, String email);*/

    public Boolean existsByNameAndEmail(String name, String email);

    public Company findCompanyByEmailAndId(String email, String id);

    public Optional<Company> findById(String id);

    public Boolean existsByEmail(String email);

    public Company findByEmail(String email);

    public boolean existsById(String id);

   /* public Company findById(int id);

	public Boolean existsById(int companyID);


	@Query("select c from Company c where c.email = ?1 and c.id <> ?2")
	public Company findCompanyByEmailAndId(String email, int id);

	@Query("select c from Coupon c where companyID = ?1")
	public Set<Coupon> findCompanyCoupons(int companyID);*/
}
