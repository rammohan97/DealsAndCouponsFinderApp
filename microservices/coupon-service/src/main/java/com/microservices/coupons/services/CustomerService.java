package com.microservices.coupons.services;

import com.microservices.coupons.exceptions.CustomException;
import com.microservices.coupons.model.*;
import com.microservices.coupons.repository.CouponsRepository;
import com.microservices.coupons.repository.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomerService  {

	private int customerID;

	@Autowired
	CouponsRepository couponsRepo;

	@Autowired
	CustomersRepository customersRepo;

	public CustomerService() {

	}


	public void purchaseCoupon(Coupon coupon, String customerId) throws CustomException {
		if(coupon != null) {
			Optional<Coupon> c = couponsRepo.findById(coupon.getId());
			System.out.println(c);
			if(c.isPresent()) {
				Coupon existingCoupon = c.get();
				Optional<Customer> optionalCustomer =  customersRepo.findById(customerId);
				Customer customer = optionalCustomer.get();
				List<Coupon> existingCoupons =
						customer.getCoupons().stream().filter(cou -> cou.getId().equals(coupon.getId())).collect(Collectors.toList());
				if (!existingCoupons.isEmpty()) {
					throw new CustomException("The same coupon cannot be purchased more than once.");	
				}
				else {
					if(existingCoupon.getAmount() > 0) {
						if(existingCoupon.getEndDate() != null && existingCoupon.getEndDate().getTime() <= Calendar.getInstance().getTime().getTime())
							throw new CustomException("This coupon cannot be purchased because it's expired.");
						else {
							Optional<Customer> customerOptional = customersRepo.findById(customerId);
							if(customerOptional.isPresent()){
								Customer cst = customerOptional.get();
								existingCoupon.setAmount(existingCoupon.getAmount() - 1);
								cst.setCoupons(Stream.of(existingCoupon).collect(Collectors.toSet()));
								customersRepo.save(cst);
							}else{
								throw new CustomException("Customer not exist");
							}

						}
					}
					else {
						throw new CustomException("This coupon cannot be purchased because it's not in stock.");			
					}				
				}	
			}

			else {
				throw new CustomException("No coupon found with this specific ID.");
			}
		}
		else {
			throw new CustomException("Cannot purchase empty coupon.");
		}
	}

	public ArrayList<Coupon> getCustomerCoupons(String customerId) throws CustomException {
		Optional<Customer> optionalCustomer = customersRepo.findById(customerId);

		if(!optionalCustomer.isPresent()  || optionalCustomer.get().getCoupons().isEmpty()) {
			throw new CustomException("No coupons has been found");	
		}
		return new ArrayList<Coupon>(optionalCustomer.get().getCoupons());
	}

	public ArrayList<Coupon> getCustomerCoupons(Category category, String customerId) throws CustomException {
		Optional<Customer> optionalCustomer = customersRepo.findById(customerId);
		if(optionalCustomer.isPresent()){
			Customer customer = optionalCustomer.get();
			ArrayList<Coupon> list = customer.getCoupons().stream().filter(coupon ->
					coupon.getCategory().equals(category)).collect(Collectors.toCollection(ArrayList::new));
			if(list.isEmpty()){
				throw new CustomException("No coupons found with the selected category");
			}
			return list;
		}else{
			throw new CustomException("No customer has been found");

		}
	}

	public ArrayList<Coupon> getCustomerCoupons(double maxPrice, String customerId) throws CustomException {
		if(maxPrice > 0) {
			Optional<Customer>  optionalCustomer = customersRepo.findById(customerId);
			if(optionalCustomer.isPresent()){
				Customer customer = optionalCustomer.get();
				ArrayList<Coupon> list = customer.getCoupons().stream().filter(coupon ->
						coupon.getAmount()<=maxPrice).collect(Collectors.toCollection(ArrayList::new));
				if(list.isEmpty()){
					throw new CustomException("No coupons found with selected max price");
				}
				return list;
			}else{
				throw new CustomException("No customer has been found");

			}
		} else {
			throw new CustomException("The max price must be positive");
		}
	}

	public Customer getCustomerDetails(String customerId) throws CustomException {
		Optional<Customer> optionalCustomer = customersRepo.findById(customerId);
		if(optionalCustomer.isPresent()){
			Customer customer = optionalCustomer.get();
			return customer;
		}else{
			throw new CustomException("Customer doesn't exists");
		}

	}

	public ArrayList<Coupon> getAllCoupons(String customerId) throws CustomException {
		Optional<Customer> optionalCustomer = customersRepo.findById(customerId);
		if(optionalCustomer.isPresent()){
			Customer customer = optionalCustomer.get();
			Set<Coupon> couponSet = customer.getCoupons();
			List<Coupon> coupons = couponsRepo.findAll();
			if(couponSet.isEmpty()){

				if(coupons.isEmpty()){
					throw new CustomException("No Coupon found in system");
				}
				return  new ArrayList<>(coupons);
			}else{
				List<Coupon> filteredCouponList =
						coupons.stream().filter(coupon -> !couponSet.contains(coupon)).collect(Collectors.toList());
				return new ArrayList<Coupon>(filteredCouponList);
			}

		}else{
			throw new CustomException("Customer doesn't exists");
		}

	}

	public  String getCustomerId(String username) throws CustomException{
	 	Customer customer=	customersRepo.findByEmail(username);
		if(customer!=null){
			return  customer.getId();
		}else{
			throw new CustomException("Company Not found");
		}
	}

    public CouponResponse applyCoupon(Category category, String couponId, double amount) {
		Optional<Coupon> optionalCoupon = couponsRepo.findByCategoryAndId(category,couponId);

		if(optionalCoupon.isPresent()){
			Coupon coupon = optionalCoupon.get();
			if(amount <coupon.getPrice()){
				return   new CouponResponse("Minimum amount should be "+coupon.getPrice(), HttpStatus.NOT_FOUND.value(),0.0);
			}else{
				return   new CouponResponse("Applied successfully ", HttpStatus.OK.value(),
						coupon.getAmount());
			}
		}else{
			return  new CouponResponse("Coupon not exist", HttpStatus.NOT_FOUND.value(),0.0);
		}

    }
}
