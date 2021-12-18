package com.foodapp.awabackend.repo;

import java.util.List;

import com.foodapp.awabackend.data.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>{

    public Product findById(long productId);
    public List<Product> findByRestaurantId(long restaurantId);
}
