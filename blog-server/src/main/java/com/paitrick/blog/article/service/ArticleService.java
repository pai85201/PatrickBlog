package com.paitrick.blog.article.service;

import com.paitrick.blog.article.domain.Article;
import com.paitrick.blog.article.dto.ArticleRequest;
import com.paitrick.blog.article.dto.ArticleResponse;
import com.paitrick.blog.article.repository.ArticleMapper;
import com.paitrick.blog.common.exception.BusinessException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleService {
  private final ArticleMapper articleMapper;

  public ArticleService(ArticleMapper articleMapper) {
    this.articleMapper = articleMapper;
  }

  public Map<String, Object> published(int page, int size) {
    var articles = articleMapper.published(size, (page - 1) * size).stream().map(this::toResponse).toList();
    return Map.of("items", articles, "page", page, "size", size, "total", articleMapper.publishedCount());
  }

  public ArticleResponse detail(String slug) {
    return toResponse(requirePublishedArticle(slug));
  }

  public Map<String, Object> adminList(int page, int size) {
    var articles = articleMapper.adminList(size, (page - 1) * size).stream().map(this::toResponse).toList();
    return Map.of("items", articles, "page", page, "size", size, "total", articleMapper.count());
  }

  public ArticleResponse byId(long id) {
    return toResponse(requireArticle(id));
  }

  @Transactional
  public ArticleResponse create(ArticleRequest request) {
    Article article = toEntity(null, request);
    articleMapper.insert(article);
    replaceTags(article.getId(), request.getTagIds());
    return byId(article.getId());
  }

  @Transactional
  public ArticleResponse update(long id, ArticleRequest request) {
    requireArticle(id);
    articleMapper.update(toEntity(id, request));
    replaceTags(id, request.getTagIds());
    return byId(id);
  }

  @Transactional
  public ArticleResponse publish(long id) {
    Article article = requireArticle(id);
    Article publishedArticle = new Article(
        id, article.getTitle(), article.getSlug(), article.getSummary(), article.getContent(),
        article.getCoverUrl(), "PUBLISHED", article.getCategoryId(), null, null,
        article.getPublishedAt() == null ? LocalDateTime.now() : article.getPublishedAt());
    articleMapper.update(publishedArticle);
    return byId(id);
  }

  @Transactional
  public void delete(long id) {
    articleMapper.clearTags(id);
    if (articleMapper.delete(id) == 0) {
      throw new BusinessException("article not found");
    }
  }

  private ArticleResponse toResponse(Article article) {
    return new ArticleResponse(
        article.getId(), article.getTitle(), article.getSlug(), article.getSummary(),
        article.getContent(), article.getCoverUrl(), article.getStatus(), article.getCategoryId(),
        article.getCreatedAt(), article.getUpdatedAt(), article.getPublishedAt(),
        articleMapper.tags(article.getId()));
  }

  private Article requirePublishedArticle(String slug) {
    Article article = articleMapper.publishedBySlug(slug);
    if (article == null) {
      throw new BusinessException("article not found");
    }
    return article;
  }

  private Article requireArticle(long id) {
    Article article = articleMapper.byId(id);
    if (article == null) {
      throw new BusinessException("article not found");
    }
    return article;
  }

  private Article toEntity(Long id, ArticleRequest request) {
    String status = request.getStatus() == null ? "DRAFT" : request.getStatus();
    LocalDateTime publishedAt = "PUBLISHED".equals(status) ? LocalDateTime.now() : null;
    return new Article(
        id, request.getTitle(), request.getSlug(), request.getSummary(), request.getContent(),
        request.getCoverUrl(), status, request.getCategoryId(), null, null, publishedAt);
  }

  private void replaceTags(long articleId, List<Long> tagIds) {
    articleMapper.clearTags(articleId);
    if (tagIds != null) {
      for (Long tagId : tagIds) {
        articleMapper.addTag(articleId, tagId);
      }
    }
  }
}
