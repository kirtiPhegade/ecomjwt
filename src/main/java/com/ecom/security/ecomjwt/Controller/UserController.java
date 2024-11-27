package com.ecom.security.ecomjwt.Controller;

import com.ecom.security.ecomjwt.JwtPackage.JwtUtil;
import com.ecom.security.ecomjwt.Model.User;
import com.ecom.security.ecomjwt.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;

    //add a user
    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody User user){
        User userData = userService.addUser(user);
        if(userData != null){
            return new ResponseEntity<>(userData, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Invalid",HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("public/login")
    public ResponseEntity<String> login (@RequestBody User requesterBody){
        User user = userService.loadUserByUsername(requesterBody.getUsername());
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requesterBody.getUsername(),requesterBody.getPassword()));
        return ResponseEntity.ok(jwtUtil.generateToken(user.getUsername()));
    }
}
