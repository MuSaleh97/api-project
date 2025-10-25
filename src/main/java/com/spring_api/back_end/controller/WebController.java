package com.spring_api.back_end.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    @GetMapping("/verify-account")
    public String verifyAccountPage() {
        return "verify-account";
    }

    @GetMapping("/")
    public String homePage() {
        return "redirect:/login"; // Redirect to login instead of registration
    }
}
