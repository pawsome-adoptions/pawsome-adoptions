package com.pawsomeadoptions.capstoneproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutUsController {
    @GetMapping("/about") //This is for the pet all the ads from pet API
    public String showPetApi() {
        return "home/about-us";
    }
}
