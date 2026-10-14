package com.paitrick.blog.article.controller;

import com.paitrick.blog.article.dto.ArticleRequest;
import com.paitrick.blog.article.dto.ArticleResponse;
import com.paitrick.blog.article.service.ArticleService;
import com.paitrick.blog.common.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ArticleController {
  private final ArticleService articleService;

  public ArticleController(ArticleService articleService) {
    this.articleService = articleService;
  }

  @GetMapping("/articles")
  ApiResponse<Map<String, Object>> list(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size) {
    return ApiResponse.ok(articleService.published(Math.max(1, page), Math.min(100, Math.max(1, size))));
  }

  @GetMapping("/articles/{slug}")
  ApiResponse<ArticleResponse> detail(@PathVariable String slug) {
    return ApiResponse.ok(articleService.detail(slug));
  }

  @GetMapping("/admin/articles")
  ApiResponse<Map<String, Object>> adminList(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "20") int size) {
    return ApiResponse.ok(articleService.adminList(page, size));
  }

  @GetMapping("/admin/articles/{id}")
  ApiResponse<ArticleResponse> get(@PathVariable long id) {
    return ApiResponse.ok(articleService.byId(id));
  }

  @PostMapping("/admin/articles")
  ApiResponse<ArticleResponse> create(@Valid @RequestBody ArticleRequest request) {
    return ApiResponse.ok(articleService.create(request));
  }

  @PutMapping("/admin/articles/{id}")
  ApiResponse<ArticleResponse> update(
      @PathVariable long id, @Valid @RequestBody ArticleRequest request) {
    return ApiResponse.ok(articleService.update(id, request));
  }

  @PostMapping("/admin/articles/{id}/publish")
  ApiResponse<ArticleResponse> publish(@PathVariable long id) {
    return ApiResponse.ok(articleService.publish(id));
  }

  @DeleteMapping("/admin/articles/{id}")
  ApiResponse<Void> delete(@PathVariable long id) {
    articleService.delete(id);
    return ApiResponse.ok(null);
  }
}
