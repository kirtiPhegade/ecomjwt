package com.ecom.security.ecomjwt.Repository;

import com.ecom.security.ecomjwt.Model.CartProduct;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct,Integer> {
    int countByProduct_ProductId(int productId);

//    Optional<CartProduct> findByCartUserUserIdAndProductProductId(int userId, int productId);
//
//    Optional<CartProduct> findByCartCartIdAndProductProductId(int cartId, int productId);
//
//    void deleteBy(CartProduct cartProduct);
}
