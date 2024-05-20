package com.example.travailafaire.DAO.repositories;

import com.example.travailafaire.DAO.entities.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String keyword);
    Page<Product> findByDescriptionContains(String keyword, Pageable pageable);


}
