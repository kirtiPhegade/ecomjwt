package com.ecom.security.ecomjwt.Repository;

import com.ecom.security.ecomjwt.Model.Cart;
import com.ecom.security.ecomjwt.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
//    Optional<Cart> findByUserUsername(String username);

}
