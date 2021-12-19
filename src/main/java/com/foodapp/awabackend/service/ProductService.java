package com.foodapp.awabackend.service;

import java.util.List;
import java.util.stream.Collectors;

import com.foodapp.awabackend.data.Product;
import com.foodapp.awabackend.data.Restaurant;
import com.foodapp.awabackend.repo.ProductRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    RestaurantService restaurantService;

    public List<Product> getProducts(List<Long> productIds) {

        return productIds.stream()
            .map(productId -> productRepo.findById(productId).orElse(null))
            .collect(Collectors.toList());
    }

    public Restaurant save(Product product) {
        productRepo.save(product);
        return restaurantService.findById(product.getRestaurantId());
    }
}
