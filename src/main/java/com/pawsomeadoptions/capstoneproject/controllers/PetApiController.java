package com.pawsomeadoptions.capstoneproject.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PetApiController {

    @Value("${clientId.petfinder}")
    private String clientID;

    @Value("${secret.petfinder}")
    private String secret;

    @GetMapping("/adopt") //This is for the pet all the ads from pet API
    public String showPetApi(Model model) {
        model.addAttribute("clientIDView", clientID);
        model.addAttribute("secretView", secret);

        return "pet-api/all-pets";
    }

    @GetMapping("/adopt/{page}") //This is for the pet all the ads from pet API
    public String showPetPage(@PathVariable int page) {
        return "pet-api/all-pets";
    }

//    @GetMapping("/adopt/{petID}") //This is for the pet all the ads from pet API
//    public String showSinglePetApi() {
//        return "pet-api/single-pet";
//    }

}
