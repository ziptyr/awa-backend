package com.foodapp.awabackend.data;

import java.util.Map;
import java.util.Optional;

import com.foodapp.awabackend.repo.ProductRepo;
import com.foodapp.awabackend.repo.OrderRepo;


/**
 * This class holds the datastructure that will be parsed from 
 * the route POST /customer/buy. It also performs 
 * input validation on the carts content; it checks if 
 * all products are from the same restaurant
 */
public class Cart {

    public Map<Long, Integer> products;   
    public Optional<String> deliveryAddress;

    public Cart(Map<Long, Integer> list){
        this.products = list;
    }

    public Cart(){}

    public boolean placeOrder(String username, OrderRepo orderRepo, ProductRepo productRepo){
        // Get a primary key for the order_id from the database
        long order_id = orderRepo.getNextPrimaryKey();
        long valid = validate(productRepo); // -1 if invalid, otherwise the restaurant_id
        if(valid > -1){
            long restaurant_id = valid;
            orderRepo.createOrder(order_id, restaurant_id, username, username);
            for (Long prod_id : products.keySet()){
                orderRepo.insertOrderProductRelation(order_id, prod_id, products.get(prod_id), prod_id);
            }
            orderRepo.completeOrderPlacement(order_id, order_id);
            if (deliveryAddress.isPresent()){
                orderRepo.updateDeliveryAddress(deliveryAddress.get(), order_id);
            }
        } else {
            return false;
        }
        return true;
    }
    // if all products have the same restaurant_id, return 
    // the restaurant_id, otherwise return -1
    private long validate(ProductRepo pr){
        long restaurant_id = -1;

        for(long prod_id : products.keySet()){
            Optional<Product> res = pr.findById(prod_id);
            if(res.isPresent()){
                long id = res.get().restaurantId;
                if(restaurant_id == -1){
                    restaurant_id = id;
                    continue;
                } 
                if (restaurant_id != id) {
                    return -1;
                }
            }

        }
        return restaurant_id;
    }
}
