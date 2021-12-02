package com.foodapp.awabackend.repo;

import com.foodapp.awabackend.data.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Account, String>{
    public Account findByUserName(String userName);
}