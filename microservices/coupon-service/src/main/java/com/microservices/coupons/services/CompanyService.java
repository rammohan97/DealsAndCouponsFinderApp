package com.microservices.coupons.services;

import com.microservices.coupons.exceptions.CustomException;
import com.microservices.coupons.model.Category;
import com.microservices.coupons.model.Company;
import com.microservices.coupons.model.Coupon;
import com.microservices.coupons.repository.CompaniesRepository;
import com.microservices.coupons.repository.CouponsRepository;
import com.microservices.coupons.repository.CustomersRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CompanyService  {

	@Autowired
	CompaniesRepository companiesRepo;

	@Autowired
	CustomersRepository customersRepo;

	@Autowired
	CouponsRepository couponsRepo;

	private int companyID;

	public CompanyService() {

	}

	public CompanyService(int companyID) {
		this.companyID = companyID;
	}



	public void addCoupon(Coupon coupon) throws CustomException {
		if(coupon != null) {
			//coupon.setCompanyID(companyID);
			//Check if the date is after current time
			checkdate(coupon.getStartDate());
			//Check if the start date before end date
			if(coupon.getEndDate() != null && coupon.getStartDate().after(coupon.getEndDate()) ) {
				throw new CustomException("Cannot add coupon with invalid end date");
			}
			checkdate(coupon.getEndDate());

			if(!companiesRepo.existsById(coupon.getCompanyID()) ) {
				throw new CustomException("Cannot add coupon because no company found with this specific ID");	
			}
			else {
				if(couponsRepo.existsByTitleAndCompanyID(coupon.getTitle(),coupon.getCompanyID()) ) {
					throw new CustomException("The title is already exist in the company");	
				} else {
					Coupon savedCoupon = couponsRepo.save(coupon);
					Optional<Company> optionalCompany = companiesRepo.findById(coupon.getCompanyID());
					Company company = optionalCompany.get();
					company.setCoupons(Stream.of(savedCoupon).collect(Collectors.toSet()));
					companiesRepo.save(company);

				}
			}
		} else {
			throw new CustomException("Cannot add empty Coupon");
		}
	}

	public void updateCoupon(Coupon coupon) throws CustomException {
		if(coupon != null) {
			//coupon.setCompanyID(companyID);
			//check if the date after current time
			checkdate(coupon.getStartDate());
			//Check if the start date before end date
			if(coupon.getEndDate() != null && coupon.getStartDate().after(coupon.getEndDate()) ) {
				throw new CustomException("Cannot add coupon with invalid end date");
			}
			checkdate(coupon.getEndDate());
			Optional<Coupon> couponOptional =couponsRepo.findById(coupon.getId());
			if(couponOptional.isPresent()) {
				if(companiesRepo.existsById(coupon.getCompanyID())) {
					//Cannot update title that already exists
					if(couponsRepo.existsByTitleAndCompanyIDAndId(coupon.getTitle(),coupon.getCompanyID(),
							coupon.getId()) != null ) {
						throw new CustomException("the title is already exist in the company");	
					}
					Coupon savedCoupon = couponsRepo.save(coupon);
					Optional<Company> optionalCompany = companiesRepo.findById(coupon.getCompanyID());
					Company company = optionalCompany.get();
					company.setCoupons(Stream.of(savedCoupon).collect(Collectors.toSet()));
					companiesRepo.save(company);
				}
				else {
					throw new CustomException("Coupon's companyId cannot be updated");	
				}
			}
			else {
				throw new CustomException("The coupon does not exist in the system");	
			}
		} else {
			throw new CustomException("Cannot update empty Coupon");
		}
	}

	public void deleteCoupon(String couponID) throws CustomException {
		if(StringUtils.isNotEmpty(couponID)) {
			Optional<Coupon> c =  couponsRepo.findById(couponID);
			if(c.isPresent()) {
				Coupon coupon = c.get();
				Optional<Company> optionalCompany = companiesRepo.findById(coupon.getCompanyID());
				Company company = optionalCompany.get();
				Set<Coupon> coupons = company.getCoupons();
				coupons.remove(coupon);
				company.setCoupons(coupons);
				companiesRepo.save(company);
				couponsRepo.delete(coupon);
			}
			else {
				throw new CustomException("The coupon does not exist in the system");	
			}
		}else {
			throw new CustomException("Cannot delete coupon with invalid ID");
		}
	}

	public ArrayList<Coupon> getCompanyCoupons(String companyId) throws CustomException {
		Optional<Company> optionalCompany =  companiesRepo.findById(companyId);
		if(optionalCompany.isPresent()){
			ArrayList<Coupon> coupons = new ArrayList<>(optionalCompany.get().getCoupons());
			if(coupons.isEmpty()){
				throw  new CustomException("No Coupons exist!!");
			}
			return  coupons;
		}else{
			throw  new CustomException("No COmpany found");
		}

	}

	public ArrayList<Coupon> getCompanyCoupons(Category category,String companyId) throws CustomException {
		Set<Coupon> coupons = couponsRepo.findByCategoryAndCompanyID(category, companyId);
		if(coupons.isEmpty()) {
			throw new CustomException("No coupons found with selected category");	
		}
		return new ArrayList<Coupon>(coupons);
	}

	public ArrayList<Coupon> getCompanyCoupons(double maxPrice,String companyId) throws CustomException {
		if(maxPrice > 0) {
			List<Coupon> coupons = couponsRepo.findAll();
			 List<Coupon> couponList=
					 coupons.stream().filter(coupon -> coupon.getPrice()<=maxPrice).collect(Collectors.toList());
			if(couponList.isEmpty()) {
				throw new CustomException("No coupons found with this price limit");		 
			}
			return new ArrayList<Coupon>(couponList);
		} else {
			throw new CustomException("The MaxPrice must be positive");
		}
	}

	public Company getCompanyDetails(String companyId) throws CustomException {
		Optional<Company> c = companiesRepo.findById(companyId);
		if(!c.isPresent()) {
			throw new CustomException("Company does not exists");	
		}
		return c.get();
	}

	//Check if the date is after the current date
	private void checkdate(Date date) throws CustomException {
		if(date != null) {
			if(date.getTime()< Calendar.getInstance().getTime().getTime()) {
				throw new CustomException("The date have to be after the current time");
			}
		}
	}

	public String getCompanyId(String username) throws CustomException {
		Company company = companiesRepo.findByEmail(username);
		if(company!=null){
			return  company.getId();
		}else{
			throw new CustomException("Company Not found");
		}
	}
}
