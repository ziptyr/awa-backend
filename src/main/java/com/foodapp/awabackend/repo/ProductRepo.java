package com.foodapp.awabackend.repo;

import java.util.List;

import com.foodapp.awabackend.data.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepo extends JpaRepository<Product, Long>{
    

    final String getMenuQuery = "select * from products where restaurant_id = ?;";

    @Query(value = getMenuQuery, nativeQuery = true)
    List<Product> getMenuFromId(long restaurantId);
    
    final String createProduct = "insert into products ("
        + " restaurant_id,product_name, description, price, image, categories) "
        + " values (?,?,?,?,?,?);";

    @Transactional
    @Modifying
    @Query(value = createProduct, nativeQuery = true)
    void createProduct(long restaurantId,String name, String descrpition, double price, String image, String category);
    
}
