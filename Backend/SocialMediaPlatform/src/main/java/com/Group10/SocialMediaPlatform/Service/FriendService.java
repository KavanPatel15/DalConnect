package com.Group10.SocialMediaPlatform.Service;

import com.Group10.SocialMediaPlatform.Repository.UserRepository;
import com.Group10.SocialMediaPlatform.model.Friend;
import com.Group10.SocialMediaPlatform.model.FriendId;
import com.Group10.SocialMediaPlatform.Repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService {
    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    // Adds a new friend relationship and saves it to the repository
    public Friend addFriend(Friend friend) {
        validateFriendId(friend);
        validateUserAndFriend(friend);
        return friendRepository.save(friend);
    }

    // Validates that the FriendId is not null
    private void validateFriendId(Friend friend) {
        validateNotNull(friend.getId(), "FriendId cannot be null");
        validateNotNull(friend.getId().getUserId(), "FriendId's UserId cannot be null");
        validateNotNull(friend.getId().getFriendId(), "FriendId's FriendId cannot be null");
    }

    // Validates that both the user and friend are not null
    private void validateUserAndFriend(Friend friend) {
        validateNotNull(friend.getUser(), "User cannot be null");
        validateNotNull(friend.getFriend(), "Friend cannot be null");
    }

    // Checks if an object is not null and throws an exception with a message if it is null
    private void validateNotNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    // Retrieves all friends of a user by userId
    public List<Friend> getFriendsByUserId(Integer userId) {
        return friendRepository.findByUser_UserId(userId);
    }

    // Removes a friend relationship in both directions
    public void removeFriend(Integer userId, Integer friendId) {
        friendRepository.deleteById(new FriendId(userId, friendId));
        friendRepository.deleteById(new FriendId(friendId, userId));
    }

    // Retrieves all friends of a user by userId, including both user and friend relationships
    public List<Friend> getAllFriendsForUser(Integer userId) {
        List<Friend> friends = new ArrayList<>(friendRepository.findByUser_UserId(userId));
        friends.addAll(friendRepository.findByFriend_UserId(userId));
        return friends;
    }
}
