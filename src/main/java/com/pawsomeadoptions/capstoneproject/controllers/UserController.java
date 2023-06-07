package com.pawsomeadoptions.capstoneproject.controllers;

import com.pawsomeadoptions.capstoneproject.models.User;
import com.pawsomeadoptions.capstoneproject.repositories.UserRepository;
import com.pawsomeadoptions.capstoneproject.service.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private UserRepository userDao;
    private EmailService emailService;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository userDao, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/sign-up")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "users/sign-up";
    }


    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user){
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
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


//    @PostMapping("/login")
//    public String login(@RequestParam String username, @RequestParam String password) {
//        User user = userDao.findByUsername(username);
//
//        if (user != null && user.getPassword().equals(password)) {
//            return "redirect:/usersposts";
//        } else {
//            return "redirect:/invalidUsernameOrPassword";
//        }
//    }
}
