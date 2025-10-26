package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description = "Comment Data Transfer Object - represents a comment on a blog post"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    @Schema(
            description = "Unique identifier of the comment",
            example = "1"
    )
    private long id;

    @Schema(
            description = "Name of the person commenting",
            example = "Omar Ellafy"
    )
    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    @Schema(
            description = "Email address of the commenter",
            example = "omar.ellafy@example.com"
    )
    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;

    @Schema(
            description = "Content of the comment (minimum 10 characters)",
            example = "This is a great blog post! I really enjoyed reading it and learned a lot."
    )
    @NotEmpty
    @Size(min = 10,message = "Body must be more than 10 characters")
    private String body;

}
