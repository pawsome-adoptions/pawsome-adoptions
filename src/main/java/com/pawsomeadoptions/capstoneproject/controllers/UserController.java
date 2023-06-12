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

import java.security.SecureRandom;
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
    public String editProfileView(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = userDao.getReferenceById(user.getId());
        List<Post> allUserPosts = postDao.findAllByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("allUserPosts", allUserPosts);
        return "users/profile";
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

    // method to generate a random password
    public static String randomPassword(int len) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";// ASCII range

        SecureRandom random = new SecureRandom(); //used SecureRandom() class to create a random number
        StringBuilder sb = new StringBuilder(); //used StringBuilder() class to append characters

        for (int i = 0; i < len; i++)
        {
            int randomIndex = random.nextInt(chars.length()); //makes a random number within the range of chars
            sb.append(chars.charAt(randomIndex)); //appends the character at the random index
        }

        return sb.toString(); //convert it to string
    }

    @GetMapping("/resetpassword")
    public String resetPassword(Model model) {
        model.addAttribute("user", new User());
        return "users/reset-password";
    }

    @PostMapping("/resetpassword")
    public String handleResetPassword(@ModelAttribute User user) {
        String username = user.getUsername();
        User existingUser = userDao.findByUsername(username);

        if (existingUser != null) {
            String newPassword = randomPassword(8);
            String hashedPassword = passwordEncoder.encode(newPassword);

            existingUser.setPassword(hashedPassword);
            userDao.save(existingUser);

            String subject = "Password Reset Successful";
            String body = "Dear " + existingUser.getUsername() + ",\n\n"
                    + "Your password has been successfully reset. Your new password is: " + newPassword + "\n\n"
                    + "Please login using your new password.\n\n"
                    + "Regards,\nPawesome Adoptions";

            emailService.prepareAndSend(existingUser.getEmail(), subject, body);

            System.out.println("Password Reset Successful");
        } else {
            // username not found
        }

        return "users/login";
    }



}
