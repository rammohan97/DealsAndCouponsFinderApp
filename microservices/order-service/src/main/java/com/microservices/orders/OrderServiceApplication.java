package com.microservices.orders;

import com.microservices.orders.entity.Product;
import com.microservices.orders.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EnableEurekaClient
@SpringBootApplication
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner createAdmin(ProductRepository productRepository){
		return args -> {
			Product shoeProduct = new Product("Adidas","Shoes",12.00,12,"https://test.jpg");
			Product jeans = new Product("jeans","Clothing",3000.00,12,"https://test.jpg");
			Product shorts = new Product("shorts","Clothing",600.00,12,"https://test.jpg");
			Product watch = new Product("Fossil Smart Watch","Electronics",12.00,12,"https://test.jpg");

			productRepository.saveAll((Stream.of(shoeProduct,jeans,shorts,watch).collect(Collectors.toList())));

		};
	}


}
