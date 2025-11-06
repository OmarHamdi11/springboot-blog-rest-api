package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.CommentResponse;
import com.springboot.blog.service.CommentService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
@Tag(name = "Comment Management", description = "APIs for managing comments on blog posts")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @Operation(
            summary = "Create new comment",
            description = "Creates a new comment for a specific blog post with the provided comment details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid comment data provided"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @PostMapping
    public ResponseEntity<CommentDto> createComment(
        @PathVariable(value = "postId") long postId,@Valid @RequestBody CommentDto commentDto
    ){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all comments for a post",
            description = "Retrieves all comments associated with a specific blog post ID, Retrieves a paginated list of all blog comments with optional sorting. Supports pagination and sorting parameters."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @GetMapping
    public ResponseEntity<CommentResponse> getCommentsByPostId(
            @PathVariable(value = "postId") long postId,
            @RequestParam(value = "pageNo" , defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize" , defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId,pageNo,pageSize,sortBy,sortDir)) ;
    }

    @Operation(
            summary = "Get comment by ID",
            description = "Retrieves a specific comment by its ID for a given post"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment found and returned successfully"),
            @ApiResponse(responseCode = "404", description = "Comment or Post not found")
    })
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") long postId,
                                                     @PathVariable(value = "commentId") long commentId
    ){
        return new ResponseEntity<>(commentService.getCommentById(postId,commentId),HttpStatus.OK);
    }

    @Operation(
            summary = "Update comment",
            description = "Updates an existing comment with new data for a specific post"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid comment data provided"),
            @ApiResponse(responseCode = "404", description = "Comment or Post not found")
    })
    @PutMapping("{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") long postId,
                                                    @PathVariable(value = "commentId") long commentId,
                                                    @Valid @RequestBody CommentDto commentDto
    ){
        return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDto),HttpStatus.OK);
    }

    @Operation(
            summary = "Patch comment",
            description = "Patch an existing comment with new data for a specific post"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid comment data provided"),
            @ApiResponse(responseCode = "404", description = "Comment or Post not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("{commentId}")
    public ResponseEntity<CommentDto> patchComment(
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "commentId") long commentId,
            @RequestBody Map<String,Object> patchPayload
    ){
        return ResponseEntity.ok(commentService.patchComment(postId, commentId, patchPayload));
    }



    @Operation(
            summary = "Delete comment",
            description = "Deletes a specific comment by its ID from a given post"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Comment or Post not found")
    })
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") long postId,
                                                @PathVariable(value = "commentId") long commentId
    ){
        commentService.deleteCommentById(postId, commentId);
        return new ResponseEntity<>("Comment Deleted Successfully", HttpStatus.OK);
    }

}
