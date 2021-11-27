package com.foodapp.awabackend.repo;

import java.util.List;

import com.foodapp.awabackend.data.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepo extends JpaRepository<Product, Long>{
    

    final String getMenuQuery = "select * from products where restaurant_id =?";

    @Query(value = getMenuQuery, nativeQuery = true)
    List<Product> getMenuFromId(long restaurantId);
    
}
