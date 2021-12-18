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
    private String userName;
    @Column(name="address")
    private String address;
    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private Role role;
    //@Column(name="manager")
    //private boolean manager;
    // @JsonIgnore
    @Column(name="password_hash")
    private String password;

    public Account() {}

    //public Account(String userName, String address, boolean manager, String passwordHash) {
    //    this.userName = userName;
    //    this.address = address;
    //    this.manager = manager;
    //    BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
    //    this.passwordHash = pwEncoder.encode(this.passwordHash);
    //}

    public void encodePassword() {
        BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        this.password = pwEncoder.encode(this.password);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    //public boolean isManager() {
    //    return manager;
    //}

    //public void setManager(boolean manager) {
    //    this.manager = manager;
    //}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
