package com.kt.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kt.backend.entity.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Integer>{

	 @Query(value = "select * from discount where code = :code", nativeQuery = true)
	 Discount findDiscountByCode(@Param("code") String code);
	 
	 @Query(value = "select * from discount where is_exist = true", nativeQuery = true)
	 List<Discount> findDiscountCurrent();

	 @Query(value = "select * from discount", nativeQuery = true)
	 List<Discount> findAllDiscount();

}
