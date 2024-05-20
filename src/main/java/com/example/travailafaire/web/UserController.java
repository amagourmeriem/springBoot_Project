package com.example.travailafaire.web;

import com.example.travailafaire.DAO.entities.User;
import com.example.travailafaire.service.UserManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserManager userManager;


    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // Invalidate the session to log the user out
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // Redirect to the login page or homepage after logout
        return "redirect:/login";
    }

//    @PostMapping("/login-post")
//    public String postLoginPage(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null && auth.isAuthenticated()) {
//            // User is already authenticated, redirect to index
//            return "redirect:/Index";
//        } else {
//            // Authentication failed, redirect to login page with error message
//            return "redirect:/login?error";
//        }
//    }
    @GetMapping("/login")
    public String getLoginPage(Model model) {
        User user=new User();
        model.addAttribute("user",user);
        return "login_page";
    }


    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("user",new User());
        return"register_page";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user){
        userManager.registerUser(user);
        return "redirect:/login?success";
    }

    @PostMapping
    public ResponseEntity<String> saveUsers(@RequestBody List<User> users) {
        try {
            userManager.saveUsers(users);
            return ResponseEntity.status(HttpStatus.CREATED).body("Users saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save users: " + e.getMessage());
        }
    }
}
