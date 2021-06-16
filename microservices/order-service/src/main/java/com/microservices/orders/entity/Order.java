package com.microservices.orders.entity;

import com.microservices.orders.dtos.ProductOrder;
import com.microservices.orders.dtos.ProductOrders;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "order")
public class Order {

	@Id
	private String id;
	
	private String orderId;
	
	List<ProductOrder> productOrders;
	
	private String paymentType;

	private double amount;

	private String couponId;

	private Date date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<ProductOrder> getProductOrders() {
		return productOrders;
	}

	public void setProductOrders(List<ProductOrder> productOrders) {
		this.productOrders = productOrders;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Order(String id, String orderId, List<ProductOrder> productOrders, String paymentType, double amount,
			String couponId, Date date) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.productOrders = productOrders;
		this.paymentType = paymentType;
		this.amount = amount;
		this.couponId = couponId;
		this.date = date;
	}

	public Order(List<ProductOrder> productOrders, String paymentType, double amount, String couponId) {
		super();
		this.productOrders = productOrders;
		this.paymentType = paymentType;
		this.amount = amount;
		this.couponId = couponId;
	}

	public Order() {
	}

	
}
