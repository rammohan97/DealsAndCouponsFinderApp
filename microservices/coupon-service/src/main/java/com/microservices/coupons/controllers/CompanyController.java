package com.microservices.coupons.controllers;

import com.microservices.coupons.exceptions.CustomException;
import com.microservices.coupons.model.Category;
import com.microservices.coupons.model.Company;
import com.microservices.coupons.model.Coupon;
import com.microservices.coupons.model.ReturnResponse;
import com.microservices.coupons.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("/companyController")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@GetMapping("/hello")
	public String test() {
		return "Helo";
	}
	
	
	@GetMapping("/getCompanyId/{username}")
	public ReturnResponse getCompanyId(@PathVariable("username") String username) throws CustomException {
		return new ReturnResponse(companyService.getCompanyId(username));
	}

	@PostMapping("/addCoupon")
	public void addCoupon(@RequestBody Coupon coupon) throws CustomException {
		companyService.addCoupon(coupon);
	}

	@PutMapping("/updateCoupon")
	public void updateCoupon(@RequestBody Coupon coupon) throws CustomException {
		companyService.updateCoupon(coupon);
	}

	@DeleteMapping("/deleteCoupon/{id}")
	public void deleteCoupon(@PathVariable("id") String couponID) throws CustomException {
		companyService.deleteCoupon(couponID);
	}

	@GetMapping("/getCompanyCoupons/{id}")
	public ArrayList<Coupon> getCompanyCoupons(@PathVariable("id") String companyId) throws CustomException {
		return companyService.getCompanyCoupons(companyId);
	}

	@GetMapping("/getCompanyCouponsByCategory/{id}")
	public ArrayList<Coupon> getCompanyCoupons(@PathVariable("id") String companyId,
											   @RequestParam("category") Category category) throws CustomException {
		return companyService.getCompanyCoupons(category,companyId);
	}

	@GetMapping("/getCompanyCouponsByPrice/{id}")
	public ArrayList<Coupon> getCompanyCoupons(@PathVariable("id") String companyId,@RequestParam("maxPrice") double maxPrice) throws CustomException {
		return companyService.getCompanyCoupons(maxPrice,companyId);
	}

	@GetMapping("/getCompanyDetails/{id}")
	public Company getCompanyDetails(@PathVariable("id") String companyId) throws CustomException {
		return companyService.getCompanyDetails(companyId);
	}
}
