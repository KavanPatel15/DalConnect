package com.Group10.SocialMediaPlatform.Controller;

import com.Group10.SocialMediaPlatform.model.User;
import com.Group10.SocialMediaPlatform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint to create a new user (signup)
    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    // Endpoint to create a new admin user (admin signup)
    @PostMapping("/adminsignup")
    public ResponseEntity<?> createAdmin(@RequestBody User user) {
        try {
            if (!"admin".equalsIgnoreCase(user.getRole())) {
                throw new IllegalArgumentException("User role must be 'admin' for admin signup.");
            }

            User createdUser = userService.createUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    // Endpoint to login a user
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            User loggedInUser = userService.login(user.getEmail(), user.getPassword());
            if (loggedInUser.isApproved()) {
                // Prepare a response object that includes user details and role
                Map<String, Object> response = new HashMap<>();
                response.put("userId", loggedInUser.getUserId());
                response.put("firstName", loggedInUser.getFirstName());
                response.put("lastName", loggedInUser.getLastName());
                response.put("email", loggedInUser.getEmail());
                response.put("role", loggedInUser.getRole());

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not approved");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong email or password");
        }
    }

    // Endpoint to reset a user's password
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody User user) {
        try {
            User updatedUser = userService.resetPassword(user.getEmail(), user.getSecurityAnswer(), user.getPassword());
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint to get a user by their ID
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Integer userId) {
        return userService.getUserById(userId);
    }

    // Endpoint to get all users except the specified user and their friends
    @GetMapping("/getOther/{userId}")
    public List<User> getUsersExceptUserId(@PathVariable Integer userId) {
        return userService.getAllUsersExceptUserAndFriends(userId);
    }

    // Endpoint to get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Endpoint to update a user by their ID
    @PatchMapping("/{userId}")
    public User updateUser(@PathVariable Integer userId, @RequestBody User user) {
        user.setUserId(userId);
        return userService.updateUser(user);
    }

    // Endpoint to delete a user by their ID
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
    }
}
