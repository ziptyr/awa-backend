package com.foodapp.awabackend.data;

import java.io.Serializable;

import javax.persistence.Column;

public class OrderProductRelationId implements Serializable {

    @Column(name = "order_id")
    public Long orderId;

    @Column(name = "product_id")
    public Long productId;

    public OrderProductRelationId() {}

    public OrderProductRelationId(long orderId, long productId) {
        this.orderId = orderId;
        this.productId = productId;
    }
}