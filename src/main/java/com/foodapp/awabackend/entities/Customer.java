package com.foodapp.awabackend.entities;

public class Consumer extends Account {

    private String[] address;

    public Customer(String userName, String[] address) {
        super(userName);
        this.address = address;
    }

    public String[] getAddress() {
        return address;
    }

    public void setAddress(String[] address) {
        this.address = address;
    }
}
