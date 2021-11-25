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

}
