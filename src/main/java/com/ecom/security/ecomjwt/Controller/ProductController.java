package com.ecom.security.ecomjwt.Controller;

import com.ecom.security.ecomjwt.JwtPackage.JwtUtil;
import com.ecom.security.ecomjwt.Model.*;
import com.ecom.security.ecomjwt.Service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartProductService cartProductService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private JwtUtil jwtUtil;
//
//    @GetMapping("/public/product/search")
//    public ResponseEntity<?> searchProduct(@RequestParam String keyword){
//        List<Product> products = productService.searchProduct(keyword);
//        if(products.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }else{
//            return new ResponseEntity<>(products,HttpStatus.OK);
//        }
//    }
//
//    @PostMapping("/auth/seller/product")
//    public ResponseEntity<?> addProduct(@RequestBody Product product){
//        Product productData = productService.addProduct(product);
//        String redirect = "http://localhost/api/auth/seller/product/" + productData.getProductId();
//        if(productData!=null)
//        {
//            return ResponseEntity.ok(redirect);
//        }else{
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PostMapping("/auth/consumer/cart")
//    public ResponseEntity<?> addProductToCart(@RequestBody Product product, HttpServletRequest request){
//        String jwtToken = jwtUtil.getJwtFromHeader(request);
//        User user = jwtUtil.getUserFromToken(jwtToken);
//        Optional<Cart> cartData = cartService.getDataFromCart(user.getUsername());
//        Cart cart;
//        if(cartData.isPresent()){
//            cart = cartData.get();
//        }else{
//            cart = new Cart();
//            cart.setUser(user);
//            cartService.saveCart(cart);
//        }
//        Optional<Product> productData = productService.findProductFromCartById(product);
//        Product existingProduct = productData.get();
//        CartProduct cartProduct;
//        Optional<CartProduct> cartProductData = cartProductService.findCartProductByUserAndProductId(user.getUserId(),existingProduct.getProductId());
//        if(cartProductData.isPresent()){
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        }else{
//            cartProduct = new CartProduct(cart,existingProduct,1);
//        }
//        cartProductService.addProductInCartProduct(cartProduct);
//        cartService.saveCart(cart);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
    //////////////////////////////////////////////////////////////////////
    /* CURD Opration */
    /////////////////////////////////////////////////////////////////////

    /*
    Crate Product
    role Seller
    endpoint /api/products
    http method is POST
    status 201 created or 400 Bad request
    input
    {
    "productId": 3,
    "category": {
        "categoryName": "Electronics",
        "categoryId": "2"
    },
    "price": "98000.0",
    "productName": "iPhone 12 Pro Max"
    }
    steps:
    1) check if user is authenticated
    2) check if product is already present in product table
    3) if yer return 400 else add product and set seller
     */

    @PostMapping("/auth/seller/product")
    public ResponseEntity<?> addProductInProductTable(@RequestBody Product product, HttpServletRequest request){
        //authenticate user
        String userName  = userService.authenticateUserGetUserName(request);
        User userData = userService.loadUserByUsername(userName);
        //check if product is available by catetogy name and product name
        boolean isExistedByCategoryName = categoryService.categoryPresentByName(product.getCategory().getCategoryName());
        Category categoryData = categoryService.getCategoryByCategoryName(product.getCategory().getCategoryName());
        boolean isExistedByProductName = productService.productPresentByName(product.getProductName());
        Optional<Product> productData = null;
        if(isExistedByProductName && isExistedByCategoryName){
            //product is already present
            return new ResponseEntity<>("Product already present",HttpStatus.BAD_REQUEST);
        }else{
            product.setSeller(userData);
            product.setCategory(categoryData);
            productData = productService.addProduct(product);
        }
        if(productData.isPresent()){
            return new ResponseEntity<>(productData,HttpStatus.CREATED);
        }
       else {
            return new ResponseEntity<>("Product already present",HttpStatus.BAD_REQUEST);

        }
    }

    /*GetProduct by productId
    GetProduct by productId
    Public access url /public/productId
    Http Status 200ok, 404 not found
    1) it is public
    2) check if produc is present
    3) if yes get
     */
    @GetMapping("/public/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id){
        boolean isPresentInProducts = productService.isProductPresent(id);
        if(isPresentInProducts){
            Optional<Product> product = productService.getProduct(id);
            if (product.isPresent()) {
                return new ResponseEntity<>(product.get(),HttpStatus.OK);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return null;
    }

    /*
    Update Product
    Seller
    HttpStatus 200 ok what update product, 400 incorrect details, 403 if user dont have access
    1) check user is authorized or not
    2) if yes the check if product is present
    3) if yes update details
     */
    @PutMapping("/auth/seller/product/{id}")
    public ResponseEntity<?> updateProductById(@AuthenticationPrincipal User user, @RequestBody Product product, @PathVariable int id){
        String userName = user.getUsername();
        System.out.println(userName);
        boolean isProductPresent = productService.isProductPresent(id);
        if(isProductPresent){
            if(Objects.equals(product.getProductName(), "") && product.getPrice()<=0){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }else {
                Product productData = productService.updateProduct(product, id);
                return new ResponseEntity<>(productData, HttpStatus.OK);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


        /*
        Delete product
        url auth/seller/product/{id}
        only seller can do
        1)autorize user using @AuthenticationPrincipal
        2)check product is there in database by id
        3) if yes check in procut is available in cartproduct
        4) if yes "cant delete"
        5) else delete
        The product_id in the product table is referenced as a foreign key in the cart_product table.
        Deleting a product without first removing or handling its references in cart_product violates the foreign key constraint.
         */
    //@PreAuthorize("SELLER")
    @DeleteMapping("auth/seller/product/{id}")
    public ResponseEntity<?>deleteProduct(@AuthenticationPrincipal User user, @PathVariable int id){
        System.out.println(user.getUsername());
        boolean isProductAvailable = productService.isProductPresent(id);
        int checkIsProductIsInCartProduct = cartProductService.checkifAvailableByProductId(id);
        if(isProductAvailable){
            if(checkIsProductIsInCartProduct>0){
                String problem = "Can't Delete Product user has product in Cart";
                return new ResponseEntity<>(problem,HttpStatus.BAD_REQUEST);
            }
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //////////////////////////////////////////////////////////////////////
    /* Search Opration */
    /////////////////////////////////////////////////////////////////////

    /*
    Search product by Product name
    any pne can perform Public
    Http method GET
    endpoint: /public/product
    using @RequestParam
    Http Status: 200 OK, 404 Not Found
    1)check is product is present
    2) if yes return with 200
    3)else 404
     */
    @GetMapping("/public/product")
    public ResponseEntity<?> searchProductByProductName(@RequestParam(name="productName") String productName){
        Product product = productService.searchProductByProductName(productName);
        if(product != null){
            return new ResponseEntity<>(product, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
    filter product by category name
    public /public/products/
    using @Requestparam categoryName
    Http method GET
    Http status code 200Ok, 404 Not Found
    1) check if category is present
    2)if yes filter products by product name
    3)else category not found
     */

    @GetMapping("/public/products")
    public ResponseEntity<?> filterProductByCategoryName(@RequestParam(name="categoryName") String categoryName){
        List<Product> products = productService.getProductsByCategoryName(categoryName);
        if(products.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return  new ResponseEntity<>(products,HttpStatus.OK);
        }
    }

    /*
    sort product based on price
    public
    Http method get
    end Point /public/products
    http status 200 ,404
    use jpa Sort class allows us to aplay sorting to query

     */
    @GetMapping("/public/products/filter")
    public ResponseEntity<?> filterProductByPrice(@RequestParam(value = "sort", defaultValue = "asc") String sortOrder, @RequestParam(name="fieldName") String fieldName){
        Sort sort = Sort.by(Sort.Order.asc(fieldName));
        if("desc".equalsIgnoreCase(sortOrder)){
            sort = Sort.by(Sort.Order.desc(fieldName));
        }
        List<Product>products = productService.getAllProductsWithFilter(sort);
        if(products.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(products,HttpStatus.OK);
        }
    }
    //////////////////////////////////////////////////////////////////////
    /* Add / view / decrease Stock to product Opration */
    /////////////////////////////////////////////////////////////////////
//    @PutMapping("/auth/seller/product/{id}/{stock}")
//    public ResponseEntity<?> updateStockOfProduct(@AuthenticationPrincipal User user, @PathVariable int id, @PathVariable int stock){
//        System.out.println(user.getUsername()+" has role "+user.getRoles());
//        boolean checkIfProductIsAvailable = productService.isProductPresent(id);
//        if(checkIfProductIsAvailable){
//            Product product = productService.updateStockOfProduct(id,stock);
//        }
//    }
}
