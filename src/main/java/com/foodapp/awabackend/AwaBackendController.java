package com.foodapp.awabackend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.foodapp.awabackend.repo.OrderProductsRepo;
import com.foodapp.awabackend.repo.OrderRepo;
import com.foodapp.awabackend.repo.ProductRepo;
import com.foodapp.awabackend.repo.RestaurantRepo;
import com.foodapp.awabackend.repo.UserRepo;
import com.foodapp.awabackend.data.Restaurant;
import com.foodapp.awabackend.data.User;
import com.foodapp.awabackend.entities.Account;
import com.foodapp.awabackend.data.Order;
import com.foodapp.awabackend.data.OrderProductRelation;
import com.foodapp.awabackend.data.Product;
import com.foodapp.awabackend.data.Cart;

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
    @Autowired
    OrderProductsRepo orderProductRepo;

    @GetMapping("/public/restaurants")
    public ResponseEntity<List<Restaurant>> searchRestaurants(
        @RequestParam Optional<String> restaurantName,
        @RequestParam Optional<String> type, 
        @RequestParam Optional<Integer> price,
        @RequestParam Optional<String> address
    ) {
        String name = "";
        String typeSearch = "";
        int priceMin = 1;
        int priceMax = 3;
        String addr = "%";

        if(restaurantName.isPresent()) name = restaurantName.get();
        if(type.isPresent()) typeSearch = type.get();
        if(price.isPresent()) {
            priceMin = price.get();
            priceMax = price.get();
        }
        if(address.isPresent()) addr = "%"+address.get()+"%";

        List<Restaurant> res = restaurantRepo.search("%"+name+"%","%"+typeSearch+"%", priceMin,priceMax,addr);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @GetMapping("/public/restaurants/{restaurantId}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable long restaurantId) {
        Restaurant res = restaurantRepo.findById(restaurantId).get();
        return new ResponseEntity<Restaurant>(res, HttpStatus.OK);
    }
    @GetMapping("/public/restaurants/{restaurantId}/menu")
    public ResponseEntity<List<Product>> getMenu(@PathVariable long restaurantId) {
        List<Product> menu = productRepo.getMenuFromId(restaurantId);
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }
    @GetMapping("/public/users")
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @PostMapping("/public/users")
    public HttpStatus createUser(@RequestBody User newUser) {
        try {
            // newUser.setPasswordHash(encoder.encode(newUser.getPasswordHash()));
        } catch (Exception e){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        //////////////////////////////////////
        // Password should be salted and hashed before inserting the user
        // But for that to work, authentication must first be implemented.
        //////////////////////////////////////
        userRepo.save(newUser);
        return HttpStatus.CREATED;
    }

    //////////////////////////////////////
    // This might get handled by Spring security allready
    //////////////////////////////////////
    @PostMapping("/public/users/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> userInfo) {
        return null;
    }


    //////////////////////////////////////
    // The following routes need to allways 
    // check if the user calling the route
    // is allowed to perform the actions.
    // Some routes need to know which
    // user is calling the route.
    // For that, security with jwt must work!
    //////////////////////////////////////

    @PostMapping("/customer/buy")
    public HttpStatus buyCart(
        // @RequestBody Map<Long, Integer> list
        @RequestBody Cart cart
    ) {
        // Cart cart = new Cart(list);
        // username is static for now, should be 
        // extracted from jwt token.
        String username = "moritz";
        boolean success = cart.placeOrder(username, orderRepo, productRepo);
        if(success) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @GetMapping("/customer/orders")
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> res = orderRepo.getOrdersByUsername("moritz");
        return new ResponseEntity<List<Order>>(res, HttpStatus.OK);
    }

    @GetMapping("/customer/orders/{orderId}")
    public ResponseEntity<Map<String,Object>> getOrder(@PathVariable long orderId) {
        Map<String,Object> order = new HashMap<>();
        order.put("details", orderRepo.getOrderById(orderId));
        order.put("products", orderProductRepo.findAll());
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
        List<Order> orders = orderRepo.getOrdersByRestaurantId(restaurantId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/manager/restaurants/{restaurantId}/orders/new")
    public ResponseEntity<List<Order>> getNewOrders(@PathVariable long restaurantId) {
        List<Order> orders = orderRepo.getNewOrdersByRestaurantId(restaurantId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/manager/restaurants/{restaurantId}/products")
    public HttpStatus createProduct(
        @PathVariable long restaurantId, @RequestBody Product newProduct
    ) {
        productRepo.save(newProduct);
        return HttpStatus.CREATED;
    }

    @GetMapping("/manager/restaurant/orders/{orderId}")
    public ResponseEntity<Order> getOrderAsManager(@PathVariable long orderId) {
        Optional<Order> order = orderRepo.findById(orderId);
        if (order.isPresent()){
            return new ResponseEntity<>(order.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/manager/restaurants/orders/{orderId}")
    public ResponseEntity<String> updateOrderStatus(
        @PathVariable long orderId, @RequestBody int status
    ) {
        return null;
    }
}
