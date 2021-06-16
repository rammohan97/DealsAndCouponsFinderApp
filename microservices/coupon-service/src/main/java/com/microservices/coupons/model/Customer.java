package com.microservices.coupons.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Document(collection = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

	//Attributes
	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Set<Coupon> coupons = new HashSet<Coupon>();


	//Print Customer Details
	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", coupons=" + coupons + "]";
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Set<Coupon> getCoupons() {
		return coupons;
	}


	public void setCoupons(Set<Coupon> coupons) {
		this.coupons = coupons;
	}
	
	
}
