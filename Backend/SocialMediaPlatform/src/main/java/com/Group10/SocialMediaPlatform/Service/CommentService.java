package com.Group10.SocialMediaPlatform.Service;

import com.Group10.SocialMediaPlatform.model.Comment;
import com.Group10.SocialMediaPlatform.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    // Creates a new comment and saves it to the database
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    // Retrieves a comment by its ID
    public Comment getCommentById(Integer commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    // Retrieves all comments
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    // Deletes a comment by its ID
    public void deleteComment(Integer commentId) {
        commentRepository.deleteById(commentId);
    }
}
