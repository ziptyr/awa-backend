package com.foodapp.awabackend.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @Column(name = "restaurant_id")
    public long restaurantId;
    @Column(name = "restaurant_name")
    public String restaurantName;
    @Column(name = "manager_name")
    public String managerName;
    @Column(name = "address")
    public String address;
    @Column(name = "opens")
    public String opens;
    @Column(name = "closes")
    public String closes;
    @Column(name = "image")
    public String image;
    @Column(name = "type")
    public String type;
    @Column(name = "price_level")
    public long priceLevel;
    // @OneToMany(mappedBy = "restaurant")
    // public Set<Product> menu;

    public Restaurant(){}

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpens() {
        return opens;
    }

    public void setOpens(String opens) {
        this.opens = opens;
    }

    public String getCloses() {
        return closes;
    }

    public void setCloses(String closes) {
        this.closes = closes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(long priceLevel) {
        this.priceLevel = priceLevel;
    }
    
    
}
