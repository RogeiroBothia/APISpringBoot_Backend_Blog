package com.blog.rest.controller;

import com.blog.rest.dto.CommentDTO;
import com.blog.rest.entities.Comment;
import com.blog.rest.service.CommentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private final CommentServiceImpl commentService;

    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTO> getCommentsById(@PathVariable long postId){
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable long postId, @PathVariable long commentId){
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable long postId, @Valid @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(
                commentService.createComment(postId, commentDTO),
                HttpStatus.CREATED);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long postId, @PathVariable long commentId, @Valid @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(
                commentService.updateComment(postId, commentId, commentDTO),
                HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted successfully",HttpStatus.OK);
    }
}
