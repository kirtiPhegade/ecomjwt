package com.ecom.security.ecomjwt.Repository;

import com.ecom.security.ecomjwt.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    boolean existsByCategoryName(String categoryName);

    Category findByCategoryName(String categoryName);

    boolean existsByCategoryId(int id);
}
