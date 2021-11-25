package com.foodapp.awabackend.entities;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class Restaurant {

    private int restaurantId;
    private String restaurantName;
    private String managerName;
    private String[] address;
    private ZonedDateTime opens;
    private ZonedDateTime closes;
    private String image;
    private RestaurantType type;
    private int priceLevel;
    private List<Product> menu;

    public Restaurant(
        String restaurantName, String managerName, String[] address, ZonedDateTime opens,
        ZonedDateTime closes, String image, RestaurantType type, int priceLevel, List<Product> menu)
    {
        this.restaurantName = restaurantName;
        this.managerName = managerName;
        this.address = address;
        this.opens = opens;
        this.closes = closes;
        this.image = image;
        this.type = type;
        this.priceLevel = priceLevel;
        this.menu = menu;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getManagerName() {
        return managerName;
    }

    public String[] getAddress() {
        return address;
    }

    public void setAddress(String[] address) {
        this.address = address;
    }

    public ZonedDateTime getOpens() {
        return opens;
    }

    public void setOpens(ZonedDateTime opens) {
        this.opens = opens;
    }

    public ZonedDateTime getCloses() {
        return closes;
    }

    public void setCloses(ZonedDateTime closes) {
        this.closes = closes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public RestaurantType getType() {
        return type;
    }

    public void setType(RestaurantType type) {
        this.type = type;
    }

    public int getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(int priceLevel) {
        this.priceLevel = priceLevel;
    }

    public List<Product> getMenu() {
        return menu;
    }

    public void setMenu(List<Product> menu) {
        this.menu = menu;
    }
}
