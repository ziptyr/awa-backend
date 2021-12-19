package com.foodapp.awabackend.data;


import java.io.Serializable;
import java.time.LocalDate;

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
    private LocalDate orderDate;

    @Column(name = "total")
    private double total;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "eta")
    private String eta;

    Order() {}

    //public Order(long restaurantId, int orderStatus, double total) {
    //    this.restaurantId = restaurantId;
    //    this.orderStatus = orderStatus;
    //    this.total = total;
    //    this.deliveryAddress = "";
    //    this.orderDate = LocalDate.now();
    //    this.eta = "0:00";
    //}

    //public Order(long restaurantId, int orderStatus, double total, String deliveryAdString) {
    //    this(restaurantId, orderStatus, total);
    //    this.setDeliveryAddress(deliveryAddress);
    //}

    @Override
    public String toString() {
        return "Order: ("
            + this.orderId
            + " " + this.restaurantId
            + " " + this.username
            + " " + this.orderStatus
            + " " + this.orderDate
            + " " + this.total
            + " " + this.deliveryAddress
            + " " + this.eta
            + ")";
    }

    public long getOrderId() {
        return orderId;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
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
