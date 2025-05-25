package com.artxchange.artxchange.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginSuccessController {

    @PostMapping("/login-success")
    public String handleLoginSuccess() {
        // Authentication is already handled by FirebaseAuthenticationFilter
        // Just redirect to home page
        return "redirect:/home";
    }
}