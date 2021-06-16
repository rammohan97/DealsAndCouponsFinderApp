package com.microservices.coupons.model;

import lombok.AllArgsConstructor;
import lombok.Data;

public class CouponResponse {
    private String message;

    private Integer status;

    private double amount;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public CouponResponse(String message, Integer status, double amount) {
		super();
		this.message = message;
		this.status = status;
		this.amount = amount;
	}
    
    
}
