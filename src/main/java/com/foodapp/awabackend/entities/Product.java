package com.foodapp.awabackend.entities;

public class Product {

    private long productId;
    private String restaurantName;
    private String name;
    private String description;
    private double price;
    private String image;
    private String[] category;

    public Product(
        String restaurantName, String name, String description, double price, String image,
        String[] category)
    {
        this.restaurantName = restaurantName;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
    }

    public long getProductId() {
        return productId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }

}
