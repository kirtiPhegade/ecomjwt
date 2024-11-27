package com.ecom.security.ecomjwt.JwtPackage;

import com.ecom.security.ecomjwt.Model.User;
import com.ecom.security.ecomjwt.Repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.security.Key;
import java.security.SignatureException;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${spring.app.jwtSecret}")
    private String secretKey;
    @Autowired
    private UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    public String generateToken(String username){
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))//1 hr
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }

    //get jwe from postman header where name is authorization and bearer token start with bearer
    //not need in wings 1
    public String getJwtFromHeader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);//remove Bearer prefix
        }
        return null;
    }

    //get token from username
    public String generateTokenFromUsername(UserDetails userDetails){
        String username = userDetails.getUsername();
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }

    //get username from token
    public String getUserNameFromJwtToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    //validating jwt token
    public boolean validateToken(String token){
        log.debug(token);
        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
            // Parse the JWT and check if it is expired or has an invalid signature
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("Token has expired", e);
        } catch (JwtException e){
            return false;
        }
    }

    //get User From token
    // Get User object from JWT token
    public User getUserFromToken(String token) {
        try {
            String username = getUserNameFromJwtToken(token); // Get username from token
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching user from token: " + e.getMessage(), e);
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
