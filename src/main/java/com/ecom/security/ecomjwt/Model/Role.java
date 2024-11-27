package com.ecom.security.ecomjwt.Model;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import lombok.Data;
//
//@Entity
//@Data
//public class Role {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String role;
//}

import org.springframework.security.core.GrantedAuthority;

public enum Role {
    CONSUMER, SELLER;
}

class RoleGrantedAuthority implements GrantedAuthority {
    String role;

    public RoleGrantedAuthority(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return this.role;
    }
}