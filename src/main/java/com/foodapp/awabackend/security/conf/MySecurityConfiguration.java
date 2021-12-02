package com.foodapp.awabackend.security.conf;

import com.foodapp.awabackend.data.Role;
import com.foodapp.awabackend.security.encoder.MyPasswordEncoder;
import com.foodapp.awabackend.security.service.UserServiceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class MySecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    MyPasswordEncoder pwEncoder;

    @Autowired
    // TODO: ask which one to use
    //UserDetailsService udService;
    UserServiceImplementation udService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(udService).passwordEncoder(pwEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests().antMatchers("/public/**").permitAll();
        http.authorizeHttpRequests()
            .antMatchers("/manager/**").hasAnyAuthority(Role.MANAGER.toString());
        http.authorizeHttpRequests()
            .antMatchers("/customer/**").hasAnyAuthority(Role.CUSTOMER.toString());
    
        http.authorizeHttpRequests().anyRequest().authenticated();

        UsernamePasswordAuthenticationFilter myAuthFilter = new MyAuthenticationFilter();
        myAuthFilter.setAuthenticationManager(this.authenticationManager());
        http.addFilter(myAuthFilter);

        http.addFilterBefore(
            new MyAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
