package com.microservices.coupons.controllers;

import com.microservices.coupons.exceptions.CustomException;
import com.microservices.coupons.model.*;
import com.microservices.coupons.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("/customerController")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	

	@PostMapping("/purchaseCoupon/{id}")
	public void purchaseCoupon(@PathVariable("id") String customerId,@RequestBody Coupon coupon) throws CustomException {
		customerService.purchaseCoupon(coupon,customerId);
	}

	@GetMapping("/getCustomerCoupons/{id}")
	public ArrayList<Coupon> getCustomerCoupons(@PathVariable("id") String customerId) throws CustomException {
		return customerService.getCustomerCoupons(customerId);
	}

	@GetMapping("/getCustomerCouponsByCategory/{id}")
	public ArrayList<Coupon> getCustomerCoupons(@PathVariable("id") String customerId,@RequestParam("category") Category category) throws CustomException {
		return customerService.getCustomerCoupons(category,customerId);
	}

	@GetMapping("/getCustomerCouponsByPrice/{id}")
	public ArrayList<Coupon> getCustomerCoupons(@PathVariable("id") String customerId,@RequestParam("maxPrice") double maxPrice) throws CustomException {
		return customerService.getCustomerCoupons(maxPrice,customerId);
	}

	@GetMapping("/getCustomerDetails/{id}")
	public Customer getCustomerDetails(@PathVariable("id") String customerId) throws CustomException {
		return customerService.getCustomerDetails(customerId);
	}

	@GetMapping("/getAllCoupons/{id}")
	public ArrayList<Coupon> getAllCoupons(@PathVariable("id") String customerId) throws CustomException {
		return customerService.getAllCoupons(customerId);
	}

	@GetMapping("/getCustomerId/{username}")
	public ReturnResponse getCustomerId(@PathVariable("username") String username) throws CustomException {
		return new ReturnResponse(customerService.getCustomerId(username));
	}

	@PostMapping("/applyCoupon")
	public CouponResponse applyCoupon(@RequestBody CouponDto couponDto){
		return customerService.applyCoupon(couponDto.getCategory(),couponDto.getCoupon(),couponDto.getAmount());
	}

}
