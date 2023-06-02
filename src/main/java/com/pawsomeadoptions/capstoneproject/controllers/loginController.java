package com.pawsomeadoptions.capstoneproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class loginController {
    @GetMapping("/login")
    public String showLoginForm() {
        return "users/login";
    }

}
