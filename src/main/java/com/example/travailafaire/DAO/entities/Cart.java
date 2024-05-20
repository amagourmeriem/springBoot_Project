package com.example.travailafaire.DAO.entities;

import com.example.travailafaire.DAO.entities.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Long userId;
    private int quantity;
    private float subtotal;
    private float shipping;
    private float total;

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY)
    private Collection<Product> products;


    @CollectionTable(name = "cart_contents", joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    @ElementCollection
    private Map<Product, Integer> cartContents = new HashMap<>();

    // Method to add products to the cart
    public void addToCart(Product product, int quantity) {
        if (cartContents.containsKey(product)) {
            int currentQuantity = cartContents.get(product);
            cartContents.put(product, currentQuantity + quantity);
        } else {
            cartContents.put(product, quantity);
        }
    }
    // Getters and setters
}
