package com.pawsomeadoptions.capstoneproject.models;

import com.pawsomeadoptions.capstoneproject.repositories.CategoriesRepository;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 100, name = "title" )
    private String title;

    @Column(nullable = false, length = 300, name = "description")
    private String description;

    @Column(nullable = false, name = "category", length = 50)
    private String category;

    @Column(nullable = false, name = "img", length = 2500)
    private String img;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="posts_categories",
            joinColumns={@JoinColumn(name="posts_id")},
            inverseJoinColumns={@JoinColumn(name="category_id")}
    )
    private List<Category> categories;


    // Constructors, getters, and setters

    public Post() {

    }

    public Post(Long id, String title, String description, String img, User users, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.img = img;
        this.user = users;
        this.category = category;
    }

    public Post(String title, String description, String img, User users, String category) {
        this.title = title;
        this.description = description;
        this.img = img;
        this.user = users;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public User getUsers() {
        return user;
    }

    public void setUsers(User user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
