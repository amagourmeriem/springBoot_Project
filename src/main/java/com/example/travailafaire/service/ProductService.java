package com.example.travailafaire.service;

import com.example.travailafaire.DAO.entities.Product;
import com.example.travailafaire.DAO.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ProductManager{
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Product addProduct(Product product) {
        if(product.getPrice()>0) {
           return productRepository.save(product);
        }else{
            System.out.println("tHE PRICE is not valid");
            return null;
        }
    }

    @Override
    public Product updateProduct(Product product) {
       return productRepository.save(product);
    }

    @Override
    public boolean deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
    @Override
    public Page<Product> searchProductsByKeyword(String keyword, int page, int taille) {
        return productRepository.findByDescriptionContains(keyword, PageRequest.of(page, taille));
    }
    @Override
    public Page<Product> getAllProducts(int page, int taille) {

        return productRepository.findAll(PageRequest.of(page, taille));
     }
     @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
    }



