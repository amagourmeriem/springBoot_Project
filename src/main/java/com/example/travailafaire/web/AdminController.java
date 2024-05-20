package com.example.travailafaire.web;

import com.example.travailafaire.DAO.entities.User;
import com.example.travailafaire.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/users")
    public String getAllUsers(Model model) {
        // Récupérer la liste de tous les utilisateurs depuis le service
        Iterable<User> users = userService.getAllUsers();
        // Ajouter la liste à l'objet Model pour l'affichage dans la vue
        model.addAttribute("users", users);
        // Retourner le nom de la vue à afficher
        return "admin/users";
    }

    @GetMapping("/admin/deleteUser")
    public String deleteUser(@RequestParam("userId") Long userId) {
        // Supprimer l'utilisateur avec l'ID spécifié
        userService.deleteUser(userId);
        // Rediriger vers la page d'administration des utilisateurs
        return "redirect:/admin/users";
    }

    // Ajoutez d'autres méthodes pour gérer les opérations d'administration des utilisateurs si nécessaire

}
