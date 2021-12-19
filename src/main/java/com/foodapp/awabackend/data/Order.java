package com.foodapp.awabackend.data;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "orders")
public class Order implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long orderId;

    @Column(name = "restaurant_id")
    private long restaurantId;

    @Column(name = "user_name")
    private String username;

    @Column(name = "order_status")
    private int orderStatus;

    @Column(name = "order_date")
    private String orderDate;

    @Column(name = "total")
    private double total;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "eta")
    private String eta;

    Order() {}

    public long getOrderId() {
        return orderId;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public String getUsername() {
        return username;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }
}
