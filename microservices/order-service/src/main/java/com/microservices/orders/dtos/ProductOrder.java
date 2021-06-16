package com.microservices.orders.dtos;

import com.microservices.orders.entity.Product;
import lombok.Data;

public class ProductOrder {

    private Product product;
    private Integer quantity;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public ProductOrder(Product product, Integer quantity) {
		super();
		this.product = product;
		this.quantity = quantity;
	}
	public ProductOrder() {
	}
    
	
    
}
