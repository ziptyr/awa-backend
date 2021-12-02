package com.foodapp.awabackend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.foodapp.awabackend.repo.OrderProductsRepo;
import com.foodapp.awabackend.repo.OrderRepo;
import com.foodapp.awabackend.repo.ProductRepo;
import com.foodapp.awabackend.repo.RestaurantRepo;
import com.foodapp.awabackend.repo.AccountRepo;
import com.foodapp.awabackend.data.Restaurant;
import com.foodapp.awabackend.data.Account;
import com.foodapp.awabackend.data.NewRestaurant;
import com.foodapp.awabackend.data.Order;
import com.foodapp.awabackend.data.Product;
import com.foodapp.awabackend.data.Cart;
import com.foodapp.awabackend.data.NewProduct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    AccountRepo accountRepo;
    @Autowired
    RestaurantRepo restaurantRepo;
    @Autowired 
    OrderRepo orderRepo;
    @Autowired 
    ProductRepo productRepo;
    @Autowired
    OrderProductsRepo orderProductRepo;

    @GetMapping("/public")
    public String helloPublic() {
        return "works";
    }

    @GetMapping("/customer")
    public String helloCustomer() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return "Customer: Hello " + username;
    }

    @GetMapping("/manager")
    public String helloManager() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return "Manager: Hello " + username;
    }

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
    public ResponseEntity<String> createUser(@RequestBody Account newUser) {
        /**
         * salts and encodes the password befor saving to database
         */

        newUser.encodePassword();
        accountRepo.save(newUser);
        return new ResponseEntity<>("CREATED", HttpStatus.CREATED);
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
    public ResponseEntity<String> buyCart(
       @RequestBody Cart cart
    ) {
       // username is static for now, should be 
       // extracted from jwt token.
       String username = "moritz";
       boolean success = cart.placeOrder(username, orderRepo, productRepo);
       if(success) {
           return new ResponseEntity<>("Order placed",HttpStatus.OK);
       } else {
           return new ResponseEntity<>("Order can only have products from one restaurant",HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<String> confirmOrder(@PathVariable long orderId) {
       // username static for now, extract from jwt later
       String username = "moritz";
       Optional<Order> order = orderRepo.findById(orderId);
       // if no order for orderId can be found return 404
       if (order.isEmpty()){
           return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
       }
       // if order does not belong to order or 
       // order has not been marked as delivered by manager
       // return 403

       // 0: order placed 
       // 1: preparing
       // 2: ready for delivery
       // 3: delivering
       // 4: delivered

       if(!order.get().username.equals(username) || order.get().orderStatus >= 3){
           return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
       }
       orderRepo.updateOrderStatus(4, orderId);
       return new ResponseEntity<>("delivery confirmed", HttpStatus.OK);
    }

    @GetMapping("/manager/restaurants")
    public ResponseEntity<List<Restaurant>> getManagerRestaurants() {
       // using static manager name for now, extract from jwt eventually
       String manager = "lucas";
       List<Restaurant> restaurants = restaurantRepo.getByManager(manager);

       return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @PostMapping("/manager/restaurants")
    public ResponseEntity<String> createRestaurant(@RequestBody NewRestaurant r) {
       // using static manager name for now, extract from jwt eventually
       String manager = "lucas";
       restaurantRepo.createRestaurant(r.restaurantName, manager, r.address, r.opens, r.closes, r.image, r.type, r.priceLevel);

       return new ResponseEntity<>("CREATED",HttpStatus.CREATED);
    }

    @GetMapping("/manager/restaurants/{restaurantId}/orders")
    public ResponseEntity<List<Order>> getRestaurantsOrders(@PathVariable long restaurantId) {
       // using static manager name for now, extract from jwt eventually
       String manager = "lucas";
       // Check if user is manager of restaurantId
       Optional<Restaurant> restaurant = restaurantRepo.findById(restaurantId);
       if(restaurant.isEmpty()) {
           return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
       } else if (!restaurant.get().getManagerName().equals(manager)) {
           return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
       }

       List<Order> orders = orderRepo.getOrdersByRestaurantId(restaurantId);
       return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/manager/restaurants/{restaurantId}/orders/new")
    public ResponseEntity<List<Order>> getNewOrders(@PathVariable long restaurantId) {
       // using static manager name for now, extract from jwt eventually
       String manager = "lucas";
       // Check if user is manager of restaurantId
       Optional<Restaurant> restaurant = restaurantRepo.findById(restaurantId);
       if(restaurant.isEmpty()) {
           return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
       } else if (!restaurant.get().getManagerName().equals(manager)) {
           return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
       }
       List<Order> orders = orderRepo.getNewOrdersByRestaurantId(restaurantId);
       return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/manager/restaurants/{restaurantId}/products")
    public ResponseEntity<String> createProduct(
       @PathVariable long restaurantId, @RequestBody NewProduct np
    ) {
       // using static manager name for now, extract from jwt eventually
       String manager = "lucas";
       // Check if user is manager of restaurantId
       Optional<Restaurant> restaurant = restaurantRepo.findById(restaurantId);
       if(restaurant.isEmpty()) {
           return new ResponseEntity<>("RESTAURANT NOT FOUND",HttpStatus.NOT_FOUND);
       } else if (!restaurant.get().getManagerName().equals(manager)) {
           return new ResponseEntity<>("User does not own restaurant",HttpStatus.FORBIDDEN);
       }
       // make sure product is created for the restaurant 
       // specified in the request path
       productRepo.createProduct(restaurantId, np.name, np.description, np.price, np.image, np.category);
       return new ResponseEntity<>("CREATED",HttpStatus.CREATED);
    }

    @GetMapping("/manager/restaurant/orders/{orderId}")
    public ResponseEntity<Order> getOrderAsManager(@PathVariable long orderId) {
       // using static manager name for now, extract from jwt eventually
       String manager = "lucas";
       // Check if user is manager of restaurantId
       Optional<Order> order = orderRepo.findById(orderId);
       if (order.isEmpty()){
           return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
       }
       // Check if user is manager of restaurantId from order
       Optional<Restaurant> restaurant = restaurantRepo.findById(order.get().restaurantId);
       if(restaurant.isPresent()){
           if(!restaurant.get().managerName.equals(manager)){
               return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
           }
       } else {
           // Invalid restaurantId
           return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
       }
       // if all is ok
       return new ResponseEntity<>(order.get(), HttpStatus.CREATED);
    }


    @PutMapping("/manager/restaurants/orders/{orderId}")
    public ResponseEntity<String> updateOrderStatus(
       @PathVariable long orderId, @RequestBody int status
    ) {
       // using static manager name for now, extract from jwt eventually
       String manager = "lucas";
       // Check if user is manager of restaurantId
       Optional<Order> order = orderRepo.findById(orderId);
       if (order.isEmpty()){
           return new ResponseEntity<>("ORDER NOT FOUND", HttpStatus.NOT_FOUND);
       }
       // Check if user is manager of restaurantId from order
       Optional<Restaurant> restaurant = restaurantRepo.findById(order.get().restaurantId);
       if(restaurant.isPresent()){
           if(!restaurant.get().managerName.equals(manager)){
               return new ResponseEntity<>("User does not own corresponding restaurant",HttpStatus.FORBIDDEN);
           }
       } else {
           // Invalid restaurantId
           return new ResponseEntity<>("RESTAURANT NOT FOUND", HttpStatus.BAD_REQUEST);
       }
       // if all is okay
       // 0: order placed 
       // 1: preparing
       // 2: ready for delivery
       // 3: delivering
       // 4: delivered
       orderRepo.updateOrderStatus(status, orderId);
       return new ResponseEntity<>("Order status updated", HttpStatus.OK);
    }
}
