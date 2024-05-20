package com.example.travailafaire.service;

import com.example.travailafaire.DAO.entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductManager {
public Product addProduct(Product product);
public Product updateProduct(Product product);
public boolean deleteProduct(Long id);
public Product getProductById(Long id);
public Page<Product> searchProductsByKeyword(String keyword, int page, int taille);

public Page<Product> getAllProducts(int page, int taille);
}
