package com.foodapp.awabackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.foodapp.awabackend.data.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long>{
    final String getOrderById = "select * from orders where order_id = ?;";
    final String getOrdersByUsername = "SELECT * FROM orders where user_name = ?;";

    @Query(value = getOrderById, nativeQuery = true)
    public Order getOrderById(long order_id);

    @Query(value = getOrdersByUsername, nativeQuery = true)
    public List<Order> getOrdersByUsername(String username);



}