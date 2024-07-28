package com.Group10.SocialMediaPlatform.Controller;

import com.Group10.SocialMediaPlatform.model.Friend;
import com.Group10.SocialMediaPlatform.Service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendController {

    @Autowired
    private FriendService friendService;

    // Endpoint to add a new friend
    @PostMapping
    public Friend addFriend(@RequestBody Friend friend) {
        return friendService.addFriend(friend);
    }

    // Endpoint to get friends by user ID
    @GetMapping("/{userId}")
    public List<Friend> getFriendsByUserId(@PathVariable Integer userId) {
        return friendService.getFriendsByUserId(userId);
    }

    // Endpoint to get all friends for a user by user ID
    @GetMapping("/all/{userId}")
    public List<Friend> getAllFriendsForUser(@PathVariable Integer userId) {
        return friendService.getAllFriendsForUser(userId);
    }

    // Endpoint to remove a friend by user ID and friend ID
    @DeleteMapping("/{userId}/{friendId}")
    public void removeFriend(@PathVariable Integer userId, @PathVariable Integer friendId) {
        friendService.removeFriend(userId, friendId);
    }
}
