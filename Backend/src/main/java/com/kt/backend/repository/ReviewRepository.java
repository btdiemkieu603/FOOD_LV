package com.kt.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kt.backend.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>{

	
	@Query(value = "select * from review where review.account_id = :accountId", nativeQuery = true)
	List<Review> findAllReviewOfAccount(@Param("accountId") Integer accountId);
	
	@Query(value = "select * from review where review.product_id = :productId", nativeQuery = true)
	List<Review> findAllReviewOfProduct(@Param("productId") Integer productId);
	
	@Query(value = "select * from review", nativeQuery = true)
	List<Review> findAllReview();
}
