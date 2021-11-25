package com.foodapp.awabackend;

import java.util.List;

import com.foodapp.awabackend.entities.Order;
import com.foodapp.awabackend.entities.Product;
import com.foodapp.awabackend.entities.Restaurant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class AwaBackendController {
    
    @GetMapping("/public/restaurants")
    public ResponseEntity<Restaurant> getRestaurants() {
        return null;
    }

    @PutMapping("/customer/orders/confirm")
    public ResponseEntity<String> confirmOrder() {
        return null;
    }

    @PostMapping("/public/users")
    public ResponseEntity<String> createUser() {
        return null;
    }

    @PutMapping("/manager/restaurants/orders/{orderId}")
    public ResponseEntity<String> updateOrderStatus() {
        return null;
    }

    @PostMapping("/customer/buy")
    public ResponseEntity<String> buyCart() {
        return null;
    }

    @GetMapping("/public/restaurants/{restaurantId}/menu")
    public ResponseEntity<List<Product>> getMenu() {
        return null;
    }

    @PostMapping("/public/users/login")
    public ResponseEntity<String> login() {
        return null;
    }

    @GetMapping("/customer/orders")
    public ResponseEntity<List<Order>> getOrders() {
        return null;
    }

    @GetMapping("/customer/orders/{orderId}")
    public ResponseEntity<Order> getOrder() {
        return null;
    }

    @PostMapping("/manager/restaurants/{restaurantId}/products")
    public ResponseEntity<String> createProduct() {
        return null;
    }

    @PostMapping("/manager/restaurants")
    public ResponseEntity<String> createRestaurant() {
        return null;
    }

    @GetMapping("/manager/restaurants/{restaurantId}/orders/new")
    public ResponseEntity<List<Order>> getNewOrders() {
        return null;
    }

    @GetMapping("/manager/restaurant/orders/{orderId}")
    public ResponseEntity<Order> getOrderAsManager() {
        return null;
    }

    @GetMapping("/manager/restaurants/{restaurantId}/orders")
    public ResponseEntity<List<Order>> getRestaurantsOrders() {
        return null;
    }
}
