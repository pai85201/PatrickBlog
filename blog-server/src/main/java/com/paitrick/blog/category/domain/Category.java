package com.paitrick.blog.category.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
  private Long id;
  private String name;
  private String slug;
  private String description;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}

