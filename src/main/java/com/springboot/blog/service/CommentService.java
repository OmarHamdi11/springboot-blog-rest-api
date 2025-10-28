package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.CommentResponse;

import java.util.List;
import java.util.Map;

public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto);

    CommentResponse getCommentsByPostId(long postId, int pageNo, int pageSize, String sortBy, String sortDir);

    CommentDto getCommentById(long postId, long commentId);

    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);

    CommentDto patchComment(long postId, long commentId, Map<String,Object> patchPayload);

    void deleteCommentById(long postId, long commentId);

}
