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
        @RequestBody Cart cart
    ) {
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
        // username static for now, extract from jwt later
        String username = "moritz";
        List<Order> res = orderRepo.getOrdersByUsername(username);
        return new ResponseEntity<List<Order>>(res, HttpStatus.OK);
    }

    @GetMapping("/customer/orders/{orderId}")
    public ResponseEntity<Map<String,Object>> getOrder(@PathVariable long orderId) {
        // username static for now, extract from jwt later
        String username = "moritz";
        Optional<Order> order = orderRepo.findById(orderId);
        // if no order for orderId can be found return 404
        if (order.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        // only return the Order if the user is the owner of the order
        if(order.get().username.equals(username)) {
            Map<String,Object> res = new HashMap<>();
            res.put("details", order.get());
            res.put("products", orderProductRepo.getOrderProducts(orderId));
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
        // order.userName is not the same as username return 403 FORBIDDEN
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/customer/orders/{orderId}/confirm")
    public HttpStatus confirmOrder(@PathVariable long orderId) {
        // username static for now, extract from jwt later
        String username = "moritz";
        Optional<Order> order = orderRepo.findById(orderId);
        // if no order for orderId can be found return 404
        if (order.isEmpty()){
            return HttpStatus.NOT_FOUND;
        }
        // if order does not belong to order or 
        // order has not been marked as delivered by manager
        // return 403
        // TODO: make sure that the status codes are correct
        if(!order.get().username.equals(username) || order.get().orderStatus > 10){
            return HttpStatus.FORBIDDEN;
        }
        orderRepo.updateOrderStatus(4, orderId);
        return HttpStatus.OK;
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
