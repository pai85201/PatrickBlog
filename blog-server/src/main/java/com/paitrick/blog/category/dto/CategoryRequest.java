package com.paitrick.blog.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryRequest {
  @NotBlank private String name;
  @NotBlank private String slug;
  private String description;
}

