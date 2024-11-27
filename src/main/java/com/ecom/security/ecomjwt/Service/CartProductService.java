package com.ecom.security.ecomjwt.Service;

import com.ecom.security.ecomjwt.Model.CartProduct;
import com.ecom.security.ecomjwt.Repository.CartProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartProductService {
    @Autowired
    private CartProductRepository cartProductRepository;

    public int checkifAvailableByProductId(int id) {
        return cartProductRepository.countByProduct_ProductId(id);
    }
//
//    public Optional<CartProduct> findCartProductByUserAndProductId(int userId, int productId) {
//        return cartProductRepository.findByCartUserUserIdAndProductProductId(userId,productId);
//    }
//
//
//    public void addProductInCartProduct(CartProduct cartProduct) {
//        cartProductRepository.save(cartProduct);
//    }
//
//    public Optional<CartProduct> checkifProductIsPresent(int cartId, int productId) {
//        return cartProductRepository.findByCartCartIdAndProductProductId(cartId,productId);
//    }
//
//    public void deleteCartProduct(CartProduct cartProduct) {
//        cartProductRepository.deleteBy(cartProduct);
//    }
}
