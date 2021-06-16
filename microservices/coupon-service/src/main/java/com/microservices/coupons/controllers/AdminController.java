package com.microservices.coupons.controllers;

import com.microservices.coupons.exceptions.CustomException;
import com.microservices.coupons.model.Company;
import com.microservices.coupons.model.Customer;
import com.microservices.coupons.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping ("/administrator")
public class AdminController  {

	@Autowired
	private AdminService adminService;

	@PostMapping("/addCompany")
	public void addCompany(@RequestBody Company company) throws CustomException {
		adminService.addCompany(company);
	}

	@PutMapping("/updateCompany")
	public void updateCompany(@RequestBody Company company) throws CustomException {
		adminService.updateCompany(company);
	}

	@DeleteMapping("/deleteCompany/{id}")
	public void deleteCompany(@PathVariable("id") String companyID) throws CustomException {
		adminService.deleteCompany(companyID);
	}

	@GetMapping("/getAllCompanies")
	public ArrayList<Company> getAllCompanies() throws CustomException {
		return adminService.getAllCompanies();
	}

	@GetMapping("/getOneCompany/{id}")
	public Company getOneCompany(@PathVariable("id") String companyID) throws CustomException {
		return adminService.getOneCompany(companyID);
	}


	@GetMapping("/getOneCompanyByEmail/{email}")
	public Company getOneCompanyByEmail(@PathVariable("email") String email) throws CustomException {
		return adminService.getOneCompanyByEmail(email);
	}

	@PostMapping("/addCustomer")
	public void addCustomer(@RequestBody Customer customer) throws CustomException {
		adminService.addCustomer(customer);
	}

	@PutMapping("/updateCustomer")
	public void updateCustomer(@RequestBody Customer customer) throws CustomException {
		adminService.updateCustomer(customer);
	}

	@DeleteMapping("/deleteCustomer/{id}")
	public void deleteCustomer(@PathVariable("id") String customerID) throws CustomException {
		adminService.deleteCustomer(customerID);
	}

	@GetMapping("/getAllCustomers")
	public ArrayList<Customer> getAllCustomers() throws CustomException {
		return adminService.getAllCustomers();
	}

	@GetMapping("/getOneCustomer")
	public Customer getOneCustomer(@PathVariable("id") String customerID) throws CustomException {
		return adminService.getOneCustomer(customerID);
	}
}
