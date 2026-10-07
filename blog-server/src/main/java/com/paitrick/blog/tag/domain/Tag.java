package com.paitrick.blog.tag.domain; import java.time.LocalDateTime; public record Tag(Long id,String name,String slug,LocalDateTime createdAt,LocalDateTime updatedAt){}
