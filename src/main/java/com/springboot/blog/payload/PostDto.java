package com.springboot.blog.payload;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Schema(
        description = "Post Data Transfer Object - represents a blog post with its details"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    @Schema(
            description = "Unique identifier of the blog post",
            example = "1"
    )
    private long id;

    @Schema(
            description = "Title of the blog post (minimum 2 characters)",
            example = "Introduction to Spring Boot"
    )
    @NotEmpty(message = "post title must be not empty")
    @Size(min = 2,message = "post title must be more than 2 characters")
    private String title;

    @Schema(
            description = "Brief description or summary of the blog post (minimum 10 characters)",
            example = "A comprehensive guide to building REST APIs with Spring Boot framework"
    )
    @NotEmpty(message = "post description must be not empty")
    @Size(min = 10, message = "post description should be more than 10 chars")
    private String description;

    @Schema(
            description = "Full content of the blog post",
            example = "Spring Boot is a powerful framework that simplifies the development of Java applications. In this post, we will explore its key features..."
    )
    @NotEmpty(message = "post content must be not empty")
    private String content;

    @Schema(
            description = "Set of comments associated with this blog post"
    )
    private Set<CommentDto> comments;

    @Schema(
            description = "ID of the category this post belongs to",
            example = "1"
    )
    private Long categoryId;
}
