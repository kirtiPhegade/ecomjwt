package com.ecom.security.ecomjwt.Service;

import com.ecom.security.ecomjwt.Model.Product;
import com.ecom.security.ecomjwt.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;

    public boolean productPresentByName(String productName) {
        return productRepository.existsByProductName(productName);
    }

    public Optional<Product> addProduct(Product product) {
        return Optional.of(productRepository.save(product));
    }

    public boolean isProductPresent(int id) {
        return productRepository.existsByProductId(id);
    }

    public Optional<Product> getProduct(int id) {
        return productRepository.findById(id);
    }

    public Product updateProduct(Product product, int id) {
        Product productData = getProduct(id).get();
        productData.setProductName(product.getProductName());
        productData.setPrice(product.getPrice());
        return productRepository.save(productData);
    }

    public void deleteProduct(int id) {
        Product productData = getProduct(id).get();
        productRepository.delete(productData);
    }

    public Product searchProductByProductName(String productName) {
        Product product = null;
        if(productPresentByName(productName)){
            product = getProductByName(productName);
            return product;
        }else{
            return null;
        }
    }

    public Product getProductByName(String productName){
        return productRepository.findByProductName(productName);
    }

    public List<Product> getProductsByCategoryName(String categoryName) {
        int categoryId = categoryService.getIdOfCategory(categoryName);
        return productRepository.findByCategory_CategoryId(categoryId);
    }

    public List<Product> getAllProductsWithFilter(Sort sort) {
        return productRepository.findAll(sort);
    }

    public List<Product> getProductsByCategoryId(int id) {
        return productRepository.findByCategory_CategoryId(id);
    }

//
//    public List<Product> searchProduct(String keyword) {
//        return productRepository.findByProductNameContainingIgnoreCaseOrCategoryCategoryNameContainingIgnoreCase(keyword, keyword);
//    }
//
//    public Product addProduct(Product product) {
//        return productRepository.save(product);
//    }
//
//    public boolean findProductFromCart(Product product, String username) {
//        List<Product> productData = productRepository.findByProductNameContainingIgnoreCaseOrCategoryCategoryNameContainingIgnoreCase(product.getProductName(), product.getCategory().getCategoryName());
//        if (productData.isEmpty()) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public Optional<Product> findProductFromCartById(Product product) {
//        return productRepository.findById(product.getProductId());
//    }
}
