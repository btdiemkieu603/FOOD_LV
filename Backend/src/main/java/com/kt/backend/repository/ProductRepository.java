package com.kt.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kt.backend.entity.Category;
import com.kt.backend.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	List<Product> findByCategory(Category category);
	
	@Query(value = "select id from product", nativeQuery = true)
	List<Integer> getAllIdOfProduct();
}
