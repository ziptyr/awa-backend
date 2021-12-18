package com.foodapp.awabackend.repo;

import java.util.List;

import com.foodapp.awabackend.data.Account;
import com.foodapp.awabackend.data.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Account, String>{

    public Account findByUserName(String userName);
    public List<Account> findUsersByRole(Role role);
}