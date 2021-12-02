package com.foodapp.awabackend.repo;

import com.foodapp.awabackend.data.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, String>{
    
}