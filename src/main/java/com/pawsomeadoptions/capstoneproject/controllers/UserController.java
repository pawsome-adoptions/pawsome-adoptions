package com.pawsomeadoptions.capstoneproject.controllers;

import com.pawsomeadoptions.capstoneproject.models.User;
import com.pawsomeadoptions.capstoneproject.repositories.UserRepository;
import com.pawsomeadoptions.capstoneproject.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private UserRepository userDao;
    private EmailService emailService;

    public UserController(UserRepository userDao, EmailService emailService) {
        this.userDao = userDao;
        this.emailService = emailService;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "users/register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user){
        userDao.save(user);

        String subject = "Thank you for joining Pawesome Adoptions!";
        String body = "Dear " + user.getUsername() + ",\n\n"
                + "Thanks again for joining our community. Your account has been successfully created.\n"
                + "We look forward to see all your pawesome stories!\n\n"
                + "Cheers,\nPawesome Adoptions";

        emailService.prepareAndSend(user.getEmail(), subject, body);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profileForm() {
        return "users/profile";
    }


    @GetMapping("/login")
    public String showLoginForm() {
        return "users/login";
    }


}
