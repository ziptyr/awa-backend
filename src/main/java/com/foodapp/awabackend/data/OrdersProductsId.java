package com.foodapp.awabackend.data;

import java.io.Serializable;

import javax.persistence.Column;

public class OrdersProductsId implements Serializable {

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

    OrdersProductsId() {}

    public Long getOrderId() {
        return orderId;
    }

    public Long getProductId() {
        return productId;
    }
}