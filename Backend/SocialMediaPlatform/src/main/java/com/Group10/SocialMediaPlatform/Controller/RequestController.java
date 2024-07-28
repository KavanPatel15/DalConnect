package com.Group10.SocialMediaPlatform.Controller;

import com.Group10.SocialMediaPlatform.model.Request;
import com.Group10.SocialMediaPlatform.Service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    // Endpoint to send a new request
    @PostMapping
    public Request sendRequest(@RequestBody Request request) {
        return requestService.sendRequest(request);
    }

    // Endpoint to get requests by user ID
    @GetMapping("/{userId}")
    public List<Request> getRequestsByUserId(@PathVariable Integer userId) {
        return requestService.getRequestsByUserId(userId);
    }

    // Endpoint to handle a request (accept/reject) by user ID and request ID
    @PatchMapping("/{userId}/{requestId}")
    public void handleRequest(@PathVariable Integer userId, @PathVariable Integer requestId, @RequestParam String action) {
        requestService.handleRequest(userId, requestId, action);
    }
}
