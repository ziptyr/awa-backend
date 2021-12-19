package com.foodapp.awabackend.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "restaurants")
public class Restaurant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private long restaurantId;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "manager_name")
    private String managerName;

    @Column(name = "restaurantAddress")
    private String restaurantAddress;

    @Column(name = "opens")
    private String opens;

    @Column(name = "closes")
    private String closes;

    @Column(name = "image")
    private String image;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "price_level")
    private long priceLevel;

    // @OneToMany(mappedBy = "restaurant")
    // private Set<Product> menu;

    Restaurant() {}

    public Restaurant(
        String restaurantName,
        String managerName,
        String restaurantAddress,
        String opens,
        String closes,
        String image,
        Type type,
        String priceLevel
    ) {
        this.restaurantName = restaurantName;
        this.managerName = managerName;
        this.restaurantAddress = restaurantAddress;
        this.opens = opens;
        this.closes = closes;
        this.image = image;
        this.type = type;
        this.priceLevel = Long.parseLong(priceLevel);
    }

    public long getRestaurantId() {
        return restaurantId;
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

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public long getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(long priceLevel) {
        this.priceLevel = priceLevel;
    }
}
