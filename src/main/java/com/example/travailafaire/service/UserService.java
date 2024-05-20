package com.example.travailafaire.service;

import com.example.travailafaire.DAO.entities.Role;
import com.example.travailafaire.DAO.entities.User;
import com.example.travailafaire.DAO.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserManager {

    private final PasswordEncoder passwordEncoder;
    private static List<User> users = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void postConstruct() {
        User user = new User();
        user.setId(user.getId());
        user.setRole(Role.ADMIN);
        user.setFirstname("meriem");
        user.setLastname("amagour");
        user.setUsername("meriem");
        user.setEmail("meriem@gmail.com");
        user.setPassword(passwordEncoder.encode("meriem"));

    userRepository.save(user);
    }

    @Override
    @Transactional
    public void registerUser(User user) {
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User findByLogin(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            existingUser.setEmail(user.getEmail());
            userRepository.save(existingUser);
        }
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public void saveUsers(List<User> users) {
        userRepository.saveAll(users);
    }
}
