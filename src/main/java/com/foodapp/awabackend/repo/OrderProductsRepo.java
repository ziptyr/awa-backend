package com.foodapp.awabackend.repo;

import java.util.List;

import com.foodapp.awabackend.data.OrderProductRelation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductsRepo extends JpaRepository<OrderProductRelation, Long>{
    final String getOrderProducts = "SELECT * FROM orders_products where order_id = ?;";

    @Query(value = getOrderProducts, nativeQuery = true)
    public List<OrderProductRelation> getOrderProducts(long order_id);
}