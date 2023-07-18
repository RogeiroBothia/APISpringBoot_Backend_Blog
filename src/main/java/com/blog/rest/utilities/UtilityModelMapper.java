package com.blog.rest.utilities;

import com.blog.rest.dto.CommentDTO;
import com.blog.rest.dto.PostDTO;
import com.blog.rest.entities.Comment;
import com.blog.rest.entities.Post;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UtilityModelMapper {

    private final ModelMapper modelMapper;

    private UtilityModelMapper(){
        this.modelMapper = new ModelMapper();
    }

    // Build singleton
    private static UtilityModelMapper instance;
    public static UtilityModelMapper getInstance(){
        if(instance == null)
            instance = new UtilityModelMapper();

        return instance;
    }

    // implementation for mapper methods

    // Entity Post:
    public Post mapToPost(PostDTO postDTO){
        return modelMapper.map(postDTO, Post.class);
    }
    public PostDTO mapToPostDTO(Post post){
        PostDTO postDTO = modelMapper.map(post, PostDTO.class);
        postDTO.setCommentSet(post.getComments());

        return postDTO;
    }

    // Entity Comment:
    public Comment mapToComment(CommentDTO commentDTO){
        return modelMapper.map(commentDTO, Comment.class);
    }
    public CommentDTO mapToCommentDTO(Comment comment){
        return modelMapper.map(comment, CommentDTO.class);
    }

}
