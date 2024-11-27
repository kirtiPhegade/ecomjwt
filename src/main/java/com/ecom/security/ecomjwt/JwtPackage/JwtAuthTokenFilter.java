package com.ecom.security.ecomjwt.JwtPackage;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
/*
@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = parseJwtToken(request);
            if(jwt != null && jwtUtil.validateToken(jwt)){
                String username = jwtUtil.getUserNameFromJwtToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                        null,
                        userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (Exception e){
            logger.error("Error occurred while processing the JWT token: " + e.getMessage(), e);
        }
        filterChain.doFilter(request,response);
    }

    private String parseJwtToken(HttpServletRequest request) {
        String jwt = jwtUtil.getJwtFromHeader(request);
        return jwt;
    }
}
*/
@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwtToken(request);
            if (jwt != null && jwtUtil.validateToken(jwt)) {
                String username = jwtUtil.getUserNameFromJwtToken(jwt);
                logger.info("Token validated successfully, username: " + username);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                        null,
                        userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                logger.warn("JWT Token is invalid or missing.");
            }
        } catch (Exception e) {
            logger.error("Error occurred while processing the JWT token: " + e.getMessage(), e);
        }

        filterChain.doFilter(request, response);  // Proceed with the request
    }

    private String parseJwtToken(HttpServletRequest request) {
        String jwt = jwtUtil.getJwtFromHeader(request);
        if (jwt == null) {
            logger.warn("JWT Token is missing in the request.");
        }
        return jwt;
    }
}
