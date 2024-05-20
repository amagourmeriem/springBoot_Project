package com.example.travailafaire.DAO.repositories;

import com.example.travailafaire.DAO.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    public List<User> findByEmail (String email);

}
