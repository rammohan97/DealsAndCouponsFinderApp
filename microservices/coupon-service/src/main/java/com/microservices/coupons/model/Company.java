package com.microservices.coupons.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Document(collection = "companies")
public class Company {

	@Id
	private String id;
	private String name;
	private String email;
	private String password;
	private Set<Coupon> coupons = new HashSet<Coupon>();

	//Print Customer Details
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", coupons="
				+ coupons + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Company company = (Company) o;
		return getId().equals(company.getId()) &&
				getName().equals(company.getName()) &&
				getEmail().equals(company.getEmail()) &&
				getPassword().equals(company.getPassword());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getName(), getEmail(), getPassword());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
