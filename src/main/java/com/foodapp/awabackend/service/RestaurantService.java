package com.foodapp.awabackend.service;

import java.util.List;
import java.util.Map;

import com.foodapp.awabackend.data.Restaurant;
import com.foodapp.awabackend.data.Type;
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

    public List<Restaurant> findAll() {
        return restaurantRepo.findAll();
    }

    public List<Restaurant> findByManagerName(String userName) {
        return restaurantRepo.findByManagerName(userName);
    }

    public Restaurant save(Map<String, String> restaurantData) {
        Restaurant restaurant = new Restaurant(
                restaurantData.get("restaurantName"),
                restaurantData.get("managerName"),
                restaurantData.get("restaurantAddress"),
                restaurantData.get("opens"),
                restaurantData.get("closes"),
                restaurantData.get("image"),
                Type.valueOf(restaurantData.get("type")),
                restaurantData.get("priceLevel")
            );

        restaurantRepo.save(restaurant);
        return restaurant;
    }

    public Restaurant updateRestaurant(Map<String, String> restaurantData) {

        Restaurant restaurant = this.findById(
            Long.parseLong(restaurantData.get("restaurantId")));

        if (restaurant == null) return null;

        restaurant.setRestaurantName(restaurantData.get("restaurantName"));
        restaurant.setRestaurantAddress(restaurantData.get("restaurantAddress"));
        restaurant.setOpens(restaurantData.get("opens"));
        restaurant.setCloses(restaurantData.get("closes"));
        restaurant.setImage(restaurantData.get("image"));
        restaurant.setType(Type.valueOf(restaurantData.get("type")));
        restaurant.setPriceLevel(Long.parseLong(restaurantData.get("priceLevel")));

        restaurantRepo.flush();
        return restaurant;
    }
}
