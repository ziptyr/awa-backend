package com.foodapp.awabackend;

import java.util.List;
import java.util.Map;

import com.foodapp.awabackend.entities.Account;
import com.foodapp.awabackend.entities.Order;
import com.foodapp.awabackend.entities.Product;
import com.foodapp.awabackend.entities.Restaurant;
import com.foodapp.awabackend.entities.RestaurantType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class AwaBackendController {
    
    @GetMapping("/public/restaurants")
    public ResponseEntity<Restaurant> getRestaurants() {
        return null;
    }

    @GetMapping("/public/restaurants?id={restaurantId}&name={restaurantName}&type={type}&price={price}")
    public ResponseEntity<List<Restaurant>> searchRestaurants(
        @RequestParam long restaurantId, @RequestParam String restaurantName,
        @RequestParam RestaurantType type, @RequestParam int price
    ) {
        return null;
    }

    @GetMapping("/public/restaurants/{restaurantId}/menu")
    public ResponseEntity<List<Product>> getMenu(@PathVariable long restaurantId) {
        return null;
    }

    @PostMapping("/public/users")
    public ResponseEntity<String> createUser(@RequestBody Account newUser) {
        return null;
    }

    @PostMapping("/public/users/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> userInfo) {
        return null;
    }

    @PostMapping("/customer/buy")
    public ResponseEntity<String> buyCart() {
        return null;
    }

    @GetMapping("/customer/orders")
    public ResponseEntity<List<Order>> getOrders() {
        return null;
    }

    @GetMapping("/customer/orders/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable long orderId) {
        return null;
    }

    @PutMapping("/customer/orders/confirm")
    public ResponseEntity<String> confirmOrder(@RequestBody int status) {
        return null;
    }

    @PostMapping("/manager/restaurants")
    public ResponseEntity<String> createRestaurant(@RequestBody Restaurant newRestaurant) {
        return null;
    }

    @GetMapping("/manager/restaurants/{restaurantId}/orders")
    public ResponseEntity<List<Order>> getRestaurantsOrders(@PathVariable long restaurantId) {
        return null;
    }

    @GetMapping("/manager/restaurants/{restaurantId}/orders/new")
    public ResponseEntity<List<Order>> getNewOrders(@PathVariable long restaurantId) {
        return null;
    }

    @PostMapping("/manager/restaurants/{restaurantId}/products")
    public ResponseEntity<String> createProduct(
        @PathVariable long restaurantId, @RequestBody Product newProduct
    ) {
        return null;
    }

    @GetMapping("/manager/restaurant/orders/{orderId}")
    public ResponseEntity<Order> getOrderAsManager(@PathVariable long orderId) {
        return null;
    }

    @PutMapping("/manager/restaurants/orders/{orderId}")
    public ResponseEntity<String> updateOrderStatus(
        @PathVariable long orderId, @RequestBody int status
    ) {
        return null;
    }
}
