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

    public List<Order> findByUsername(String username) {
        return orderRepo.findByUsername(username);
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
}
