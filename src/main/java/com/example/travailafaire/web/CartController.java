package com.example.travailafaire.web;

import com.example.travailafaire.DAO.entities.Cart;
import com.example.travailafaire.service.CartManager;
import com.example.travailafaire.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {

    @Autowired

    private CartManager cartManager;
    @Autowired

    private UserManager userManager;

    @GetMapping("/cart")
    public String viewCart(Model model, @RequestParam(name = "userId") Long userId) {
        Cart cart = cartManager.getCartByUserId(userId);
        model.addAttribute("cart", cart);
        model.addAttribute("userId", userId); // Add userId to the model
        System.out.println("here====================");

        return "Cart"; // Return the view containing the cart link
    }
    @PostMapping("/cart/add")
    public String addToCart(Model model,
                            @RequestParam(name = "userId") Long userId,
                            @RequestParam(name = "productId") int productId,
                            @RequestParam(name = "quantity", defaultValue = "1") int quantity) {
        cartManager.addToCart(userId, productId, quantity);
        Cart cart = cartManager.getCartByUserId(userId);
        model.addAttribute("cart", cart);
        model.addAttribute("userId", userId); // Add userId to the model
        return "Cart" ;// Return the Cart.html view
    }



//    @PostMapping("/cart/remove")
//    public String removeFromCart(@RequestParam(name = "userId") Long userId,
//                                 @RequestParam(name = "productId") int productId) {
//        cartManager.removeFromCart(userId, productId);
//        return "redirect:/cart?userId=" + userId;
//    }
//
//    @PostMapping("/cart/clear")
//    public String clearCart(@RequestParam(name = "userId") Long userId) {
//        cartManager.clearCart(userId);
//        return "redirect:/cart?userId=" + userId;
//    }

}
