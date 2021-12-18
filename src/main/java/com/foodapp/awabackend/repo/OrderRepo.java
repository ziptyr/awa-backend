package com.foodapp.awabackend.repo;

import com.foodapp.awabackend.data.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    public Order findById(long orderId);
}
