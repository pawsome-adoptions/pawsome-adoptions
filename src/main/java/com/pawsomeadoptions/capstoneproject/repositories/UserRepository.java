package com.pawsomeadoptions.capstoneproject.repositories;

import com.pawsomeadoptions.capstoneproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
