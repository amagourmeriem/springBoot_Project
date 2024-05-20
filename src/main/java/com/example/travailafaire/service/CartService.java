package com.example.travailafaire.service;

import com.example.travailafaire.DAO.entities.Cart;
import com.example.travailafaire.DAO.entities.Product;
import com.example.travailafaire.DAO.repositories.CartRepository;
import com.example.travailafaire.DAO.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;


@Service
public class CartService implements CartManager {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void addToCart(Long userId, int productId, int quantity) {
        Cart cart = cartRepository.getCartByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            System.out.println("Cart is null. Creating new cart for user: " + userId);
            System.out.println("message2===================");

        }
        System.out.println("message1===================");

        // Retrieve the product from the repository
        Product product = productRepository.findById((long) productId).orElse(null);
        if (product != null) {
            // Add the product to the cart using the Cart entity method
            cart.addToCart(product, quantity);
            // Update the cart
            cartRepository.save(cart);

        } else {
            System.out.println("Product not found for ID: " + productId);
            System.out.println("message3===================");

        }
    }



    @Override
    public void removeFromCart(Long userId, int productId) {
        Cart cart = cartRepository.getCartByUserId(userId);
        if (cart != null) {
            Collection<Product> products = cart.getProducts();
            products.removeIf(product -> product.getId() == productId);
            cartRepository.save(cart);
        }
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = cartRepository.getCartByUserId(userId);
        if (cart != null) {
            cart.getProducts().clear();
            cartRepository.save(cart);
        }
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.getCartByUserId(userId);
    }


    public BigDecimal calculateCartTotal(Long userId) {
        Cart cart = cartRepository.getCartByUserId(userId);
        BigDecimal total = BigDecimal.ZERO;
        if (cart != null) {
            Collection<Product> products = cart.getProducts();
            for (Product product : products) {
                total = total.add(BigDecimal.valueOf(product.getPrice()));
            }
        }
        return total;
    }

}
