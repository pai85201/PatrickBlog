package com.paitrick.blog.article.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleRequest {
  @NotBlank private String title;
  @NotBlank private String slug;
  private String summary;
  @NotBlank private String content;
  private String coverUrl;
  @Pattern(regexp = "DRAFT|PUBLISHED|ARCHIVED") private String status;
  private Long categoryId;
  private List<Long> tagIds;
}

