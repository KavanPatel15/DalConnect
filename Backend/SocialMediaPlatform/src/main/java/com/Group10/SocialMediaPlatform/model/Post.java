package com.Group10.SocialMediaPlatform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Posts")
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId; // Unique identifier for the post

    @ManyToOne(fetch = FetchType.EAGER) // Many-to-one relationship with User
    @JoinColumn(name = "user_id", nullable = false) // Foreign key column referring to the User entity
    private User user; // User who created the post

    private String content; // Content of the post

    @Column(nullable = false, updatable = false) // Timestamp when the post was created
    private LocalDateTime createdAt;

    // Automatically set the creation timestamp before persisting the entity
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
