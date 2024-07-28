package com.Group10.SocialMediaPlatform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Profiles", uniqueConstraints = @UniqueConstraint(columnNames = "user_id"))
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer profileId; // Unique identifier for the profile

    @Setter
    @Getter
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonIgnore // Prevents serialization of the User reference in JSON responses
    private User user; // Associated user for this profile

    @Setter
    @Getter
    private String bio; // Short biography or description for the profile

    @Setter
    @Getter
    private String profilePicture; // URL or path to the profile picture

    @Getter
    @Column(nullable = false, updatable = false) // Timestamp when the profile was created
    private LocalDateTime createdAt;

    @Column(nullable = false) // Timestamp when the profile was last updated
    private LocalDateTime updatedAt;

    // Automatically set the creation and update timestamps before persisting the entity
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    // Automatically update the timestamp before updating the entity
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
