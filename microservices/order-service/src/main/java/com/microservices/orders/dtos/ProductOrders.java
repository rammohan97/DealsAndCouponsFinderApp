package com.microservices.orders.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ProductOrders {

    private List<ProductOrder> productOrderList;

    private double amount;

    private String couponId;

}
