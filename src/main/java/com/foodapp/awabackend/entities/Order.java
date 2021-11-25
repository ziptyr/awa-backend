package com.foodapp.awabackend.entities;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public class Order {

    private long orderId;
    private int restaurantId;
    private String userName;
    private int status;
    private ZonedDateTime orderDate;
    private double totalPrice;
    private List<Map<Product, Integer>> shoppingCart;

    public Order(int restaurantId, String userName, int status, ZonedDateTime orderDate, double totalPrice,
            List<Map<Product, Integer>> shoppingCart) {
        this.restaurantId = restaurantId;
        this.userName = userName;
        this.status = status;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.shoppingCart = shoppingCart;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getUserName() {
        return userName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Map<Product, Integer>> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(List<Map<Product, Integer>> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
