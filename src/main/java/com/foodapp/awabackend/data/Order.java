package com.foodapp.awabackend.data;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "orders")
public class Order {
    
    @Id
    @Column(name = "order_id")
    public long orderId;
    @Column(name = "restaurant_id")
    public long restaurantId;
    @Column(name = "user_name")
    public String username;
    @Column(name = "order_status")
    public int orderStatus;
    @Column(name = "order_date")
    public String orderDate;
    @Column(name = "total")
    public double total;
    @Column(name = "delivery_address")
    public String deliveryAddress;
    

    public Order(){}
}
