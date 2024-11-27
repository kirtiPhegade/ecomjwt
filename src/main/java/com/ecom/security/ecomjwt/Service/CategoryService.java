package com.ecom.security.ecomjwt.Service;

import com.ecom.security.ecomjwt.Model.Category;
import com.ecom.security.ecomjwt.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public boolean categoryPresentByName(String categoryName) {
        return categoryRepository.existsByCategoryName(categoryName);
    }

    public Category getCategoryByCategoryName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    public int getIdOfCategory(String categoryName) {
        Category category = getCategoryByCategoryName(categoryName);
        return category.getCategoryId();
    }

    public boolean categoryPresentById(int id) {
        return categoryRepository.existsByCategoryId(id);
    }
}
