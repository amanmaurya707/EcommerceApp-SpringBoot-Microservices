package com.category.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.category.dto.CategoryDTO;



public interface ICategoryService {
	public List<CategoryDTO>  readAllCategory();
	public List<CategoryDTO> readAllCategoryWithPagingAndSorting(int pageNo, int pageSize);
	public CategoryDTO readCategoryById(Long id);
	public CategoryDTO readCategoryByName(String category);
	public List<CategoryDTO> readAllCategoriesStartWithName(String category);
	public CategoryDTO saveCategory(CategoryDTO categoryDTO);
	public String removeCategoryById(Long id);
	public String removeAllCategory();
	public CategoryDTO editCategory(CategoryDTO categoryDTO);
	public Long totalCategory();

}
