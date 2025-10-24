package com.springboot.blog.payload;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private long id;

    @NotEmpty(message = "post title must be not empty")
    @Size(min = 2,message = "post title must be more than 2 characters")
    private String title;

    @NotEmpty(message = "post description must be not empty")
    @Size(min = 10, message = "post description should be more than 10 chars")
    private String description;

    @NotEmpty(message = "post content must be not empty")
    private String content;

    private Set<CommentDto> comments;

    private Long categoryId;
}
