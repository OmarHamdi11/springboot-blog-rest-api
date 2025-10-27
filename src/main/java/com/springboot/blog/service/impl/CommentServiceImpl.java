package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.CommentResponse;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);
        comment.setId(null);

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));

        comment.setPost(post);

        return mapToDTO(commentRepository.save(comment));
    }

    @Override
    public CommentResponse getCommentsByPostId(long postId, int pageNo, int pageSize, String sortBy, String sortDir) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // create pageable instance
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);

        // Find all Posts
        Page<Comment> commentsPage = commentRepository.findByPostId(postId, pageable);

        List<Comment> commentsList = commentsPage.getContent();

        // Map to DTO and return
        List<CommentDto> commentDtoList = commentsList.stream().map(this::mapToDTO).toList();

        CommentResponse commentResponse = new CommentResponse();

        commentResponse.setPageNo(commentsPage.getNumber());
        commentResponse.setPageSize(commentsPage.getSize());
        commentResponse.setTotalPages(commentsPage.getTotalPages());
        commentResponse.setTotalElements(commentsPage.getTotalElements());
        commentResponse.setLast(commentsPage.isLast());
        commentResponse.setContent(commentDtoList);

        return commentResponse;
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {

        Comment comment = validateCommentWithPost(postId,commentId);


        return mapToDTO(comment);

    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {

        Comment comment = validateCommentWithPost(postId,commentId);

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);

        return mapToDTO(updatedComment);
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {

        Comment comment = validateCommentWithPost(postId,commentId);

        commentRepository.deleteById(commentId);
    }

    private Comment validateCommentWithPost(long postId, long commentId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }

        return comment;
    }

    private CommentDto mapToDTO(Comment comment){
        return modelMapper.map(comment, CommentDto.class);
    }

    private Comment mapToEntity(CommentDto commentDto){
        return modelMapper.map(commentDto, Comment.class);
    }


}