package com.paitrick.blog.tag.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
  private Long id;
  private String name;
  private String slug;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}

