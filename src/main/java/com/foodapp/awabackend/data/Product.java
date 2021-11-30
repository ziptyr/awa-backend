package com.foodapp.awabackend.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue
    public long productId;
    @Column(name = "restaurant_id")
    public long restaurantId;
    @Column(name = "product_name")
    public String name;
    @Column(name = "description")
    public String description;
    @Column(name = "price")
    public double price;
    @Column(name = "image")
    public String image;
    @Column(name = "categories")
    public String category;

    // public Product(){}

 
    
}
