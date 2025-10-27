package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/posts")
@Tag(name = "Post Management", description = "APIs for managing blog posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create blog post rest api
    @Operation(
            summary = "Create new post",
            description = "Creates a new blog post with the provided content, title, and description. Only accessible by ADMIN users."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid post data provided"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required")
    })
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new  ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // get all posts rest api
    @Operation(
            summary = "Get all posts",
            description = "Retrieves a paginated list of all blog posts with optional sorting. Supports pagination and sorting parameters."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Posts retrieved successfully with pagination information"
    )
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo" , defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize" , defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return new ResponseEntity<>(postService.getAllPosts(pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    // get post by id
    @Operation(
            summary = "Get post by ID",
            description = "Retrieves a specific blog post by its unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post found and returned successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @GetMapping("{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // update post
    @Operation(
            summary = "Update post",
            description = "Updates an existing blog post with new content. Only accessible by ADMIN users."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid post data provided"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id){
        return new ResponseEntity<>(postService.updatePost(postDto,id),HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<PostDto> patchPost(
            @PathVariable(name = "id") long id,
            @RequestBody Map<String,Object> patchPayload
    ){
        return ResponseEntity.ok(postService.patchPost(id,patchPayload));
    }


    // delete post by id
    @Operation(
            summary = "Delete post",
            description = "Deletes a specific blog post by its ID. Only accessible by ADMIN users."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id") long id){
        postService.deletePostById(id);
        return ResponseEntity.ok("Post deleted successfully");
    }

    // Build Get Post By Category REST API
    // http://localhost:8080/api/posts/category/id
    @Operation(
            summary = "Get posts by category",
            description = "Retrieves all blog posts that belong to a specific category"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategoryId(@PathVariable(name = "id") Long categoryId){
         return ResponseEntity.ok(postService.getPostsByCategoryId(categoryId));
    }

}
