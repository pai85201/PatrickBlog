package com.paitrick.blog.tag.controller;
import com.paitrick.blog.tag.domain.Tag;
import com.paitrick.blog.tag.dto.TagRequest;
import com.paitrick.blog.tag.service.TagService;
import com.paitrick.blog.common.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api") public class TagController{
  private final TagService s;
  public TagController(TagService s){
    this.s=s;
  }
  @GetMapping("/tags") ApiResponse<List<Tag>> all(){
    return ApiResponse.ok(s.all());
  }
  @PostMapping("/admin/tags") ApiResponse<Tag> create(@Valid @RequestBody TagRequest r){
    return ApiResponse.ok(s.create(r));
  }
  @PutMapping("/admin/tags/{id}") ApiResponse<Tag> update(@PathVariable long id,@Valid @RequestBody TagRequest r){
    return ApiResponse.ok(s.update(id,r));
  }
  @DeleteMapping("/admin/tags/{id}") ApiResponse<Void> delete(@PathVariable long id){
    s.delete(id);
    return ApiResponse.ok(null);
  }
}

