package com.blog.rest.controller;

import com.blog.rest.dto.PaginationDetails;
import com.blog.rest.dto.PostDTO;
import com.blog.rest.service.PostServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostServiceImpl postService;

    public PostController(PostServiceImpl postService){
        this.postService = postService;
    }

    // List by pagination:
    @GetMapping
    public PaginationDetails getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageLength,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy
    ){
        return postService.getAllPosts(pageNumber, pageLength, sortBy);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @PostMapping
    public ResponseEntity<PostDTO> savePost(@Valid @RequestBody PostDTO postDTO){
        return new ResponseEntity<>(
                postService.createPost(postDTO),
                HttpStatus.CREATED);
    }
    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable long postId, @RequestBody PostDTO postDTO){
       return new ResponseEntity<>(
               postService.updatePost(postId,postDTO),
               HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable long postId){
        postService.deletePost(postId);
        return new ResponseEntity<>(
                "Post deleted successfully",
                HttpStatus.OK);
    }
}
