package com.pawsomeadoptions.capstoneproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/register")
    public String registerForm() {
        return "users/register";
    }

    @GetMapping("/profile")
    public String profileForm() {
        return "users/profile";
    }

    @Controller
    public class loginController {
        @GetMapping("/login")
        public String showLoginForm() {
            return "users/login";
        }

    }

}
