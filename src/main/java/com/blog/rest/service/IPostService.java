package com.blog.rest.service;

import com.blog.rest.dto.PostDTO;
import com.blog.rest.dto.PaginationDetails;

public interface IPostService {

    public PostDTO createPost(PostDTO postDTO);
    public PaginationDetails getAllPosts(int pageNumber, int pageLength, String sortBy);
    public PostDTO getPostById(long idPost);
    public PostDTO updatePost(long idPost, PostDTO postDTO);
    public void deletePost(long idPost);

}
