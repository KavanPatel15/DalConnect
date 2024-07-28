package com.Group10.SocialMediaPlatform.Service;

import com.Group10.SocialMediaPlatform.model.Post;
import com.Group10.SocialMediaPlatform.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    /**
     * Creates and saves a new post to the database.
     *
     * @param post the Post entity to be saved
     * @return the saved Post entity
     */
    @Transactional
    public Post createPost(Post post) {
        validatePost(post); // Validate the post before saving
        return postRepository.save(post); // Save and return the post
    }

    /**
     * Retrieves a post by its ID.
     *
     * @param postId the ID of the post to retrieve
     * @return the Post entity with the specified ID
     * @throws IllegalArgumentException if no post is found with the given ID
     */
    @Transactional(readOnly = true)
    public Post getPostById(Integer postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found")); // Handle missing post
    }

    /**
     * Retrieves all posts from the database.
     *
     * @return a list of all Post entities
     */
    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAll(); // Return the list of all posts
    }

    /**
     * Deletes a post by its ID.
     *
     * @param postId the ID of the post to be deleted
     * @throws IllegalArgumentException if no post is found with the given ID
     */
    @Transactional
    public void deletePost(Integer postId) {
        if (!postRepository.existsById(postId)) {
            throw new IllegalArgumentException("Post not found"); // Handle missing post
        }
        postRepository.deleteById(postId); // Delete the post
    }

    /**
     * Validates the post before saving.
     *
     * @param post the Post entity to validate
     * @throws IllegalArgumentException if the post is invalid
     */
    private void validatePost(Post post) {
        // Implement validation logic
        if (post.getContent() == null || post.getContent().isEmpty()) {
            throw new IllegalArgumentException("Post content cannot be null or empty"); // Ensure post content is valid
        }
    }
}
