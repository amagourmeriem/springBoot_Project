package com.example.travailafaire.service;

import com.example.travailafaire.DAO.entities.User;

import java.util.List;

public interface UserManager {
    List<User> getAllUsers();
    User getUserByUsername(String username);
    User getUserById(Long id); // Utilisez Long au lieu de int pour l'identifiant
    void createUser(User user);
    void updateUser(User user);
    User findByLogin(String login);
    public void saveUsers(List<User> users);
    void deleteUser(Long id); // Utilisez Long au lieu de int pour l'identifiant
    boolean authenticateUser(String username, String password);
     void registerUser(User user);
}
