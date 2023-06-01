package com.pawsomeadoptions.capstoneproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingPageController {
    @GetMapping("/")
    public String showLandingPage() {
        // Process logic for the landing page
        return "landing-page"; // Return the name of the view template
    }
}
