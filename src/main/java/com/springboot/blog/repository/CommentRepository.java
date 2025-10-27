package com.springboot.blog.repository;

import com.springboot.blog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Page<Comment> findByPostId(long postId, Pageable pageable);
}
