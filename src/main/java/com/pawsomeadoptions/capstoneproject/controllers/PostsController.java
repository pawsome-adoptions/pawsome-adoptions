package com.pawsomeadoptions.capstoneproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostsController {
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
    @GetMapping("/usersposts")
    public String showUsersPosts() {
        return "posts/user-post";
    }

    //single posts for users who are logged in
    @GetMapping("/usersposts/{postID}")
    public String showSingleUsersPosts() {
        return "posts/visitor-post";
    }
}
