package com.ecom.security.ecomjwt.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cpId;

    @ManyToOne
    @JoinColumn(name="cart_id", referencedColumnName = "cartId")
    @JsonIgnore
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "productId")
    private Product product;

    private int quantity;

    public CartProduct(Cart cart, Product product, Integer quantity){
        super();
        this.cart=cart;
        this.product=product;
        this.quantity=quantity;
    }
}
