package com.Group10.SocialMediaPlatform.Service;

import com.Group10.SocialMediaPlatform.model.Profile;
import com.Group10.SocialMediaPlatform.model.User;
import com.Group10.SocialMediaPlatform.Repository.ProfileRepository;
import com.Group10.SocialMediaPlatform.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new profile for a user. Ensures the user does not already have a profile.
     *
     * @param profile the Profile entity to be created
     * @param userId the ID of the user for whom the profile is created
     * @return the created Profile entity
     * @throws IllegalArgumentException if the user already has a profile or if the user is not found
     */
    public Profile createProfile(Profile profile, Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (profileRepository.existsByUser(user)) {
                throw new IllegalArgumentException("User already has a profile."); // Prevent duplicate profiles
            }
            profile.setUser(user);
            return profileRepository.save(profile); // Save and return the new profile
        } else {
            throw new IllegalArgumentException("User not found."); // Handle case where user does not exist
        }
    }

    /**
     * Retrieves all profiles from the database.
     *
     * @return a list of all Profile entities
     */
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll(); // Return the list of all profiles
    }

    /**
     * Retrieves the profile of a user by user ID.
     *
     * @param userId the ID of the user whose profile is to be retrieved
     * @return the Profile entity associated with the given user ID
     * @throws IllegalArgumentException if the user is not found or if the user does not have a profile
     */
    public Profile getProfileByUserId(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return profileRepository.findByUser(user).orElse(null); // Return profile or null if not found
        } else {
            throw new IllegalArgumentException("User not found."); // Handle case where user does not exist
        }
    }

    /**
     * Updates the profile of a user identified by user ID.
     *
     * @param profile the Profile entity with updated information
     * @param userId the ID of the user whose profile is to be updated
     * @return the updated Profile entity
     * @throws IllegalArgumentException if the user or the existing profile is not found
     */
    public Profile updateProfileByUserId(Profile profile, Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<Profile> existingProfileOptional = profileRepository.findByUser(user);
            if (existingProfileOptional.isPresent()) {
                Profile existingProfile = existingProfileOptional.get();
                existingProfile.setBio(profile.getBio());
                existingProfile.setProfilePicture(profile.getProfilePicture());
                return profileRepository.save(existingProfile); // Save and return the updated profile
            } else {
                throw new IllegalArgumentException("Profile not found."); // Handle case where profile does not exist
            }
        } else {
            throw new IllegalArgumentException("User not found."); // Handle case where user does not exist
        }
    }
}
