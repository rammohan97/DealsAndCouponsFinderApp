package com.microservices.coupons.services;

import com.microservices.coupons.exceptions.CustomException;
import com.microservices.coupons.model.Company;
import com.microservices.coupons.model.CredentialsDto;
import com.microservices.coupons.model.Customer;
import com.microservices.coupons.model.UserDto;
import com.microservices.coupons.repository.CompaniesRepository;
import com.microservices.coupons.repository.CustomersRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService  {

	@Autowired
	CompaniesRepository companiesRepo;

	@Autowired
	CustomersRepository customersRepo;

	@Autowired
	RestTemplate restTemplate;

	public void addCompany(Company company) throws CustomException {
		if(company != null) {
			if(!companiesRepo.existsByNameAndEmail(company.getName(),company.getEmail())) {
				companiesRepo.save(company);
			ResponseEntity<UserDto> responseEntity= restTemplate.postForEntity("http://localhost:20006/users" +
							"/createUser",
						new CredentialsDto(company.getEmail(),company.getPassword(),"Company"), UserDto.class);
			if(responseEntity.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR.value())){
				throw new CustomException(" SOmething went wrong, please try again");
			}
			}
			else {
				throw new CustomException("Company is already exists");
			}
		}
		else {
			throw new CustomException("Cannot add empty company");
		}
	}

	public void updateCompany(Company company) throws CustomException {
		final String uri = "http://localhost:20006/users/updateUser/{id}";

		if(company != null) {
			Optional<Company> optionalCompany = companiesRepo.findById(company.getId());
			if(optionalCompany.isPresent()) {
				Company existingCompany = optionalCompany.get();
				if(existingCompany.getEmail().equals(company.getEmail())){
					companiesRepo.save(company);
					updateUserInAuthService(company, uri);
				}else{
					Company companyByEMail = companiesRepo.findByEmail(company.getEmail());
					if(!StringUtils.equals(existingCompany.getId(),companyByEMail.getId())){
						throw new CustomException("Company email could not be updated because the" +
								" email is already exists for another company");

					}else{
						companiesRepo.save(company);
						updateUserInAuthService(company, uri);
					}
				}
			}
			else {
				throw new CustomException("The id doesn't exist in the system ");
			}
		}
		else {
			throw new CustomException("Cannot update empty company");
		}
	}

	private void updateUserInAuthService(Company company, String uri) {
		RestTemplate restTemplate = new RestTemplate();
		Map<String, String> params = new HashMap<String, String>();
		params.put("email", company.getEmail() );
		CredentialsDto credentialsDto = new CredentialsDto(company.getEmail(),company.getPassword(),"Company");
		restTemplate.put ( uri, credentialsDto, params );

	}

	private void updateUserInAuthService(Customer customer, String uri) {
		RestTemplate restTemplate = new RestTemplate();
		Map<String, String> params = new HashMap<String, String>();
		params.put("email", customer.getEmail() );
		CredentialsDto credentialsDto = new CredentialsDto(customer.getEmail(),customer.getPassword(),"Customer");
		restTemplate.put ( uri, credentialsDto, params );

	}

	public void deleteCompany(String companyID) throws CustomException {
		if(StringUtils.isNotEmpty(companyID)) {
			Optional<Company> c =  companiesRepo.findById(companyID);

			if(c.isPresent()) {
				companiesRepo.delete(c.get());

			}
			else {
				throw new CustomException("The company does not exist in the system");
			}
		}
		else {
			throw new CustomException("can't delete company with invalid ID");
		}
	}

	public ArrayList<Company> getAllCompanies() throws CustomException {
		if(companiesRepo.findAll().isEmpty()) {
			throw new CustomException("There are no companies in the system ");
		}
		return (ArrayList<Company>)companiesRepo.findAll();
	}

	public Company getOneCompany(String companyID) throws CustomException {
		if(StringUtils.isNotEmpty(companyID)) {
			Optional<Company> c = companiesRepo.findById(companyID);
			if(!c.isPresent()) {
				throw new CustomException("No company found with this specific ID");	
			}
			return c.get();
		}
		else {
			throw new CustomException("Cannot show invalid company");	
		}
	}

	public void addCustomer(Customer customer) throws CustomException {
		if(customer != null) {
			if(!customersRepo.existsByEmail(customer.getEmail())) {
				customersRepo.save(customer);
				ResponseEntity<UserDto> responseEntity= restTemplate.postForEntity("http://localhost:20006/users" +
								"/createUser",
						new CredentialsDto(customer.getEmail(),customer.getPassword(),"Customer"), UserDto.class);
				if(responseEntity.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR.value())){
					throw new CustomException(" SOmething went wrong, please try again");
				}
			}
			else {
				throw new CustomException("Cannot add email - This email is already exists in the system");	
			}
		} else {
			throw new CustomException("Cannot add empty customer");
		}
	}

	public void updateCustomer(Customer customer) throws CustomException {
		final String url = "http://localhost:20006/users/updateUser/{id}";
		if(customer != null) {
			Optional<Customer> optionalCustomer=customersRepo.findById(customer.getId());
			if(optionalCustomer.isPresent()) {
				Customer existingCustomer = optionalCustomer.get();
				if(existingCustomer.getEmail().equals(customer.getEmail())){
					customersRepo.save(customer);
					updateUserInAuthService(customer,url);
				}else{
					Customer customerByEMail = customersRepo.findByEmail(customer.getEmail());
					if(!StringUtils.equals(existingCustomer.getId(),customer.getId())){
						throw new CustomException("Customer email is already exists in another customer");

					}else{
						customersRepo.save(customer);
						updateUserInAuthService(customer,url);
					}
				}
			}
			else {
				throw new CustomException("The ID does not exist in the system");	
			}
		} else {
			throw new CustomException("Cannot update empty Customer");
		}
	}

	public void deleteCustomer(String customerID) throws CustomException {
		if(StringUtils.isNotEmpty(customerID)) {
			Optional<Customer> c = customersRepo.findById(customerID);
			if(!c.isPresent()) {
				throw new CustomException("No customer found with this specific ID");	
			}
			else {
				//c.removeCoupon(c);
				customersRepo.delete(c.get());
			}
		} else {
			throw new CustomException("cannot delete customer with invalid ID");
		}
	}

	public ArrayList<Customer> getAllCustomers() throws CustomException {
		if((customersRepo.findAll().isEmpty())) {
			throw new CustomException("There are no customers in the system");	
		}
		return (ArrayList<Customer>)customersRepo.findAll();
	}

	public Customer getOneCustomer(String customerID) throws CustomException {
		if(StringUtils.isNotEmpty(customerID)) {
			Optional<Customer> c = customersRepo.findById(customerID);
			if(!c.isPresent()) {
				throw new CustomException("No customer found with this specific ID");	
			}
			return c.get();
		} 
		else {
			throw new CustomException("Cannot show customer with invalid ID");
		}
	}

	public Company getOneCompanyByEmail(String email) throws CustomException {
		companiesRepo.findAll().stream().filter(company -> company.getEmail().equals("adidas@adidas.com")).collect(Collectors.toList());
		 Company company  = companiesRepo.findByEmail(email);
		 if(company!=null){
		 	return  company;
		 }
		 else{
		 	return new Company();
		 }
	}
}
