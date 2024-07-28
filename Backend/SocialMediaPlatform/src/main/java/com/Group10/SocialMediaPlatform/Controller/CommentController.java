package com.Group10.SocialMediaPlatform.Controller;

import com.Group10.SocialMediaPlatform.model.Comment;
import com.Group10.SocialMediaPlatform.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Endpoint to create a new comment
    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }

    // Endpoint to get a comment by its ID
    @GetMapping("/{commentId}")
    public Comment getCommentById(@PathVariable Integer commentId) {
        return commentService.getCommentById(commentId);
    }

    // Endpoint to get all comments
    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    // Endpoint to delete a comment by its ID
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
    }
}
