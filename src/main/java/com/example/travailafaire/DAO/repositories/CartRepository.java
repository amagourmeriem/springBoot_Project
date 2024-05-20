package com.example.travailafaire.DAO.repositories;

import com.example.travailafaire.DAO.entities.Cart;
import com.example.travailafaire.DAO.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart getCartByUserId(Long userId);

//    Cart findByUserId(Long userId);
//    void setUserId(Cart cart, Long userId);
//    List<Product> getProducts(Cart cart);
////    Cart getCartByUserId(Long userId);
//    int countByUserId(Long userId);
//    void setProductQuantity(Cart cart, Product product, int quantity);


}
