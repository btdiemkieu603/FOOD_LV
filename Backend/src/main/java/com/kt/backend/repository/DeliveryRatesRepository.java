package com.kt.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kt.backend.entity.DeliveryRates;
import com.kt.backend.entity.Discount;

public interface DeliveryRatesRepository extends JpaRepository<DeliveryRates, Integer>{

//	 @Query(value = "select * from discount where code = :code", nativeQuery = true)
//	 Discount findDiscountByCode(@Param("code") String code);
//	 
//	 @Query(value = "select * from discount where is_exist = true", nativeQuery = true)
//	 List<Discount> findDiscountCurrent();
//
//	 @Query(value = "select * from discount", nativeQuery = true)
//	 List<Discount> findAllDiscount();
	
	
Optional<DeliveryRates> findByProvinceAndDistrict(String province, String district);
	
	// Optional<DeliveryRates> findByDeliveryArea(String deliveryArea);
}
