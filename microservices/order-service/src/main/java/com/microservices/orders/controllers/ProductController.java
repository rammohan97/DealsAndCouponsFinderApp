package com.microservices.orders.controllers;

import com.microservices.orders.dtos.ResponseDto;
import com.microservices.orders.entity.Product;
import com.microservices.orders.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productsController")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/saveProduct")
    public Product saveProduct(@RequestBody Product product){

        return  productService.save(product);
    }

    @GetMapping("/allProducts")
    public List<Product> fetchAllProducts(){
        return  productService.fetchAllProducts();
    }

    @GetMapping("byCategory/{categoryName}")
    public List<Product> getProductByCategory(@PathVariable String categoryName ) throws Exception {
        return productService.getProductByCategory(categoryName);
    }
    @PutMapping("/updateProduct")
    public Product updateProduct(@RequestBody Product product){

        return  productService.update(product);
    }
    @DeleteMapping("/deleteProduct")
    public ResponseDto deleteProduct(@RequestBody Product product){

        return  productService.delete(product);
    }

}
