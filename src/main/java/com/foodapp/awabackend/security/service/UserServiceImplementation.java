package com.foodapp.awabackend.security.service;

import java.util.ArrayList;
import java.util.Collection;

import com.foodapp.awabackend.data.Account;
import com.foodapp.awabackend.data.Role;
import com.foodapp.awabackend.repo.AccountRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserDetailsService {

    @Autowired
    AccountRepo accountRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepo.findByUserName(username);

        if (account == null) {
            throw new UsernameNotFoundException("User not in database");
        } else {
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(this.getRole(account).toString()));
            return new User(username, account.getPassword(), authorities);
        }
    }

    private Role getRole(Account account) {
        if (account.isManager()) return Role.MANAGER;
        else return Role.CUSTOMER;
    }
}
