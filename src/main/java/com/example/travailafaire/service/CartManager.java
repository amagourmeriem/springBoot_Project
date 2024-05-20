package com.example.travailafaire.service;

import com.example.travailafaire.DAO.entities.Cart;
import com.example.travailafaire.DAO.entities.User;

import java.math.BigDecimal;

public interface CartManager {
    void addToCart(Long userId, int productId, int quantity);
    void removeFromCart(Long userId, int productId);
    void clearCart(Long userId);
    Cart getCartByUserId(Long userId);
    BigDecimal calculateCartTotal(Long userId);
}
