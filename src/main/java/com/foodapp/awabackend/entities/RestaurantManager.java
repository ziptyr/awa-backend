package com.foodapp.awabackend.entities;

import java.util.List;

public class RestaurantManager extends Account {

    private List<Restaurant> restaurants;

    public RestaurantManager(String userName, List<Restaurant> restaurants) {
        super(userName);
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
