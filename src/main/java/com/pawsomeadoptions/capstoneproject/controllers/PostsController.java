package com.pawsomeadoptions.capstoneproject.controllers;

import com.pawsomeadoptions.capstoneproject.models.Post;
import com.pawsomeadoptions.capstoneproject.models.User;
import com.pawsomeadoptions.capstoneproject.repositories.PostRepository;
import com.pawsomeadoptions.capstoneproject.repositories.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostsController {
    private PostRepository postDao;
    private UserRepository userDao;

    public PostsController(PostRepository postDao, UserRepository userDao) {
        this.postDao = postDao;
        this.userDao = userDao;
    }

    //    posts for visitors who aren't logged in
    @GetMapping("/visitorpost")

    public String showVisitorsPosts(Model model) {
        model.addAttribute("posts", postDao.findAll());

        return "posts/visitor-post";
    }

    //signle posts for visitors who aren't logged in
    @GetMapping("/visitorsinglepost")
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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUsers(user);
        System.out.println("Post Create Successful");
        postDao.save(post);
        return "redirect:/profile";
    }

    //single posts for users who are logged in
    @GetMapping("/userpost/{id}")
    public String showSingleUsersPosts(@PathVariable Long id, Model model) {
        Post post = postDao.findById(id).get();
        model.addAttribute("post", post);
        return "posts/visitor-post";
    }

    //post edit for users logged in
    @GetMapping("/userpost/{id}/edit")
    public String editUsersSinglePost(@PathVariable Long id, Model model) {
        Post postToEdit = postDao.getReferenceById(id);

        model.addAttribute("postToViewLayer", postToEdit);
        postDao.save(postToEdit);
        return "posts/edit-post";
    }

    @PostMapping("/posts/submitEdit")
    public String submitPostEdit(@ModelAttribute Post post) {

        //Note: we are hardcoding here, need to have this functionality implemented
//        post.setImg("urlHere");

        //This code is good to go
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = userDao.getReferenceById(user.getId());
        post.setUsers(user);
        postDao.save(post);
        return "redirect:/profile";
    }

    @GetMapping("/userpost/{id}/delete")
    public String deletePost(@PathVariable Long id){
        Post postToDelete = postDao.getReferenceById(id);
        postDao.delete(postToDelete);

        return "redirect:/profile";
    }

}
