package com.dream.blog.mapper;


import com.dream.blog.domain.entity.Category;
import com.dream.blog.domain.dto.CategorySubmitItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {
    List<Category> getCategoryList(@Param("parentId") Integer parentId);
    List<Category> getAll();
    Category getById(int cateId);
    Integer saveCategory(CategorySubmitItem submitItem);
    boolean updateCategory(CategorySubmitItem submitItem);
    boolean updateCategoryCode(@Param("id") int id, @Param("code") String code);
    boolean deleteCategoryById(@Param("cateId") int cateId);
}
