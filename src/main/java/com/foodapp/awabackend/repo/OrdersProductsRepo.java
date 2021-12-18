package com.foodapp.awabackend.repo;

import java.util.List;

import com.foodapp.awabackend.data.OrdersProducts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersProductsRepo extends JpaRepository<OrdersProducts, Long> {

    public List<OrdersProducts> findByOrderId(long orderId);
}
