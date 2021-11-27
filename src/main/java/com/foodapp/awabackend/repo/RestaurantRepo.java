package com.foodapp.awabackend.repo;

import com.foodapp.awabackend.data.Restaurant;

import java.util.List;

import com.foodapp.awabackend.data.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Long>{

    final String getMenuQuery = "select * from products where restaurant_id =?";

    @Query(value = getMenuQuery, nativeQuery = true)
    List<Object[]> getMenuFromId(long restaurantId);
    
}
