package com.kt.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.backend.dto.CategoryDto;
import com.kt.backend.dto.TableInfoDto;
import com.kt.backend.response.ApiResponse;
import com.kt.backend.service.CategoryService;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://192.168.234.28:30030")
@CrossOrigin(origins = "${allowedOrigins}")
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/add")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
	}

	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
			@PathVariable Integer catId) {
		CategoryDto updatedCat = this.categoryService.updateCategory(categoryDto, catId);
		return new ResponseEntity<CategoryDto>(updatedCat, HttpStatus.OK);
	}

	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId) {
		this.categoryService.deleteCaterogy(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted successfully!", true),
				HttpStatus.OK);
	}	

	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId) {
		CategoryDto catDto = this.categoryService.getCategory(catId);
		return new ResponseEntity<CategoryDto>(catDto, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories() {
		List<CategoryDto> categories = this.categoryService.getCategories();
		return ResponseEntity.ok(categories);
	}
	
	 @PutMapping("/hidden/{catId}")
	    public ResponseEntity<CategoryDto> changeExist(@PathVariable Integer catId) {
		 CategoryDto updatedTable = categoryService.changeExist(catId);
	        return ResponseEntity.ok(updatedTable);

	    }
	
}
