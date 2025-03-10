package com.kt.backend.service;

import java.util.List;

import com.kt.backend.dto.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);
	
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	void deleteCaterogy(Integer categoryId);
	
	List<CategoryDto> getCategories();
	
	CategoryDto getCategory(Integer categoryId);
	CategoryDto changeExist(Integer id);

}
