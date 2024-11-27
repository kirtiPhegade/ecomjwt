package com.ecom.security.ecomjwt.Service;

import com.ecom.security.ecomjwt.JwtPackage.JwtUtil;
import com.ecom.security.ecomjwt.Model.User;
import com.ecom.security.ecomjwt.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            return user.get();
        }else{
            throw new UsernameNotFoundException("User ID not found");
        }
    }

    public User authenticateUserGetUserData(HttpServletRequest request) {
        String token = jwtUtil.getJwtFromHeader(request);
        User user  = jwtUtil.getUserFromToken(token);
        return user;
    }

    public String authenticateUserGetUserName(HttpServletRequest request){
        String token = jwtUtil.getJwtFromHeader(request);
        String userName = jwtUtil.getUserNameFromJwtToken(token);
        return userName;
    }
}
