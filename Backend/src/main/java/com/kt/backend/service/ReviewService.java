package com.kt.backend.service;

import java.util.List;

import com.kt.backend.dto.ReviewDto;

public interface ReviewService {

	ReviewDto createReview(ReviewDto reviewDto, Integer accountId, Integer productId);
		
	ReviewDto updateReview(ReviewDto reviewDto, Integer reviewId);
	
	void deleteReview(Integer reviewId);
	
	List<ReviewDto> getReviewsOfAccount(Integer accountId);
	
	List<ReviewDto> getReviewsOfProduct(Integer productId);
	
	Integer getTotalReview();
	
	List<ReviewDto> getAllReview();
}
