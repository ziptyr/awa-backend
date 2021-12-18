package com.foodapp.awabackend.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.foodapp.awabackend.data.OrdersProducts;
import com.foodapp.awabackend.data.Product;
import com.foodapp.awabackend.repo.OrdersProductsRepo;
import com.foodapp.awabackend.repo.ProductRepo;

import org.springframework.beans.factory.annotation.Autowired;

public class ProductService {

    @Autowired
    ProductRepo productRepo;

    public List<Product> getProducts(List<Long> productIds) {

        return productIds.stream()
            .map(productId -> productRepo.findById(productId).orElse(null))
            .collect(Collectors.toList());
    }
}
