package com.paitrick.blog.category.service;
import com.paitrick.blog.category.domain.Category;
import com.paitrick.blog.category.dto.CategoryRequest;
import com.paitrick.blog.category.repository.CategoryMapper;
import com.paitrick.blog.common.exception.BusinessException;
import org.springframework.stereotype.Service;
import java.util.*;
@Service public class CategoryService{
  private final CategoryMapper m;
  public CategoryService(CategoryMapper m){
    this.m=m;
  }
  public List<Category> all(){
    return m.all();
  }
  public Category create(CategoryRequest r){
    var c=new Category(null,r.getName(),r.getSlug(),r.getDescription(),null,null);
    m.insert(c);
    return c;
  }
  public Category update(long id,CategoryRequest r){
    if(m.byId(id)==null)throw new BusinessException("category not found");
    var c=new Category(id,r.getName(),r.getSlug(),r.getDescription(),null,null);
    m.update(c);
    return m.byId(id);
  }
  public void delete(long id){
    if(m.delete(id)==0)throw new BusinessException("category not found");
  }
}

