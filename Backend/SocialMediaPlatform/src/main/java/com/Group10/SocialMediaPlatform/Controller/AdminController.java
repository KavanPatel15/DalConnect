package com.Group10.SocialMediaPlatform.Controller;

import com.Group10.SocialMediaPlatform.Service.UserService;
import com.Group10.SocialMediaPlatform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    // Endpoint to get users pending approval
    @GetMapping("/pending-approvals")
    public ResponseEntity<List<User>> getPendingApprovals() {
        List<User> pendingUsers = userService.getPendingApprovals();
        return new ResponseEntity<>(pendingUsers, HttpStatus.OK);
    }

    // Endpoint to get approved users
    @GetMapping("/approved-users")
    public ResponseEntity<List<User>> getApprovedUsers() {
        List<User> approvedUsers = userService.getApprovedUsers();
        return new ResponseEntity<>(approvedUsers, HttpStatus.OK);
    }

    // Endpoint to approve a user by their ID
    @PostMapping("/approve/{userId}")
    public ResponseEntity<Void> approveUser(@PathVariable Integer userId) {
        userService.approveUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Endpoint to reject a user by their ID
    @DeleteMapping("/reject/{userId}")
    public ResponseEntity<Void> rejectUser(@PathVariable Integer userId) {
        userService.rejectUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Endpoint to update a user's role by their ID
    @PutMapping("/update-role/{userId}")
    public ResponseEntity<Void> updateUserRole(@PathVariable Integer userId, @RequestParam String role) {
        userService.updateUserRole(userId, role);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Endpoint to create a new approved user
    @PostMapping("/create-approved-user")
    public ResponseEntity<User> createApprovedUser(@RequestBody User user) {
        User newUser = userService.createApprovedUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // Endpoint to soft delete a user by their ID
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> softDeleteUser(@PathVariable Integer userId) {
        userService.softDeleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
