package com.blog.rest.service;

import com.blog.rest.dto.CommentDTO;

import java.util.List;

public interface ICommentService {

    public CommentDTO createComment(long postId, CommentDTO commentDTO);
    public List<CommentDTO> getCommentsByPostId(long postId);
    public CommentDTO getCommentById(long postId, long commentId);
    public CommentDTO updateComment(Long postId, long commentId, CommentDTO commentDTO);
    public void deleteComment(Long postId, Long commentId);

}
