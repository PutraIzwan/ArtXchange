package com.artxchange.artxchange.controller;

import com.google.firebase.auth.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String email,
            @RequestParam String password,
            RedirectAttributes redirectAttributes
    ) {
        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password);

            FirebaseAuth.getInstance().createUser(request);
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/login";
        } catch (FirebaseAuthException e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed: " + e.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "idToken", required = false) String idToken,
            Model model
    ) {
        if (error != null) {
            model.addAttribute("error", "Invalid email or password");
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(
            @RequestParam String email,
            @RequestParam String password,
            RedirectAttributes redirectAttributes
    ) {
        try {
            // This should be replaced with client-side Firebase Auth
            // For server-side demo only
            FirebaseAuth.getInstance().getUserByEmail(email);
            return "redirect:/home";
        } catch (FirebaseAuthException e) {
            redirectAttributes.addAttribute("error", "Invalid credentials");
            return "redirect:/login";
        }
    }

    @GetMapping("/login-success")
    public String handleLoginSuccess(
            @RequestParam String idToken,
            HttpServletRequest request,
            HttpServletResponse response) {
        // Token verification is already handled by FirebaseAuthenticationFilter
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String showHome() {
        return "home";
    }
}