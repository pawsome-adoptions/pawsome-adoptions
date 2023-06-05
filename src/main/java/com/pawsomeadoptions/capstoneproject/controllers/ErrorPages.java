package com.pawsomeadoptions.capstoneproject.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class ErrorPages {

    @GetMapping("/invalidUsernameOrPassword") //This is for the pet all the ads from pet API
    public String usernameError() {
        return "errors/invalid-pass-user";
    }
}
