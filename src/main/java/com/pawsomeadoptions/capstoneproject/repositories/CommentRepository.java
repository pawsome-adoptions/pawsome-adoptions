package com.pawsomeadoptions.capstoneproject.repositories;

import com.pawsomeadoptions.capstoneproject.models.Comment;
import com.pawsomeadoptions.capstoneproject.models.Post;
import com.pawsomeadoptions.capstoneproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, String> {

}
