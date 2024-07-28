package com.Group10.SocialMediaPlatform.Service;

import com.Group10.SocialMediaPlatform.Repository.FriendRepository;
import com.Group10.SocialMediaPlatform.Repository.UserRepository;
import com.Group10.SocialMediaPlatform.model.Friend;
import com.Group10.SocialMediaPlatform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    @Autowired
    private FriendRepository friendRepository;

    /**
     * Creates a new user and sets the user as approved if the role is "admin".
     *
     * @param user the User entity to be created
     * @return the saved User entity
     * @throws IllegalArgumentException if the password is null or empty
     */
    public User createUser(User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        // Automatically approve the user if they are an admin
        if ("admin".equalsIgnoreCase(user.getRole())) {
            user.setApproved(true);
        }

        return userRepository.save(user);
    }

    /**
     * Creates a new user and sets the user as approved by default.
     *
     * @param user the User entity to be created
     * @return the saved User entity
     * @throws IllegalArgumentException if the password is null or empty
     */
    public User createApprovedUser(User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        // Set the user as approved by default
        user.setApproved(true);

        return userRepository.save(user);
    }

    /**
     * Authenticates a user based on their email and password.
     *
     * @param email the email of the user
     * @param password the password of the user
     * @return the authenticated User entity
     * @throws IllegalArgumentException if the email or password is invalid
     */
    public User login(String email, String password) {
        User user = findUserByEmail(email);
        validatePassword(user, password);
        return user;
    }

    /**
     * Finds a user by their email.
     *
     * @param email the email of the user
     * @return the User entity
     * @throws IllegalArgumentException if no user with the specified email is found
     */
    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
    }

    /**
     * Validates the user's password.
     *
     * @param user the User entity
     * @param password the password to validate
     * @throws IllegalArgumentException if the password is incorrect
     */
    private void validatePassword(User user, String password) {
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }

    /**
     * Resets the password for a user if the provided security answer is correct.
     *
     * @param email the email of the user
     * @param securityAnswer the security answer provided by the user
     * @param newPassword the new password to set
     * @return the updated User entity
     * @throws IllegalArgumentException if the security answer is incorrect
     */
    public User resetPassword(String email, String securityAnswer, String newPassword) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getSecurityAnswer().equals(securityAnswer)) {
            user.get().setPassword(newPassword);
            return userRepository.save(user.get());
        } else {
            throw new IllegalArgumentException("Security answer is incorrect.");
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user
     * @return the User entity, or null if not found
     */
    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all User entities
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves all users except the current user and their friends.
     *
     * @param userId the ID of the current user
     * @return a list of users excluding the current user and their friends
     */
    public List<User> getAllUsersExceptUserAndFriends(Integer userId) {
        List<User> users = getAllUsers();
        User currUser = getUserById(userId);

        users.remove(currUser);

        List<Friend> friendsForCurrentUser = friendRepository.findByUser_UserId(userId);

        for (Friend friend : friendsForCurrentUser) {
            users.remove(friend.getFriend());
        }

        return users;
    }

    /**
     * Updates the details of an existing user.
     *
     * @param user the User entity with updated details
     * @return the updated User entity
     * @throws IllegalArgumentException if the user does not exist
     */
    public User updateUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            // Assuming password update is handled separately, not shown in this method
            user.setPassword(user.getPassword());
            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found.");
        }
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to delete
     */
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    /**
     * Retrieves all users who are pending approval.
     *
     * @return a list of users who are not yet approved
     */
    public List<User> getPendingApprovals() {
        return userRepository.findByApprovedFalse();
    }

    /**
     * Retrieves all approved users.
     *
     * @return a list of users who are approved
     */
    public List<User> getApprovedUsers() {
        return userRepository.findByApprovedTrue();
    }

    /**
     * Approves a user by their ID.
     *
     * @param userId the ID of the user to approve
     * @throws IllegalArgumentException if the user is not found
     */
    public void approveUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setApproved(true);
        userRepository.save(user);
    }

    /**
     * Rejects and deletes a user by their ID.
     *
     * @param userId the ID of the user to reject
     */
    public void rejectUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    /**
     * Updates the role of a user by their ID.
     *
     * @param userId the ID of the user
     * @param role the new role to set
     * @throws IllegalArgumentException if the user is not found
     */
    public void updateUserRole(Integer userId, String role) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    /**
     * Soft deletes a user by setting their 'deleted' status to true.
     *
     * @param userId the ID of the user to soft delete
     * @throws RuntimeException if the user is not found
     */
    public void softDeleteUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setDeleted(true);
        userRepository.save(user);
    }
}
