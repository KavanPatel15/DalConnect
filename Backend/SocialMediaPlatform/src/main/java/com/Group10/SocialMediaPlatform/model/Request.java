package com.Group10.SocialMediaPlatform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Requests")
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer requestId; // Unique identifier for the request

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender; // The user who sent the request

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver; // The user who received the request

    @Column(nullable = false)
    private String status; // Status of the request, e.g., 'pending', 'accepted', 'rejected'

    @Column(nullable = false, updatable = false) // Timestamp when the request was created
    private LocalDateTime createdAt;

    // Automatically set the creation timestamp before persisting the entity
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
