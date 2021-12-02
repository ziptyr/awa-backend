package com.foodapp.awabackend.security.conf;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodapp.awabackend.security.JwtTools;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String[] cred = getBasicCredentials(request);

        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(cred[0], cred[1]);
        
        return this.getAuthenticationManager().authenticate(authToken);
    }

    private String[] getBasicCredentials(HttpServletRequest request)
            throws AuthenticationException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Basic")) {
            throw new BadCredentialsException("Login failed");
        }

        String cred = header.substring("Basic".length() + 1);
        cred = new String(Base64.getDecoder().decode(cred));

        String[] basicCred = cred.split(":");

        return basicCred;
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response,
            FilterChain cahin, Authentication authResult) throws IOException, ServletException {

        String jwtToken = JwtTools.createToken((User)authResult.getPrincipal());

        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("access_token", jwtToken);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(response.getOutputStream(), jsonBody);
    }
}
