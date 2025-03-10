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
import com.kt.backend.dto.ProductDto;
import com.kt.backend.response.ApiResponse;
import com.kt.backend.service.ProductService;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://192.168.234.28:30030")
@CrossOrigin(origins = "${allowedOrigins}")
@RequestMapping("/api/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@PostMapping("/add/{categoryId}")
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto,
			@PathVariable Integer categoryId) {
		ProductDto createProduct = this.productService.createProduct(productDto, categoryId);
		return new ResponseEntity<ProductDto>(createProduct, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Integer productId) {
		this.productService.deleteProduct(productId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Product is deleted successfully!", true),
				HttpStatus.OK);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> getProduct(@PathVariable Integer productId) {
		ProductDto productDto = this.productService.getProductById(productId);
		return new ResponseEntity<ProductDto>(productDto, HttpStatus.OK);
	}

	@PutMapping("/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,
			@PathVariable Integer productId) {
		ProductDto updatedProduct = this.productService.updateProduct(productDto, productId);
		return new ResponseEntity<ProductDto>(updatedProduct, HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable Integer categoryId) {
		List<ProductDto> productDtos = this.productService.getProductsByCategory(categoryId);
		return new ResponseEntity<List<ProductDto>>(productDtos, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<ProductDto>> getAllProducts() {
		List<ProductDto> productDtos = this.productService.getAllProducts();
		return new ResponseEntity<List<ProductDto>>(productDtos, HttpStatus.OK);
	}
	
	 @PutMapping("/hidden/{productId}")
	    public ResponseEntity<ProductDto> changeExist(@PathVariable Integer productId) {
		 ProductDto updatedStatus = productService.changeExist(productId);
	        return ResponseEntity.ok(updatedStatus);

	    }
//	 @GetMapping("/suggested/{userId}")
//	 public ResponseEntity<List<ProductDto>> getSuggestedProducts(@PathVariable Integer userId) {
//	     List<ProductDto> suggestedProducts = this.productService.getSuggestedProducts(userId);
//	     return new ResponseEntity<>(suggestedProducts, HttpStatus.OK);
//	 }
	 
}
