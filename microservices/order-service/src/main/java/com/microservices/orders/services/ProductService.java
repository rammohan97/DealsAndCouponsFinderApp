package com.microservices.orders.services;

import com.microservices.orders.dtos.ResponseDto;
import com.microservices.orders.entity.Product;
import com.microservices.orders.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product save(Product product) {
        return  productRepository.save(product);
    }

    public List<Product> fetchAllProducts() {
       return productRepository.findAll();
    }

    public List<Product> getProductByCategory(String categoryName) throws  Exception {
        List<Product> products = productRepository.findAll();
        if(products.isEmpty()){
            throw  new Exception("No Product FOund");
        }
        return products.stream().filter(product -> product.getCategory().equalsIgnoreCase(categoryName)).collect(Collectors.toList());
    }

    public Product update(Product product) {
        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        if(optionalProduct.isPresent()){
            return  productRepository.save(product);
        }else{
            return null;
        }
    }

    public ResponseDto delete(Product product) {
        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        if(optionalProduct.isPresent()){
              productRepository.delete(product);
            return new  ResponseDto(true);
        }else{
            return new  ResponseDto(false);
        }
    }
}
