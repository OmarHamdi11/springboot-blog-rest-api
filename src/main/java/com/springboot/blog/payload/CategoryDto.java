package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        description = "Category Data Transfer Object - represents a blog post category"
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    @Schema(
            description = "Unique identifier of the category",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Name of the category",
            example = "Technology"
    )
    private String name;

    @Schema(
            description = "Detailed description of the category",
            example = "Posts related to technology, software development, and programming"
    )
    private String description;
}
