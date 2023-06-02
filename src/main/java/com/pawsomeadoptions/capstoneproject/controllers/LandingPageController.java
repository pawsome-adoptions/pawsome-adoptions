package com.pawsomeadoptions.capstoneproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LandingPageController {
    @GetMapping("/home")
    public String showLandingPage() {
        return "home/landing-page";
    }
}
