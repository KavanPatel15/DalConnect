package com.Group10.SocialMediaPlatform.Controller;

import com.Group10.SocialMediaPlatform.model.Post;
import com.Group10.SocialMediaPlatform.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // Endpoint to create a new post
    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    // Endpoint to get a post by its ID
    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable Integer postId) {
        return postService.getPostById(postId);
    }

    // Endpoint to get all posts
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    // Endpoint to delete a post by its ID
    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Integer postId) {
        postService.deletePost(postId);
    }
}
