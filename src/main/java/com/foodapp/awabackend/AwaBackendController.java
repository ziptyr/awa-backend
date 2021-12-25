package com.foodapp.awabackend;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.foodapp.awabackend.repo.AccountRepo;
import com.foodapp.awabackend.repo.ProductRepo;
import com.foodapp.awabackend.repo.RestaurantRepo;
import com.foodapp.awabackend.service.AccountService;
import com.foodapp.awabackend.service.OrderService;
import com.foodapp.awabackend.service.ProductService;
import com.foodapp.awabackend.service.RestaurantService;
import com.foodapp.awabackend.data.Role;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.foodapp.awabackend.data.Account;
import com.foodapp.awabackend.data.Order;
import com.foodapp.awabackend.data.Product;
import com.foodapp.awabackend.data.Restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class AwaBackendController {

    @Autowired
    AccountService accountService;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @Autowired
    RestaurantService restaurantService;

    @GetMapping("/")
    public ResponseEntity<HttpHeaders> redirectToApiDocumentation() throws URISyntaxException{
        URI externalUri = new URI("https://awa-food-app.stoplight.io/docs/awa-food-app-api/YXBpOjI4OTEwMDQ1-awa-food-api");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(externalUri);

        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

    private String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PostMapping("/public/users")
    public void createUser(@RequestBody Account account) {
        BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        account.setPassword(pwEncoder.encode(account.getPassword()));
        accountService.save(account);
    }

    //@CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/public/restaurants")
    public List<Restaurant> getRestaurants() {
        return restaurantService.findAll();
    }

    @PostMapping("/customer/buy")
    public Order buyCart(@RequestBody Order order) {
        if (order.getDeliveryAddress() == null) {
            order.setDeliveryAddress(
                accountService.findByUsername(this.getUsername()).getAddress());
        }
        order.setUsername(this.getUsername());
        order.setEta("0:00");
        order.setOrderDate(LocalDate.now());

        return orderService.buy(order, this.getUsername());
    }

    @GetMapping("/customer/account")
    public Account getCustomerAccount() {
        return accountService.findByUsername(this.getUsername());
    }

    @PutMapping("/customer/orders/confirm")
    public Order confirmOrder(@RequestBody Long orderId) {
        return orderService.confirm(orderId, this.getUsername());
    }

    @GetMapping("/customer/orders")
    public List<Order> getOrders() {
        return orderService.findByUsername(this.getUsername());
    }

    @GetMapping("/manager/account")
    public Account getManagerAccount() {
        return accountService.findByUsername(this.getUsername());
    }

    @GetMapping("/manager/customer/{username}")
    public Account getCustomers(@PathVariable String username) {
        return accountService.findByUsername(username);
    }

    @GetMapping("/manager/restaurants")
    public List<Restaurant> getManagersRestaurants() {
        return restaurantService.findByManagerName(this.getUsername());
    }

    @PostMapping("/manager/restaurants")
    public Restaurant createRestaurant(@RequestBody Restaurant restaurant) {
        restaurant.setManagerName(this.getUsername());
        return restaurantService.save(restaurant);
    }

    @PutMapping("/manager/restaurants")
    public Restaurant updateRestaurant(@RequestBody Map<String, String> restaurantData) {
        return restaurantService.update(restaurantData, this.getUsername());
    }

    @GetMapping("/manager/restaurants/orders")
    public List<Order> getManagersOrders() {
        return orderService.findByManagerName(this.getUsername());
    }

    @PostMapping("/manager/restaurants/products")
    public Restaurant createProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping("/manager/restaurants/orders")
    public Order updateOrder(@RequestBody Map<String, String> update) {
        return orderService.update(update, this.getUsername());
    }

    //@PostMapping("/manager/image")
    //public ResponseEntity<Map<String,String>> uploadImage(@RequestParam("file") MultipartFile file){

    //    // Setup a cloudinary Client
    //    Cloudinary cl = new Cloudinary(ObjectUtils.asMap(
    //        "cloud_name", "dybhkvbdi",
    //        "api_key", "999868143333514",
    //        "api_secret", "jh0NZcGRSM5yXJ7Pt4cPorSElgA",
    //        "secure", true
    //    ));

    //    String imageUrl;

    //    try {
    //        // try to upload the image and extract image_url
    //        Map map = cl.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
    //        imageUrl = (String) map.get("url");
    //    } catch (IOException e) {
    //        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    //    }
    //    // Return the image_url if no Exception was thrown
    //    Map<String,String> urlJson = Collections.singletonMap("image_url", imageUrl);
    //    
    //    return new ResponseEntity<>(urlJson, HttpStatus.OK);
    //}
}