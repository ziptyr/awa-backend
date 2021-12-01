package com.foodapp.awabackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import com.foodapp.awabackend.data.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long>{
    final String getOrderById = "select * from orders where order_id = ?;";
    final String getOrdersByUsername = "SELECT * FROM orders where user_name = ? order by order_date desc;";
    final String getNewOrdersByRestaurantId = "select * from orders where restaurant_id = ? and order_status = 0;";
    final String getOrdersByRestaurantId = "select * from orders where restaurant_id = ? order by order_date desc;";
    final String updateOrderStatus = "update orders set order_status = ? where order_id = ?";

    @Query(value = getOrderById, nativeQuery = true)
    public Order getOrderById(long order_id);

    @Query(value = getOrdersByUsername, nativeQuery = true)
    public List<Order> getOrdersByUsername(String username);

    @Query(value = getNewOrdersByRestaurantId, nativeQuery = true)
    public List<Order> getNewOrdersByRestaurantId(long id);

    @Query(value = getOrdersByRestaurantId, nativeQuery = true)
    public List<Order> getOrdersByRestaurantId(long id);


    @Modifying
    @Transactional
    @Query(value = updateOrderStatus, nativeQuery = true)
    public void updateOrderStatus(int status, long id);



    /*
    These Queries/Statements are needed to place an order
    */

    final String getNextPrimaryKey = "select nextval('order_pk_seq');";
    final String createOrder = "insert into orders values (?,?,?,0,current_date,0.0,"
        + " (select address from users where user_name = ?))";
    final String insertOrderProductRelation = "insert into orders_products values("
        + " ?, ?, ?, (select price from products where product_id = ?))";
    final String completeOrderPlacement = "update orders set total = (" 
        + "select sum(amount * product_price) from orders_products"
        + " where order_id = ?) where order_id = ?";
    final String updateDeliveryAddress = "update orders set delivery_address = ?"
        + " where order_id = ?";

    @Query(value = getNextPrimaryKey, nativeQuery = true)
    public int getNextPrimaryKey();

    @Modifying
    @Transactional
    @Query(value = createOrder, nativeQuery = true)
    public void createOrder(long pk, long restaurant_id, String username, String usernameAgain);

    @Modifying
    @Transactional
    @Query(value = insertOrderProductRelation, nativeQuery = true)
    public void insertOrderProductRelation(long orderId, long productId, int amount, long prodId2 );

    @Modifying
    @Transactional
    @Query(value = completeOrderPlacement, nativeQuery = true)
    public void completeOrderPlacement(long orderId, long orderIdRepeated);

    @Modifying
    @Transactional
    @Query(value = updateDeliveryAddress, nativeQuery = true)
    public void updateDeliveryAddress(String addr, long orderId);

}