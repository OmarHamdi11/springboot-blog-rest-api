package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Schema(
        description = "Comment Response - contains paginated list of blog comments with pagination metadata"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

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
            description = "Total number of comments available across all pages",
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
            description = "List of comments for the current page"
    )
    private List<CommentDto> content;

}
