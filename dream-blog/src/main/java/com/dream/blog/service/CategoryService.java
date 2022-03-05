package com.dream.blog.service;


import com.dream.blog.domain.entity.Category;
import com.dream.blog.domain.dto.CategorySubmitItem;

import java.util.List;

public interface CategoryService {

	List<Category> getCategoryList(Integer parentId);
	List<Category> getAll();
	Integer saveCategory(CategorySubmitItem submitItem);
	boolean updateCategory(CategorySubmitItem submitItem);
	boolean deleteCategoryById(int cateId);
	Category getById(int cateId);
	boolean updateCategoryCode(int id, String code);
}
