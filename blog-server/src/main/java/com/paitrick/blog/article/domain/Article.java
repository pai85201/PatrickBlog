package com.paitrick.blog.article.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
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
}

