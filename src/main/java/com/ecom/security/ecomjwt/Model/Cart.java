package com.ecom.security.ecomjwt.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;

    private double totalAmount;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cart")
    private List<CartProduct> cartProduct;
}
