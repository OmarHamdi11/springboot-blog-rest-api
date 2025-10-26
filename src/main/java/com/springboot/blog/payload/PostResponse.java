package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(
        description = "Post Response - contains paginated list of blog posts with pagination metadata"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    @Schema(
            description = "Current page number (zero-based)",
            example = "0"
    )
    private int pageNo;

    @Schema(
            description = "Number of items per page",
            example = "10"
    )
    private int pageSize;


    @Schema(
            description = "Total number of posts available across all pages",
            example = "100"
    )
    private long totalElements;

    @Schema(
            description = "Total number of pages available",
            example = "10"
    )
    private int totalPages;

    @Schema(
            description = "Indicates whether this is the last page",
            example = "false"
    )
    private boolean last;

    @Schema(
            description = "List of posts for the current page"
    )
    private List<PostDto> content;
}
