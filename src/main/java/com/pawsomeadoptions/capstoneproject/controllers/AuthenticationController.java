package com.pawsomeadoptions.capstoneproject.controllers;

import com.pawsomeadoptions.capstoneproject.models.Post;
import com.pawsomeadoptions.capstoneproject.models.User;
import com.pawsomeadoptions.capstoneproject.repositories.PostRepository;
import com.pawsomeadoptions.capstoneproject.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AuthenticationController {

    private PostRepository postDao;
    private UserRepository userDao;

    public AuthenticationController(UserRepository userDao, PostRepository postDao) {
        this.userDao = userDao;
        this.postDao = postDao;
    }
    @GetMapping("/login")
    public String loginForm() {
        return "users/login";
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
}
