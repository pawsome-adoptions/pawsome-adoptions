package com.pawsomeadoptions.capstoneproject.controllers;

import com.pawsomeadoptions.capstoneproject.models.Post;
import com.pawsomeadoptions.capstoneproject.models.User;
import com.pawsomeadoptions.capstoneproject.repositories.PostRepository;
import com.pawsomeadoptions.capstoneproject.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostsController {
    private PostRepository postDao;
    private UserRepository userDao;

    public PostsController(PostRepository postDao, UserRepository userDao) {
        this.postDao = postDao;
        this.userDao = userDao;
    }

    //posts for visitors who aren't logged in
    @GetMapping("/visitorpost")
    public String showVisitorsPosts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            // User is logged in, redirect to the user post
            return "redirect:/userpost";
        } else {
            // User is not logged in, show the visitor post
            return "redirect:/visitorpost";
        }
    }

    //signle posts for visitors who aren't logged in
    @GetMapping("/visitorpost/{postID}")
    public String showSingleVisitorPosts() {

        return "posts/single-visitor";
    }

    //posts for users who are logged in
    @GetMapping("/userpost")
    public String userPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "posts/user-post";
    }

    @PostMapping("/userpost")
    public String userPost(@ModelAttribute Post post, Model model) {
        User myUser = userDao.getReferenceById(1L);
        post.setUsers(myUser);
        model.addAttribute("myUser", myUser);
        System.out.println("Post Create Successful");
        postDao.save(post);
        return "redirect:/profile";
    }

    //single posts for users who are logged in
    @GetMapping("/usersposts/{postID}")
    public String showSingleUsersPosts() {
        return "posts/visitor-post";
    }
}
