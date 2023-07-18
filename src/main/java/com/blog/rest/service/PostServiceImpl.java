package com.blog.rest.service;

import com.blog.rest.dto.PostDTO;
import com.blog.rest.dto.PaginationDetails;
import com.blog.rest.entities.Post;
import com.blog.rest.exceptions.ResourceNotFoundException;
import com.blog.rest.repository.PostRepository;
import com.blog.rest.utilities.UtilityModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements IPostService{
    private final PostRepository postRepository;
    private final UtilityModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, UtilityModelMapper mapper){
        this.postRepository = postRepository;
        this.mapper = mapper;
    }
    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post newPost = postRepository.save(mapper.mapToPost(postDTO));
        return mapper.mapToPostDTO(newPost);
    }

    @Override
    public PaginationDetails getAllPosts(int pageNumber, int pageLength, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageLength, Sort.by(sortBy));
        Page<Post> pagePosts = postRepository.findAll(pageable);

        List<Post> listPosts= pagePosts.getContent();
        List<PostDTO> content = listPosts
                .stream()
                .map(post -> mapper.mapToPostDTO(post))
                .collect(Collectors.toList());

        PaginationDetails paginationDetails = new PaginationDetails();
        paginationDetails.setContent(content);
        paginationDetails.setPageNumber(pagePosts.getNumber());
        paginationDetails.setPageLength(pagePosts.getSize());
        paginationDetails.setTotalItems(pagePosts.getTotalElements());
        paginationDetails.setTotalPage(pagePosts.getTotalPages());
        paginationDetails.setLast(pagePosts.isLast());

        return paginationDetails;
    }

    @Override
    public PostDTO getPostById(long idPost) throws ResourceNotFoundException {
        Optional<Post> getPost = postRepository.findById(idPost);
        if(getPost.isEmpty())
            throw new ResourceNotFoundException("Post "+idPost+" not found");

        return mapper.mapToPostDTO(getPost.get());
    }

    @Override
    public PostDTO updatePost(long idPost, PostDTO postDTO) throws ResourceNotFoundException {
        Optional<Post> getPost = postRepository.findById(idPost);
        if(getPost.isEmpty())
            throw new ResourceNotFoundException("Post "+idPost+" not found");

        Post updatedPost = getPost.get();
        updatedPost.setTitle(postDTO.getTitle());
        updatedPost.setDescription(postDTO.getDescription());
        updatedPost.setContent(postDTO.getContent());

        postRepository.save(updatedPost);
        return mapper.mapToPostDTO(updatedPost);
    }

    @Override
    public void deletePost(long idPost) throws ResourceNotFoundException{
        Optional<Post> getPost = postRepository.findById(idPost);
        if(getPost.isEmpty())
            throw new ResourceNotFoundException("Post "+idPost+" not found");

        postRepository.delete(getPost.get());
    }
}
