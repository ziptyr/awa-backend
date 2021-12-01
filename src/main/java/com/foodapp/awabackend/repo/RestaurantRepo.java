package com.foodapp.awabackend.repo;

import com.foodapp.awabackend.data.Restaurant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Long>{

    final String search = "select * from restaurants" 
        + " where restaurant_name like ?"
        // + " and address like ?"
        + " and type like ?"
        + " and price_level >= ?"
        + " and price_level <= ?"
        + " and address like ?;";

    @Query(value = search, nativeQuery = true)
    List<Restaurant> search( String name, String type, int priceMin, int priceMax, String addr);

    final String getByManager = "select * from restaurants where manager_name = ?;";

    @Query(value = getByManager, nativeQuery = true)
    List<Restaurant> getByManager(String manager);

    final String createRestaurant = "insert into restaurants("
        + " restaurant_name, manager_name, address, opens, closes, image, type, price_level)"
        + " values(?,?,?,?,?,?,?,?)";

    @Transactional
    @Modifying
    @Query(value = createRestaurant, nativeQuery = true) 
    void createRestaurant(String name, String manager, String addr, String opens, String closes, String image, String type, long priceLevel);
    
}
