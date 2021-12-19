package com.foodapp.awabackend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.foodapp.awabackend.data.Order;
import com.foodapp.awabackend.data.OrdersProducts;
import com.foodapp.awabackend.data.Product;
import com.foodapp.awabackend.repo.OrderRepo;
import com.foodapp.awabackend.repo.OrdersProductsRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    OrdersProductsRepo ordersProductsRepo;

    @Autowired
    ProductService productService;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    AccountService accountService;

    public Order findById(Long orderId) {
        return orderRepo.findById(orderId).orElse(null);
    }

    public List<Order> findByUsername(String username) {
        return orderRepo.findByUsername(username);
    }

    public List<Order> findByManagerName(String username) {
        return restaurantService.findByManagerName(username)
            .stream()
            .map(restaurant -> orderRepo.findByRestaurantId(restaurant.getRestaurantId()))
            .flatMap(List::stream)
            .collect(Collectors.toList());
    }

    public Order update(Map<String, String> orderData, String managerName) {
        Order order = this.findByManagerName(managerName)
            .stream()
            .filter(o -> o.getRestaurantId() == Long.parseLong(orderData.get("restaurantName")))
            .findFirst()
            .orElse(null);

        if (order == null) return null;

        order.setOrderStatus(Integer.parseInt(orderData.get("orderData")));
        order.setEta(orderData.get("eta"));

        orderRepo.flush();
        return order;
    }

    public Order confirm(Long orderId, String username) {
        Order order = this.findByUsername(username)
            .stream()
            .filter(o -> o.getOrderId() == orderId)
            .findFirst()
            .orElse(null);

        order.setOrderStatus(4);
        orderRepo.flush();
        return order;
    }

    public Map<String, Object> getOrder(long orderId) {
        Map<String, Object> order = new HashMap<>();

        Order orderDetails = orderRepo.findById(orderId);

        List<OrdersProducts> ordersProducts = ordersProductsRepo.findByOrderId(orderId);

        List<Product> products = productService.getProducts(
            ordersProducts.stream()
                .map(o -> o.getProductId())
                .collect(Collectors.toList())
        );

        List<Map<Map<String, Product>, Map<String, Integer>>> productDetails = products.stream()
            .map(product -> 
                    Map.of(
                        Map.of("product", product),
                        Map.of("amount", 1)
                    )
                )
            .collect(Collectors.toList());

        order.put("orderDetails", orderDetails);
        order.put("productDetails", productDetails);

        return order;
    }

    public Order buy(Order order, String username) {
        order.setUsername(username);

        if (order.getDeliveryAddress() == "") {
            order.setDeliveryAddress(accountService.findByUsername(username).getAddress());
        }

        return orderRepo.save(order);
    }
}
