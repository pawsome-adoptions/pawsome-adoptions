package com.pawsomeadoptions.capstoneproject.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true, name = "username")
    private String username;

    @Column(nullable = false, length = 200, unique = true, name = "email")
    private String email;

    @Column(nullable = false, length = 500, name = "password")
    private String password;

    @Column(nullable = true , name = "profile_pic", length = 2500)
    private String profilePic;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Post> posts;

    public User() {
        // Default constructor required by JPA
    }

    //constructor with id


    public User(Long id, String username, String email, String password, String profilePic, List<Post> posts) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profilePic = profilePic;
        this.posts = posts;
    }

    public User(String username, String email, String password, String profilePic, List<Post> posts) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profilePic = profilePic;
        this.posts = posts;
    }

    public User(User copy) {
        id = copy.id; // This line is SUPER important! Many things won't work if it's absent
        email = copy.email;
        username = copy.username;
        password = copy.password;
        profilePic = copy.profilePic;
    }

    // getters, and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }



}
