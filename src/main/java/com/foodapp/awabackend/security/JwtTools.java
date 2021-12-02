package com.foodapp.awabackend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

public class JwtTools {

    private static final String SECRET = System.getenv("SECRET");

    public static String createToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());

        String jwtToken = JWT.create()
            .withSubject(user.getUsername())
            .withClaim("role", user.getAuthorities().iterator().next().getAuthority())
            .sign(algorithm);

        return jwtToken;
    }

    public static UsernamePasswordAuthenticationToken validateJwt(String accessToken) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());

        JWTVerifier verifier = JWT.require(algorithm).build();

        UsernamePasswordAuthenticationToken authToken = null;

        try {
            DecodedJWT decodedJWT = verifier.verify(accessToken);
            String username = decodedJWT.getSubject();
            String role = decodedJWT.getClaim("role").asString();
            SimpleGrantedAuthority auth = new SimpleGrantedAuthority(role);
            authToken = new UsernamePasswordAuthenticationToken(username, null, List.of(auth));
            // ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
            // authorities.add(auth);
            // authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        } catch(Exception e) {}

        return authToken;
    }
}
