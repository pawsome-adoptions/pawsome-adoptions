package com.pawsomeadoptions.capstoneproject.controllers;

import com.pawsomeadoptions.capstoneproject.models.Post;
import com.pawsomeadoptions.capstoneproject.models.User;
import com.pawsomeadoptions.capstoneproject.repositories.PostRepository;
import com.pawsomeadoptions.capstoneproject.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostsController {
    private PostRepository postDao;

    public PostsController(PostRepository postDao) {
        this.postDao = postDao;
    }

    //posts for visitors who aren't logged in
    @GetMapping("/visitorpost")
    public String showVisitorsPosts() {
        return "posts/visitor-post";
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
    public String userPost(@ModelAttribute Post post) {
        postDao.save(post);

        System.out.println("Post Create Successful");
        return "redirect:/posts/user-post";
    }

    //single posts for users who are logged in
    @GetMapping("/usersposts/{postID}")
    public String showSingleUsersPosts() {
        return "posts/visitor-post";
    }
}
