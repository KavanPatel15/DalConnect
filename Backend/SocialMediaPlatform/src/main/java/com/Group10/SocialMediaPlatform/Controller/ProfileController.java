package com.Group10.SocialMediaPlatform.Controller;

import com.Group10.SocialMediaPlatform.model.Profile;
import com.Group10.SocialMediaPlatform.Service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    // Endpoint to create a new profile for a specific user
    @PostMapping
    public Profile createProfile(@RequestBody Profile profile, @RequestParam Integer userId) {
        return profileService.createProfile(profile, userId);
    }

    // Endpoint to get all profiles
    @GetMapping
    public List<Profile> getAllProfiles() {
        return profileService.getAllProfiles();
    }

    // Endpoint to get a profile by user ID
    @GetMapping("/{userId}")
    public Profile getProfileByUserId(@PathVariable Integer userId) {
        return profileService.getProfileByUserId(userId);
    }

    // Endpoint to update a profile by user ID
    @PatchMapping("/{userId}")
    public Profile updateProfile(@PathVariable Integer userId, @RequestBody Profile profile) {
        return profileService.updateProfileByUserId(profile, userId);
    }
}
