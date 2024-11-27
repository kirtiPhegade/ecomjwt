package com.ecom.security.ecomjwt.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int productId;

    private String productName;

    private double price;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="seller_id", referencedColumnName = "userId", updatable = false)
    @JsonIgnore
    private User seller;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    private Category category;

}
