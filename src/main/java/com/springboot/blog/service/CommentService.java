package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.CommentResponse;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto);

    CommentResponse getCommentsByPostId(long postId, int pageNo, int pageSize, String sortBy, String sortDir);

    CommentDto getCommentById(long postId, long commentId);

    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);

    void deleteCommentById(long postId, long commentId);

}
