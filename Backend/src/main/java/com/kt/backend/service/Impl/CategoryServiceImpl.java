package com.kt.backend.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.backend.dto.CategoryDto;
import com.kt.backend.entity.Category;
import com.kt.backend.exception.ResourceNotFoundException;
import com.kt.backend.repository.CategoryRepository;
import com.kt.backend.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category addCat = this.categoryRepo.save(cat);
		return this.modelMapper.map(addCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId", categoryId));
		cat.setTitle(categoryDto.getTitle());	
		cat.setUrl_image_category(categoryDto.getUrl_image_category());
		Category updatedcat = this.categoryRepo.save(cat);
		return this.modelMapper.map(updatedcat, CategoryDto.class);
	}

	@Override
	public void deleteCaterogy(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId", categoryId));
		this.categoryRepo.delete(cat);
		
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> catDtos = categories.stream().map((cat)-> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());	
		return catDtos;
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId", categoryId));;
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
    public CategoryDto changeExist(Integer id) {
        Category category = this.categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        category.setIsExist(!category.getIsExist());
        Category updatedCategory = this.categoryRepo.save(category);

        return this.modelMapper.map(updatedCategory, CategoryDto.class);
    }
}
