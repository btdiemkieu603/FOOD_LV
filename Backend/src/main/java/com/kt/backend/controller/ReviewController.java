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

import com.kt.backend.dto.ReviewDto;
import com.kt.backend.response.ApiResponse;
import com.kt.backend.service.ReviewService;

//@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://192.168.234.28:30030")
@CrossOrigin(origins = "${allowedOrigins}")
@RestController
@RequestMapping("/api/review")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@PostMapping("/add/{accountId}/{productId}")
	public ResponseEntity<ReviewDto> createReviewProduct(@RequestBody ReviewDto reviewDto,
			@PathVariable Integer accountId,
			@PathVariable Integer productId) {
		ReviewDto createReview = this.reviewService.createReview(reviewDto, accountId, productId);
		return new ResponseEntity<ReviewDto>(createReview, HttpStatus.CREATED);
	}
	
	@PutMapping("/{reviewId}")
	public ResponseEntity<ReviewDto> updateReview(@RequestBody ReviewDto reviewDto,
			@PathVariable Integer reviewId) {
		ReviewDto updatedReview = this.reviewService.updateReview(reviewDto, reviewId);
		return new ResponseEntity<ReviewDto>(updatedReview, HttpStatus.OK);
	}
	
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Integer reviewId) {
		this.reviewService.deleteReview(reviewId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Review is deleted successfully!", true),
				HttpStatus.OK);
	}
	
	@GetMapping("/account/{accountId}")
	public ResponseEntity<List<ReviewDto>> getListReviewOfAccount(@PathVariable Integer accountId) {
		List<ReviewDto> itemDtos = this.reviewService.getReviewsOfAccount(accountId);
		return new ResponseEntity<List<ReviewDto>>(itemDtos, HttpStatus.OK);
	}
	
	@GetMapping("/totalreview/")
	public ResponseEntity<Integer> getTotalReview() {
		return new ResponseEntity<Integer>(this.reviewService.getTotalReview(), HttpStatus.OK);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<ReviewDto>> getListReviewOfProduct(@PathVariable Integer productId) {
		List<ReviewDto> itemDtos = this.reviewService.getReviewsOfProduct(productId);
		return new ResponseEntity<List<ReviewDto>>(itemDtos, HttpStatus.OK);
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<List<ReviewDto>> getAllReview() {
		List<ReviewDto> itemDtos = this.reviewService.getAllReview();
		return new ResponseEntity<List<ReviewDto>>(itemDtos, HttpStatus.OK);
	}
}
