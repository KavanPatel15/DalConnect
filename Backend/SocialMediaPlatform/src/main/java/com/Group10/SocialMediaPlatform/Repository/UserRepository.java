package com.Group10.SocialMediaPlatform.Repository;

import com.Group10.SocialMediaPlatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Finds a user by their email address
    Optional<User> findByEmail(String email);

    // Finds all users who are approved
    List<User> findByApprovedTrue();

    // Finds all users who are not approved
    List<User> findByApprovedFalse();

    // Finds users by their approval and deletion status
    List<User> findByApprovedAndDeleted(boolean approved, boolean deleted);
}

