package com.pawsomeadoptions.capstoneproject.repositories;

import com.pawsomeadoptions.capstoneproject.models.Post;
import com.pawsomeadoptions.capstoneproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>{

    Post findByTitle(String title);

    List<Post> findAllByUser(User user);

}
