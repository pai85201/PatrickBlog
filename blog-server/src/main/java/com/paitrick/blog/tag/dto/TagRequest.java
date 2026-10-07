package com.paitrick.blog.tag.dto; import jakarta.validation.constraints.NotBlank; public record TagRequest(@NotBlank String name,@NotBlank String slug){}
