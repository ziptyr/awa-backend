package com.foodapp.awabackend.data;

import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "orders_products")
public class Order {
    
    @Id
    @Column(name = "order_id")
    public long orderId;
    // @OneToMany
    // // public Set<Map<Product, Integer>> products;
    // public Set<Product> products;

    public Order(){}
}
