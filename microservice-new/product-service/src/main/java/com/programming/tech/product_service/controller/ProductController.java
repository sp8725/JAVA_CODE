package com.programming.tech.product_service.controller;

import com.programming.tech.product_service.dto.ProductRequest;
import com.programming.tech.product_service.dto.ProductResponse;
import com.programming.tech.product_service.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }

    @GetMapping
    public List<ProductResponse> getProducts(){
        return productService.getAllProducts();
    }
}
