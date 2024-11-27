package com.ecom.security.ecomjwt.Controller;

import com.ecom.security.ecomjwt.JwtPackage.JwtUtil;
import com.ecom.security.ecomjwt.Model.Cart;
import com.ecom.security.ecomjwt.Model.User;
import com.ecom.security.ecomjwt.Model.CartProduct;
import com.ecom.security.ecomjwt.Model.Product;
import com.ecom.security.ecomjwt.Service.CartProductService;
import com.ecom.security.ecomjwt.Service.CartService;
import com.ecom.security.ecomjwt.Service.ProductService;
import jakarta.persistence.GeneratedValue;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class CartController
{
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

    @Autowired
    private CartProductService cartProductService;
    @Autowired
    private JwtUtil jwtUtil;

//    //api/auth/consumer/cart - GET - returns the consumer’s cart
//    @GetMapping("/consumer/cart")
//    public ResponseEntity<?> getCart(HttpServletRequest request){
//        String jwtToken = jwtUtil.getJwtFromHeader(request);
//        System.out.println("comming");
//        String username = jwtUtil.getUserNameFromJwtToken(jwtToken);
//        Optional<Cart> cartData = cartService.getCartData(username);
//        if(cartData.isPresent()){
//            return new ResponseEntity<>(cartData, HttpStatus.OK);
//        }else{
//            return new ResponseEntity<>("Data not found", HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PutMapping("/consumer/cart")
//    public ResponseEntity<?> updateProductInCart(@RequestBody CartProduct cartProduct, HttpServletRequest request){
//        String jwtToken = jwtUtil.getJwtFromHeader(request);
//        User user = jwtUtil.getUserFromToken(jwtToken);
//        Optional<Cart> cartData = cartService.getCartData(user.getUsername());
//        Cart cart;
//        if(cartData.isPresent()){
//            cart= cartData.get();
//        }else{
//            cart = new Cart();
//            cart.setUser(user);
//            cartService.saveCart(cart);
//        }
//        Optional<CartProduct> cartProductData = cartProductService.checkifProductIsPresent(cart.getCartId(),cartProduct.getProduct().getProductId());
//        CartProduct cp = null;
//        if(cartProductData.isPresent()){
//            cp = cartProductData.get();
//            if(cp.getProduct().getProductId() == cartProduct.getProduct().getProductId()){
//                if(cp.getQuantity() <= 0){
//                    cartProductService.deleteCartProduct(cartProduct);
//                }else{
//                    cp.setQuantity(cartProduct.getQuantity());
//                }
//            }
//        }else{
//            cartProductService.addProductInCartProduct(cartProduct);
//        }
//
//
//    }


}
