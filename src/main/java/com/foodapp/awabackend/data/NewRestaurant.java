package com.foodapp.awabackend.data;

/**
 * Used to parse a new restaurant from POST /manager/restaurants
 */
public class NewRestaurant {
    public String restaurantName;
    public String address;
    public String opens;
    public String closes;
    public String image;
    public String type;
    public long priceLevel;

    public NewRestaurant(){}
}
