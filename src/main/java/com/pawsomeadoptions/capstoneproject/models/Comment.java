package com.pawsomeadoptions.capstoneproject.models;

import jakarta.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @Column(name = "comment_text", length = 250)
    private String commentText;

    // Constructors
    public Comment() {

    }

    public Comment(Long id, User user, Post post, String comment) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.commentText = comment;
    }

    public Comment(User user, Post post, String comment) {
        this.user = user;
        this.post = post;
        this.commentText = comment;
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

    public String getComment() {
        return commentText;
    }

    public void setComment(String comment) {
        this.commentText = comment;
    }
}


