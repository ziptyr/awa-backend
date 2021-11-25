package com.foodapp.awabackend.entities;

public abstract class Account {

    private String userName;

    public Account(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
