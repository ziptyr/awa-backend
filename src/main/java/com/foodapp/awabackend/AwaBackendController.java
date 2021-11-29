package com.foodapp.awabackend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.foodapp.awabackend.entities.Account;
import com.foodapp.awabackend.entities.RestaurantType;
import com.foodapp.awabackend.repo.OrderRepo;
import com.foodapp.awabackend.repo.ProductRepo;
import com.foodapp.awabackend.repo.RestaurantRepo;
import com.foodapp.awabackend.repo.UserRepo;
import com.foodapp.awabackend.data.Restaurant;
import com.foodapp.awabackend.data.User;
import com.foodapp.awabackend.data.Order;
import com.foodapp.awabackend.data.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    UserRepo userRepo;
    @Autowired
    RestaurantRepo restaurantRepo;
    @Autowired 
    OrderRepo orderRepo;
    @Autowired 
    ProductRepo productRepo;
    
    @GetMapping("/public/restaurants")
    public ResponseEntity<List<Restaurant>> getRestaurants() {
        List<Restaurant> res = restaurantRepo.findAll();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/public/restaurants?id={restaurantId}&name={restaurantName}&type={type}&price={price}")
    public ResponseEntity<List<Restaurant>> searchRestaurants(
        @RequestParam long restaurantId, @RequestParam String restaurantName,
        @RequestParam RestaurantType type, @RequestParam int price
    ) {
        return null;
    }

    @GetMapping("/public/restaurants/{restaurantId}/menu")
    public ResponseEntity<List<Object[]>> getMenu(@PathVariable long restaurantId) {
    // public ResponseEntity<Product[]> getMenu(@PathVariable long restaurantId) {
        List<Object[]> menu = restaurantRepo.getMenuFromId(restaurantId);
        // Product[] menu = restaurantRepo.getMenuFromId(restaurantId);
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }
    @GetMapping("/public/users")
    public List<User> getUsers() {
        return userRepo.findAll();
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
        List<Order> res = orderRepo.getOrdersByUsername("moritz");
        return new ResponseEntity<List<Order>>(res, HttpStatus.OK);
    }

    @GetMapping("/customer/orders/{orderId}")
    public ResponseEntity<Map<String,Object>> getOrder(@PathVariable long orderId) {
        // Order res = orderRepo.findById(orderId).get();
        Map<String,Object> order = new HashMap<>();
        order.put("details", orderRepo.getOrderById(orderId));
        order.put("products", orderRepo.getOrderProducts(orderId));
        return new ResponseEntity<>(order, HttpStatus.OK);
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
