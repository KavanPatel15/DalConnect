package com.Group10.SocialMediaPlatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "User_Groups")
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use identity strategy for auto-increment
    private Integer groupId;

    private String groupName;

    private String description;

    @Column(name = "is_private") // Specify column name
    private boolean isPrivate;

    private String interests;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false) // Foreign key for user who created the group
    private User createdBy;

    @Column(name = "created_at", nullable = false, updatable = false) // Timestamp for creation, non-updatable
    private LocalDateTime createdAt;

    // Automatically set the creation timestamp before persisting the entity
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Getter and Setter for isPrivate (optional as Lombok generates them)
    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }
}
