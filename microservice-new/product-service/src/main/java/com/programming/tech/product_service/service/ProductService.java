package com.programming.tech.product_service.service;


import com.programming.tech.product_service.dto.ProductRequest;
import com.programming.tech.product_service.dto.ProductResponse;
import com.programming.tech.product_service.model.Product;
import com.programming.tech.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder().
                price(productRequest.getPrice())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .build();
        productRepository.save(product);
        log.info("product has been saved");

    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products= productRepository.findAll();
        List<ProductResponse> productResponses = products.stream().map(product -> {
            return ProductResponse.builder()
                    .id(product.getId())
                    .price(product.getPrice())
                    .name(product.getName())
                    .description(product.getDescription())
                    .build();
        }).collect(Collectors.toList());
        return productResponses;
    }
}
