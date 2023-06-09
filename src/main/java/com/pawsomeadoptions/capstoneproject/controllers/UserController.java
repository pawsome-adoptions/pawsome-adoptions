package com.pawsomeadoptions.capstoneproject.controllers;

import com.pawsomeadoptions.capstoneproject.models.Post;
import com.pawsomeadoptions.capstoneproject.models.User;
import com.pawsomeadoptions.capstoneproject.repositories.PostRepository;
import com.pawsomeadoptions.capstoneproject.repositories.UserRepository;
import com.pawsomeadoptions.capstoneproject.service.EmailService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class UserController {
    private final PostRepository postDao;
    private UserRepository userDao;
    private EmailService emailService;
    private PasswordEncoder passwordEncoder;

    public UserController(PostRepository postDao, UserRepository userDao, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;

    }

    @GetMapping("/sign-up")
    public String showSignupForm(Model model){
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

//    Post method for editing the user profile
    @PostMapping("/profile/{id}")
    public String editProfile(@ModelAttribute User user){
        userDao.save(user);
        return "redirect:/profile";
    }

    @PostMapping("/deleteUser")
    public String deleteProfile() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Post> allUserPosts = postDao.findAllByUser(user);
        postDao.deleteAll(allUserPosts);

        System.out.println(user.getId() + " " + user.getUsername());

        user = userDao.getReferenceById(user.getId());

        userDao.delete(user);
//        Need to test below: don't want to delete too many users if register is messed up

        return "redirect:/logout"; // Redirect to the logout page after deleting the profile
    }
    @GetMapping("/reset-password")
    public String resetPassword() {
        return "users/reset-password";
    }


}
