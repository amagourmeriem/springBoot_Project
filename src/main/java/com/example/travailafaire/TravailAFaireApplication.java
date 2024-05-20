package com.example.travailafaire;

import com.example.travailafaire.DAO.entities.Product;
import com.example.travailafaire.DAO.repositories.ProductRepository;
import com.example.travailafaire.service.ProductManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class TravailAFaireApplication implements CommandLineRunner {
    @Autowired
    private ProductManager productManager;

    public static void main(String[] args) {
        SpringApplication.run(TravailAFaireApplication.class, args);
    }

    @Override
    public void run(String[] args) throws Exception {
//        Product product = new Product(1, "SUNSCREEN", "CERAVE", 200,"images/suscreen.jpg");
//        productManager.addProduct(product);
//        Product product2 = new Product(2, "moisturizer", "CERAVE", 300,"images/cerave.webp");
//        productManager.addProduct(product2);
        // Création d'un produit pour tester
//        Product product = new Product(1, "Tshirt", "pink", 200);
//        productManager.addProduct(product);
//        Product product1 = new Product(2, "pull", "black", 250);
//        productManager.addProduct(product1);
//        Product product2 = new Product(3, "hodie", "green", 470);
//        productManager.addProduct(product2);
//
//
//
//        // Affichage de tous les produits
//        System.out.println(productManager.getAllProducts());
    }

}

