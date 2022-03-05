package com.dream.blog.service.impl;

import com.dream.blog.domain.entity.Category;
import com.dream.blog.domain.dto.CategorySubmitItem;
import com.dream.blog.mapper.CategoryMapper;
import com.dream.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

	@Override
	public List<Category> getCategoryList(Integer parentId) {
		return categoryMapper.getCategoryList(parentId);
	}

	@Override
	public List<Category> getAll() {
		return categoryMapper.getAll();
	}

	@Override
	public Integer saveCategory(CategorySubmitItem submitItem) {
		return categoryMapper.saveCategory(submitItem);
	}

	@Override
	public boolean updateCategory(CategorySubmitItem submitItem) {
		return categoryMapper.updateCategory(submitItem);
	}

	@Override
	public boolean deleteCategoryById(int cateId) {
		return categoryMapper.deleteCategoryById(cateId);
	}

	@Override
	public Category getById(int cateId) {
		return categoryMapper.getById(cateId);
	}

	@Override
	public boolean updateCategoryCode(int id, String code) {
		return categoryMapper.updateCategoryCode(id,code);
	}

}
