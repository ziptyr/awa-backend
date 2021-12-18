package com.foodapp.awabackend.service;

import java.util.List;

import com.foodapp.awabackend.data.Restaurant;
import com.foodapp.awabackend.repo.RestaurantRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepo restaurantRepo;

    public Restaurant findById(long restaurantId) {
        return restaurantRepo.findById(restaurantId);
    }

    public List<Restaurant> findByManagerName(String userName) {
        return restaurantRepo.findByManagerName(userName);
    }
}
