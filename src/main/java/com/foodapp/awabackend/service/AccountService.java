package com.foodapp.awabackend.service;

import java.util.List;

import com.foodapp.awabackend.data.Account;
import com.foodapp.awabackend.data.Role;
import com.foodapp.awabackend.repo.AccountRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountRepo accountRepo;

    public Account findByUsername(String username) {
        return accountRepo.findByUsername(username);
    }

    public List<Account> findUsersByRole(Role role) {
        return accountRepo.findUsersByRole(role);
    }

    public void save(Account account) {
        accountRepo.save(account);
    }
}
