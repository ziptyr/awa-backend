package com.foodapp.awabackend;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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

    private String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/")
    public ResponseEntity<HttpHeaders> redirectToApiDocumentation() throws URISyntaxException{
        URI externalUri = new URI("https://awa-food-app.stoplight.io/docs/awa-food-app-api/YXBpOjI4OTEwMDQ1-awa-food-api");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(externalUri);

        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

    // REDONE

    @PostMapping("/public/users")
    public void createUser(@RequestBody Map<String, String> account) {
        BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        accountService.save(
            new Account(
                account.get("username"),
                account.get("address"),
                Role.valueOf(account.get("role")),
                pwEncoder.encode(account.get("password"))
            )
        );
    }

    @GetMapping("/public/restaurants")
    public List<Restaurant> getRestaurants() {
        return restaurantService.findAll();
    }

    //@PostMapping("/customer/buy")
    //public String buyCart(@RequestBody Cart cart) {
    //    String userName = SecurityContextHolder.getContext().getAuthentication().getName();

    //    
    ////    boolean success = cart.placeOrder(username, orderRepo, productRepo);

    ////    if(success) {
    ////        return new ResponseEntity<>("Order placed",HttpStatus.OK);
    ////    } else {
    ////        return new ResponseEntity<>("Order can only have products from one restaurant",HttpStatus.BAD_REQUEST);
    ////    }
    //    return null;
    //}

    @GetMapping("/customer/account")
    public Account getCustomerAccount() {
        return accountService.findByUsername(this.getUsername());
    }

    //@PutMapping("/customer/orders/{orderId}/confirm")
    //public ResponseEntity<String> confirmOrder(@PathVariable long orderId) {
    //    // username static for now, extract from jwt later
    //    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    //    Optional<Order> order = orderRepo.findById(orderId);
    //    // if no order for orderId can be found return 404
    //    if (!order.isPresent()){
    //        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    //    }
    //    // if order does not belong to order or 
    //    // order has not been marked as delivered by manager
    //    // return 403
    //    if(!order.get().username.equals(username) || order.get().orderStatus != 3){
    //        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    //    }
    //    orderRepo.updateOrderStatus(4, orderId);
    //    return new ResponseEntity<>("delivery confirmed", HttpStatus.OK);
    //}

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
        return restaurantService.updateRestaurant(restaurantData);
    }

    @GetMapping("/manager/restaurants/orders")
    public List<Order> getManagersOrders() {
        return orderService.findByManagerName(this.getUsername());
    }

    @PostMapping("/manager/restaurants/products")
    public Restaurant createProduct(@RequestBody Product product) {
    //    String manager = SecurityContextHolder.getContext().getAuthentication().getName();
    //    // Check if user is manager of restaurantId
    //    Optional<Restaurant> restaurant = restaurantRepo.findById(restaurantId);
    //    if(!restaurant.isPresent()) {
    //        return new ResponseEntity<>("RESTAURANT NOT FOUND",HttpStatus.NOT_FOUND);
    //    } else if (!restaurant.get().getManagerName().equals(manager)) {
    //        return new ResponseEntity<>("User does not own restaurant",HttpStatus.FORBIDDEN);
    //    }
    //    // make sure product is created for the restaurant 
    //    // specified in the request path
    //    productRepo.createProduct(restaurantId, np.name, np.description, np.price, np.image, np.category);
    //    return new ResponseEntity<>("CREATED",HttpStatus.CREATED);
        return productService.save(product);
    }

    // REDONE END

    // OLD

    //@GetMapping("/manager/restaurant/orders/{orderId}")
    //public ResponseEntity<Map<String,Object>> getOrderAsManager(@PathVariable long orderId) {
    //    String manager = SecurityContextHolder.getContext().getAuthentication().getName();
    //    // Check if user is manager of restaurantId
    //    Optional<Order> order = orderRepo.findById(orderId);
    //    if (!order.isPresent()){
    //        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    //    }
    //    // Check if user is manager of restaurantId from order
    //    Optional<Restaurant> restaurant = restaurantRepo.findById(order.get().restaurantId);
    //    if(restaurant.isPresent()){
    //        if(!restaurant.get().managerName.equals(manager)){
    //            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    //        }
    //    } else {
    //        // Invalid restaurantId
    //        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    //    }
    //    // if all is ok
    //    Map<String,Object> res = new HashMap<>();
    //    res.put("details", order.get());
    //    res.put("products", orderProductRepo.getOrderProducts(orderId));
    //    return new ResponseEntity<>(res, HttpStatus.OK);
    //}

    //@PutMapping("/manager/restaurants/orders/{orderId}")
    //public ResponseEntity<String> updateOrderStatus(
    //   @PathVariable long orderId, @RequestBody OrderUpdate update
    //) {
    //    String manager = SecurityContextHolder.getContext().getAuthentication().getName();
    //    // Check if user is manager of restaurantId
    //    Optional<Order> order = orderRepo.findById(orderId);
    //    if (!order.isPresent()){
    //        return new ResponseEntity<>("ORDER NOT FOUND", HttpStatus.NOT_FOUND);
    //    }
    //    // Check if user is manager of restaurantId from order
    //    Optional<Restaurant> restaurant = restaurantRepo.findById(order.get().restaurantId);
    //    if(restaurant.isPresent()){
    //        if(!restaurant.get().managerName.equals(manager)){
    //            return new ResponseEntity<>("User does not own corresponding restaurant",HttpStatus.FORBIDDEN);
    //        }
    //    } else {
    //        // Invalid restaurantId
    //        return new ResponseEntity<>("RESTAURANT NOT FOUND", HttpStatus.BAD_REQUEST);
    //    }
    //    // if all is okay set order status and optionally update eta
    //    orderRepo.updateOrderStatus(update.status, orderId);
    //    // only if eta is set in request body
    //    if(update.eta.isPresent()){
    //        orderRepo.updateEta(update.eta.get(), orderId);
    //    }


    //    return new ResponseEntity<>("Order status updated", HttpStatus.OK);
    //}

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

    // OLD END
}
