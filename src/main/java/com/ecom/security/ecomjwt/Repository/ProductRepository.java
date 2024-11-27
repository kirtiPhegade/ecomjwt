package com.ecom.security.ecomjwt.Repository;

import com.ecom.security.ecomjwt.Model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    boolean existsByProductName(String productName);

    boolean existsByProductId(int id);

    Product findByProductName(String productName);

    List<Product> findByCategory_CategoryId(int categoryId);

    List<Product> findAll(Sort sort);

    //Product findByProductId(int );

    //List<Product> findByProductNameContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(String productName, String categoryName);

//    List<Product> findByProductNameContainingIgnoreCaseOrCategoryCategoryNameContainingIgnoreCase(String productName, String categoryName);
}
