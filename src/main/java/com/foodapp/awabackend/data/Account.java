package com.foodapp.awabackend.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class Account implements Serializable {

    @Id
    @Column(name="user_name")
    private String username;

    @Column(name="address")
    private String address;

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name="password_hash")
    private String password;

    Account() {}

    public Account(String username, String address, Role role, String password) {
        this.username = username;
        this.address = address;
        this.role = role;
        this.password = password;
    }

    //public void encodePassword() {
    //    BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
    //    this.password = pwEncoder.encode(this.password);
    //}

    public String getUsername() {
        return username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
