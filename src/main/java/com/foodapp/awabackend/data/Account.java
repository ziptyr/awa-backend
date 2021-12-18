package com.foodapp.awabackend.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name="users")
public class Account {

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

    public Account() {}

    public void encodePassword() {
        BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        this.password = pwEncoder.encode(this.password);
    }

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
