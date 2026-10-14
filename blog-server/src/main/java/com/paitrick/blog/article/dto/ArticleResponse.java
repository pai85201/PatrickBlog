package com.paitrick.blog.article.dto;

import com.paitrick.blog.tag.domain.Tag;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {
  private Long id;
  private String title;
  private String slug;
  private String summary;
  private String content;
  private String coverUrl;
  private String status;
  private Long categoryId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime publishedAt;
  private List<Tag> tags;
}

