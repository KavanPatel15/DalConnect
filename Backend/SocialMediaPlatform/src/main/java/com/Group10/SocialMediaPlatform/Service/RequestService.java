package com.Group10.SocialMediaPlatform.Service;

import com.Group10.SocialMediaPlatform.model.Friend;
import com.Group10.SocialMediaPlatform.model.FriendId;
import com.Group10.SocialMediaPlatform.model.Request;
import com.Group10.SocialMediaPlatform.Repository.FriendRepository;
import com.Group10.SocialMediaPlatform.Repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private FriendRepository friendRepository;

    /**
     * Sends a new friend request and sets its status to "pending".
     *
     * @param request the Request entity to be sent
     * @return the saved Request entity with status "pending"
     */
    public Request sendRequest(Request request) {
        request.setStatus("pending"); // Set the request status to "pending"
        return requestRepository.save(request); // Save and return the request
    }

    /**
     * Retrieves all friend requests received by a specific user.
     *
     * @param userId the ID of the user receiving the requests
     * @return a list of Request entities for the specified user
     */
    public List<Request> getRequestsByUserId(Integer userId) {
        return requestRepository.findByReceiverUserId(userId); // Find requests where the receiver is the specified user
    }

    /**
     * Handles a friend request by either accepting or rejecting it.
     *
     * @param userId the ID of the user handling the request
     * @param requestId the ID of the request to be handled
     * @param action the action to perform on the request ("accepted" or "rejected")
     * @throws IllegalArgumentException if the request does not belong to the user or if the request is not found
     */
    public void handleRequest(Integer userId, Integer requestId, String action) {
        Optional<Request> requestOpt = requestRepository.findById(requestId);
        if (requestOpt.isPresent()) {
            Request request = requestOpt.get();
            // Validate that the request belongs to the user handling it
            if (!request.getReceiver().getUserId().equals(userId)) {
                throw new IllegalArgumentException("Request does not belong to this user.");
            }
            request.setStatus(action); // Set the request status to the specified action

            if ("accepted".equals(action)) {
                // Create Friend entities for both sender and receiver
                Friend receiver = new Friend(new FriendId(request.getSender().getUserId(), request.getReceiver().getUserId()), "accepted", LocalDateTime.now(), request.getSender(), request.getReceiver());
                Friend sender = new Friend(new FriendId(request.getReceiver().getUserId(), request.getSender().getUserId()), "accepted", LocalDateTime.now(), request.getReceiver(), request.getSender());
                friendRepository.save(receiver); // Save receiver as a friend
                friendRepository.save(sender); // Save sender as a friend
            }

            requestRepository.delete(request); // Delete the request after handling
        } else {
            throw new IllegalArgumentException("Request not found."); // Handle case where the request does not exist
        }
    }
}
