package com.Group10.SocialMediaPlatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Group_Members")
@NoArgsConstructor
@AllArgsConstructor
public class GroupMember {

    @EmbeddedId
    private GroupMemberId id; // Composite primary key for the GroupMember entity

    @ManyToOne
    @MapsId("groupId") // Maps the "groupId" part of the composite key to the Group entity
    @JoinColumn(name = "group_id") // Foreign key column referring to the Group entity
    private Group group;

    @ManyToOne
    @MapsId("userId") // Maps the "userId" part of the composite key to the User entity
    @JoinColumn(name = "user_id") // Foreign key column referring to the User entity
    private User user;

    private String role; // Role of the user within the group (e.g., admin, member)

    @Column(name = "created_at", nullable = false, updatable = false) // Timestamp for when the member was added to the group
    private LocalDateTime createdAt;

    // Automatically set the creation timestamp before persisting the entity
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
