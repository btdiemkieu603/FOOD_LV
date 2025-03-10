package com.kt.backend.service;

import java.util.List;

import com.kt.backend.dto.CategoryDto;
import com.kt.backend.dto.ProductDto;

public interface ProductService {

	ProductDto createProduct(ProductDto productDto, Integer categoryId);
	
	ProductDto updateProduct(ProductDto productDto, Integer productId);
	
	void deleteProduct(Integer productId);

	ProductDto getProductById(Integer productId);
	
	List<ProductDto> getProductsByCategory(Integer categoryId);
	
	public List<ProductDto> getAllProducts(); 
	
	ProductDto changeExist(Integer id);
	
//	List<ProductDto> getSuggestedProducts(Integer userId);
}
