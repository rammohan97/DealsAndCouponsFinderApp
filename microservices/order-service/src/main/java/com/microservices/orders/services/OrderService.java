package com.microservices.orders.services;

import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.orders.dtos.ProductOrders;
import com.microservices.orders.dtos.ResponseDto;
import com.microservices.orders.entity.Product;
import com.microservices.orders.util.PDFGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.microservices.orders.entity.Order;
import com.microservices.orders.repositories.OrderRepository;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
@Slf4j
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	public ResponseEntity save(Order order) throws JsonProcessingException {
		// TODO Auto-generated method stub

		//Order order = new Order();
		order.setOrderId(UUID.randomUUID().toString());
		order.setDate(Date.from(Instant.now()));
		//order.setProductOrders(productOrders.getProductOrderList());
		//order.setAmount(productOrders.getAmount());
		//order.setCouponId(productOrders.getCouponId());
		//log.info("OrderService: Save before save: {}",new ObjectMapper().writeValueAsString(order));
		Order savedOrder = orderRepository.save(order);
		//log.info("OrderService: Save after save : {}",new ObjectMapper().writeValueAsString(savedOrder));

		ByteArrayInputStream bis = PDFGenerator.customerPDFReport(savedOrder);
		String filename = "booking"+"-"+savedOrder.getOrderId()+".pdf";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename="+filename);

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename);
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");
		return ResponseEntity
				.ok()
				.headers(header)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}


    public List<Order> fetchAllOrders() {
		return  orderRepository.findAll();
    }

	public Order update(Order order) {
		Optional<Order> optionalOrder = Optional.ofNullable(orderRepository.findByOrderId(order.getOrderId()));
		if(optionalOrder.isPresent()){
			return  orderRepository.save(order);
		}else{
			return null;
		}

	}

	public ResponseDto delete(Order order) {
		Optional<Order> optionalOrder = Optional.ofNullable(orderRepository.findByOrderId(order.getOrderId()));
		if(optionalOrder.isPresent()){
			orderRepository.delete(order);
			return new  ResponseDto(true);
		}else{
			return new  ResponseDto(false);
		}

	}
}
