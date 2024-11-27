package com.ecom.security.ecomjwt.Controller;

import com.ecom.security.ecomjwt.Model.Product;
import com.ecom.security.ecomjwt.Model.User;
import com.ecom.security.ecomjwt.Service.CategoryService;
import com.ecom.security.ecomjwt.Service.ProductService;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    //////////////////////////////////////////////////////////////////////
    //Operation for consumers
    ///////////////////////////////////////////////////////////////////////////////
    /*
    Find All Products by Category category Id
    end points auth/consumer/category/{categoryId}
    HttpMethod GET
    Http Status: 200Ok,404NotFound
     */
    @GetMapping("/auth/consumer/categoty/{id}/products")
    public ResponseEntity<?> getProductsByCategory(@AuthenticationPrincipal User user, @PathVariable int id){
        System.out.println(user.getUsername()+"has role"+user.getRoles());
        boolean checkIfCategoryIsPresent = categoryService.categoryPresentById(id);
        List<Product> productList;
        if(checkIfCategoryIsPresent){
            productList = productService.getProductsByCategoryId(id);
            if(productList.isEmpty()){
                return new ResponseEntity<>("No products in this category", HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(productList,HttpStatus.OK);
            }
        }else{
            return new ResponseEntity<>("No category found", HttpStatus.NOT_FOUND);
        }
    }
}
