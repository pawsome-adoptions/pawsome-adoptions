package com.pawsomeadoptions.capstoneproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PetApiController {
    @GetMapping("/adopt") //This is for the pet all the ads from pet API
    public String showPetApi() {
        return "pet-api/all-pets";
    }

    @GetMapping("/adopt/{petID}") //This is for the pet all the ads from pet API
    public String showSinglePetApi() {
        return "pet-api/single-pet";
    }
}
