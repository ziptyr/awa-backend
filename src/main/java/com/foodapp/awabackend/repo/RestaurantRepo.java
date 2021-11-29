package com.foodapp.awabackend.repo;

import com.foodapp.awabackend.data.Restaurant;
import com.foodapp.awabackend.data.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Long>{

    // final String getMenuQuery = "select * from products where restaurant_id =?";
    final String search = "select * from restaurants" 
        + " where restaurant_name like ?"
        // + " and address like ?"
        + " and type like ?"
        + " and price_level >= ?"
        + " and price_level <= ?"
        + " and address like ?;";

    // @Query(value = getMenuQuery, nativeQuery = true)
    // List<Product> getMenuFromId(long restaurantId);

    @Query(value = search, nativeQuery = true)
    List<Restaurant> search( String name, String type, int priceMin, int priceMax, String addr);
    
}
