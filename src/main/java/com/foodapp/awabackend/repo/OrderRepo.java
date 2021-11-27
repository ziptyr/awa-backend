package com.foodapp.awabackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.foodapp.awabackend.data.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long>{
    final String getOrderById = "select * from orders where order_id = ?;";
    final String getOrderProducts = "SELECT product_name, op.amount, op.product_price, op.amount * op.product_price as total_price "
     + " FROM orders_products op"
     + " JOIN products p on (op.product_id = p.product_id) where op.order_id = 1;";

    @Query(value = getOrderById, nativeQuery = true)
    public Object[] getOrderById(long order_id);

    @Query(value = getOrderProducts, nativeQuery = true)
    public List<Object[]> getOrderProducts(long order_id);



}