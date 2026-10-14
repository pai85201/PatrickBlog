package com.paitrick.blog.tag.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TagRequest {
  @NotBlank private String name;
  @NotBlank private String slug;
}

