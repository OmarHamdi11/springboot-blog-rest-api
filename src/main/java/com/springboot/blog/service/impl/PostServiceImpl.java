package com.springboot.blog.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           ModelMapper modelMapper,
                           CategoryRepository categoryRepository,
                           ObjectMapper objectMapper
    ) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId())
        );

        Post post = mapToEntity(postDto);
        post.setId(null);
        post.setCategory(category);

        // Convert DTO into Entity
        Post post1 = postRepository.save(post);

        // Convert Entity into DTO
        return mapToDTO(post1);

    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // create pageable instance
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);

        // Find all Posts
        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> postList = posts.getContent();

        // MapToDTO and return
        List<PostDto> content = postList.stream().map(this::mapToDTO).toList();

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());

        return postResponse;



    }

    @Override
    public PostDto getPostById(long id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));

        PostDto postDto;

        postDto = mapToDTO(post);

        return postDto;

    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId())
        );

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);

    }

    @Override
    public PostDto patchPost(long id, Map<String, Object> patchPayload) {

        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));

        PostDto postDto = mapToDTO(post);

        PostDto patchedPostDto = apply(patchPayload,postDto);

        // Map back to entity and save
        post.setTitle(patchedPostDto.getTitle());
        post.setDescription(patchedPostDto.getDescription());
        post.setContent(patchedPostDto.getContent());

        if (patchedPostDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(patchedPostDto.getCategoryId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Category", "id", patchedPostDto.getCategoryId())
                    );
            post.setCategory(category);
        }

        Post savedPost = postRepository.save(post);

        return mapToDTO(savedPost);
    }

    @Override
    public void deletePostById(long id) {

        postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));

        postRepository.deleteById(id);

    }

    @Override
    public List<PostDto> getPostsByCategoryId(Long categoryId) {

        categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", categoryId)
        );

        List<Post> posts =  postRepository.findByCategoryId(categoryId);

        return posts.stream().map(this::mapToDTO).toList();
    }


    private PostDto mapToDTO(Post post){
        return modelMapper.map(post, PostDto.class);
    }

    private Post mapToEntity(PostDto postDto){
        return modelMapper.map(postDto, Post.class);
    }

    private PostDto apply(Map<String, Object> patchPayload, PostDto postDto) {

        // convert post object to json object node using ObjectMapper
        ObjectNode postNode = objectMapper.convertValue(postDto, ObjectNode.class);

        // convert patchPayload to json object node using ObjectMapper
        ObjectNode patchNode = objectMapper.convertValue(patchPayload , ObjectNode.class);

        // merge patch update into post node
        postNode.setAll(patchNode);

        return objectMapper.convertValue(postNode , PostDto.class);

    }

}
