package com.foodapp.awabackend.security.conf;

import java.util.Arrays;

import com.foodapp.awabackend.data.Role;
import com.foodapp.awabackend.security.encoder.MyPasswordEncoder;
import com.foodapp.awabackend.security.service.UserServiceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
        http.cors();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers("/public/**").permitAll();
        http.authorizeRequests()
            .antMatchers("/manager/**").hasAnyAuthority(Role.MANAGER.toString());
        http.authorizeRequests()
            .antMatchers("/customer/**").hasAnyAuthority(Role.CUSTOMER.toString());
    
        http.authorizeRequests().anyRequest().authenticated();

        UsernamePasswordAuthenticationFilter myAuthFilter = new MyAuthenticationFilter();
        myAuthFilter.setAuthenticationManager(this.authenticationManager());
        http.addFilter(myAuthFilter);

        http.addFilterBefore(
            new MyAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));
        configuration.setAllowedHeaders(Arrays.asList(
            "authorization", "content-type", "x-auth-token"
        ));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
