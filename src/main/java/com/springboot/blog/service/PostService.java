package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

import java.util.List;
import java.util.Map;


public interface PostService {

    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    PostDto patchPost(long id, Map<String,Object> patchPayload);

    void deletePostById(long id);

    List<PostDto> getPostsByCategoryId(Long categoryId);
}
