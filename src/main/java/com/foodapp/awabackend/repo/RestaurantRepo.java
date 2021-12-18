package com.foodapp.awabackend.repo;

import java.util.List;

import com.foodapp.awabackend.data.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {

    public Restaurant findById(long restaurantId);
    public List<Restaurant> findByManagerName(String userName);
}
