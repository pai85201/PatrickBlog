package com.paitrick.blog.category.controller;

import com.paitrick.blog.category.domain.Category;
import com.paitrick.blog.category.dto.CategoryRequest;
import com.paitrick.blog.category.service.CategoryService;
import com.paitrick.blog.common.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CategoryController {
  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping("/categories")
  ApiResponse<List<Category>> all() {
    return ApiResponse.ok(categoryService.all());
  }

  @PostMapping("/admin/categories")
  ApiResponse<Category> create(@Valid @RequestBody CategoryRequest request) {
    return ApiResponse.ok(categoryService.create(request));
  }

  @PutMapping("/admin/categories/{id}")
  ApiResponse<Category> update(@PathVariable long id, @Valid @RequestBody CategoryRequest request) {
    return ApiResponse.ok(categoryService.update(id, request));
  }

  @DeleteMapping("/admin/categories/{id}")
  ApiResponse<Void> delete(@PathVariable long id) {
    categoryService.delete(id);
    return ApiResponse.ok(null);
  }
}
