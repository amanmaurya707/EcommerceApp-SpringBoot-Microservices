package com.category.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.category.dto.CategoryDTO;
import com.category.service.ICategoryService;

import io.swagger.annotations.ApiOperation;

@RestController
public class CategoryController {
	@Autowired
	ICategoryService categoryService;
	
	@GetMapping("/listAllCategory")
	//@ApiOperation(value = "return all categories")
	public ResponseEntity<List<CategoryDTO>> getAllCategory()
	{
		return ResponseEntity.ok(categoryService.readAllCategory());
		
	}
	@GetMapping("/listAllCategory/{pageNo}/{pageSize}")
	public ResponseEntity<List<CategoryDTO>> getAllCategoryWithPagingAndSorting(@PathVariable(value = "pageNo") int pageNo,@PathVariable(value = "pageSize") int pageSize)
	{	
		return new ResponseEntity<List<CategoryDTO>>(categoryService.readAllCategoryWithPagingAndSorting(pageNo, pageSize), HttpStatus.OK);
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id)
	{
		CategoryDTO categoryDTO=categoryService.readCategoryById(id);
	
		if (categoryDTO==null)
		{
			return null;	
		}
		else
		{
		return ResponseEntity.ok(categoryDTO);
		}
		
	}
	@GetMapping("/category/{category}")
	public ResponseEntity<CategoryDTO> findCategoryByName(@PathVariable String category)
	{
		CategoryDTO categoryDTO=categoryService.readCategoryByName(category);
		
		if (categoryDTO==null) 
		{
			return null;	
		}
		else
		{
		return ResponseEntity.ok(categoryDTO);
		}
		
	}
	@GetMapping("/searchAll/{category}")
	public ResponseEntity<List<CategoryDTO>> searchAllCategoriesStartWithName(@PathVariable String category)
	{
		return ResponseEntity.ok(categoryService.readAllCategoriesStartWithName(category));
		
	}
	@GetMapping("/totalCategory")
	public ResponseEntity<Long> totalCategory()
	{
		return ResponseEntity.ok(categoryService.totalCategory());
		
    }
	@PostMapping("/addCategory")//@RequestBody deserialized the json data format to java object
	public ResponseEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO)
	{
		CategoryDTO saveCategoryDTO=categoryService.saveCategory(categoryDTO);
		if (saveCategoryDTO==null) {
			return null;
		}
		else
		{
		return ResponseEntity.ok(saveCategoryDTO);
		
		}
	}
	@PutMapping("/updateCategory")
	public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO)
	{
		CategoryDTO updatedCategoryDTO=categoryService.editCategory(categoryDTO);
		if (updatedCategoryDTO==null) {
			return null;
		}
		else
		{
		return ResponseEntity.ok(updatedCategoryDTO);
		}
	
		
	}
	
	@DeleteMapping("/deleteCategory/{id}")
	public ResponseEntity<String> deleteCategoryById(@PathVariable Long id)
	{
		return new ResponseEntity<String>(categoryService.removeCategoryById(id),HttpStatus.OK);
		
	}
	@DeleteMapping("/deleteAllCategory")
	public ResponseEntity<String>  deleteAllCategory()
	{
		return new ResponseEntity<String>(categoryService.removeAllCategory(),HttpStatus.OK);
		
	}
}
