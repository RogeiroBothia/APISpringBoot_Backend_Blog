package com.blog.rest.service;

import com.blog.rest.dto.CommentDTO;
import com.blog.rest.entities.Comment;
import com.blog.rest.entities.Post;
import com.blog.rest.exceptions.BlogException;
import com.blog.rest.exceptions.ResourceNotFoundException;
import com.blog.rest.repository.CommentRepository;
import com.blog.rest.repository.PostRepository;
import com.blog.rest.utilities.UtilityModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements ICommentService{

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UtilityModelMapper mapper;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, UtilityModelMapper mapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }
    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        Comment comment = mapper.mapToComment(commentDTO);
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty())
            throw new ResourceNotFoundException("Post "+postId+" not found");

        comment.setPost(post.get());
        Comment saveComment = commentRepository.save(comment);
        return mapper.mapToCommentDTO(saveComment);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream()
                .map(comment -> mapper.mapToCommentDTO(comment))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(long postId, long commentId) {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty())
            throw new ResourceNotFoundException("Post "+postId+" not found");

        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isEmpty())
            throw new ResourceNotFoundException("The comment "+commentId+" is NOT found");

        if(!comment.get().getPost().getId().equals(post.get().getId()))
            throw new BlogException("The Comment does not belong to the Post");

        return mapper.mapToCommentDTO(comment.get());
    }

    @Override
    public CommentDTO updateComment(Long postId, long commentId, CommentDTO commentDTO) {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty())
            throw new ResourceNotFoundException("Post "+postId+" not found");

        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isEmpty())
            throw new ResourceNotFoundException("The comment "+commentId+" is NOT found");

        if(!comment.get().getPost().getId().equals(post.get().getId()))
            throw new BlogException("The Comment does not belong to the Post");

        Comment commentUpdate = comment.get();
        commentUpdate.setName(commentDTO.getName());
        commentUpdate.setEmail(commentDTO.getEmail());
        commentUpdate.setCommentContent(commentDTO.getCommentContent());

        commentRepository.save(commentUpdate);

        return mapper.mapToCommentDTO(commentUpdate);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty())
            throw new ResourceNotFoundException("Post "+postId+" not found");

        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isEmpty())
            throw new ResourceNotFoundException("The comment "+commentId+" is NOT found");

        if(!comment.get().getPost().getId().equals(post.get().getId()))
            throw new BlogException("The Comment does not belong to the Post");

        commentRepository.delete(comment.get());
    }
}
