package com.paitrick.blog.category.repository;
import com.paitrick.blog.category.domain.Category;
import org.apache.ibatis.annotations.*;
import java.util.*;
@Mapper public interface CategoryMapper{
  @Select("select * from category order by name") List<Category> all();
  @Select("select * from category where id=#{id}") Category byId(long id);
  @Insert("insert into category(name,slug,description) values(#{name},#{slug},#{description})") @Options(useGeneratedKeys=true,keyProperty="id") int insert(Category c);
  @Update("update category set name=#{name},slug=#{slug},description=#{description} where id=#{id}") int update(Category c);
  @Delete("delete from category where id=#{id}") int delete(long id);
}

