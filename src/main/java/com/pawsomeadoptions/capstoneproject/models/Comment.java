package com.pawsomeadoptions.capstoneproject.models;

import jakarta.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;


    @Column(nullable = false, length = 255, name = "commentText")
    private String commentText;

    // Constructors
    public Comment() {

    }

    public Comment(Long id, User user, Post post, String commentText) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.commentText = commentText;
    }

    public Comment(User user, Post post, String commentText) {
        this.user = user;
        this.post = post;
        this.commentText = commentText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}

