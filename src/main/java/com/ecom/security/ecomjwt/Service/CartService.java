package com.ecom.security.ecomjwt.Service;

import com.ecom.security.ecomjwt.Model.Cart;
import com.ecom.security.ecomjwt.Model.Product;
import com.ecom.security.ecomjwt.Repository.CartProductRepository;
import com.ecom.security.ecomjwt.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartProductRepository cartProductRepository;

//
//    public Optional<Cart> getCartData(String username) {
//        return cartRepository.findByUserUsername(username);
//    }
//
//
//    public Optional<Cart> getDataFromCart(String username) {
//        return cartRepository.findByUserUsername(username);
//    }
//
//    public void saveCart(Cart cart) {
//        cartRepository.save(cart);
//    }
}
