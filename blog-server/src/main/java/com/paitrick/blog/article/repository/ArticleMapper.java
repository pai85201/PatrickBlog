package com.paitrick.blog.article.repository;

import com.paitrick.blog.article.domain.Article;
import com.paitrick.blog.tag.domain.Tag;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ArticleMapper {
  @Select("select * from article where status = 'PUBLISHED' order by published_at desc limit #{limit} offset #{offset}")
  List<Article> published(int limit, int offset);

  @Select("select count(*) from article where status = 'PUBLISHED'")
  long publishedCount();

  @Select("select * from article where slug = #{slug} and status = 'PUBLISHED'")
  Article publishedBySlug(String slug);

  @Select("select * from article where id = #{id}")
  Article byId(long id);

  @Select("select * from article order by updated_at desc limit #{limit} offset #{offset}")
  List<Article> adminList(int limit, int offset);

  @Select("select count(*) from article")
  long count();

  @Select("select t.* from tag t join article_tag at on at.tag_id = t.id where at.article_id = #{id} order by t.name")
  List<Tag> tags(long id);

  @Insert({
    "insert into article(title, slug, summary, content, cover_url, status, category_id, published_at)",
    "values(#{title}, #{slug}, #{summary}, #{content}, #{coverUrl}, #{status}, #{categoryId}, #{publishedAt})"
  })
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int insert(Article article);

  @Update({
    "update article set title = #{title}, slug = #{slug}, summary = #{summary}, content = #{content},",
    "cover_url = #{coverUrl}, status = #{status}, category_id = #{categoryId},",
    "published_at = #{publishedAt} where id = #{id}"
  })
  int update(Article article);

  @Delete("delete from article_tag where article_id = #{id}")
  int clearTags(long id);

  @Insert("insert into article_tag(article_id, tag_id) values(#{articleId}, #{tagId})")
  int addTag(long articleId, long tagId);

  @Delete("delete from article where id = #{id}")
  int delete(long id);
}
