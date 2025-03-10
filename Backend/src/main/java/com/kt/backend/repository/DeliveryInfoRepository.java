package com.kt.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kt.backend.dto.DeliveryInfoDto;
import com.kt.backend.entity.DeliveryInfo;
import com.kt.backend.entity.Discount;

public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo, Integer> {
	 //DeliveryInfoDto createDeliveryInfo(Integer orderOnlineId, String deliveryAddress);
	    //DeliveryInfoDto getDeliveryInfoByOrderId(Integer orderOnlineId);
//	  @Query("SELECT new com.kt.backend.dto.DeliveryInfoDto(d.deliveryInfoId, d.orderOnline.orderOnlineId, d.address.id, d.deliveryStaff.email, d.deliveryFee, d.deliveryTime) " +
//	           "FROM DeliveryInfo d WHERE d.orderOnline.orderOnlineId = :orderOnlineId")
//	    DeliveryInfoDto getDeliveryInfoByOrderId(@Param("orderOnlineId") Integer orderOnlineId);
//
//	    Optional<DeliveryInfo> findByOrderOnline_OrderOnlineId(Integer orderId);
//	
	
	
//	 @Query("SELECT new com.kt.backend.dto.DeliveryInfoDto(d.deliveryInfoId, d.orderOnline.id, d.address.id, d.deliveryStaff.email, d.deliveryFee, d.deliveryTime) " +
//	           "FROM DeliveryInfo d WHERE d.orderOnline.id = :orderOnlineId")
//	    DeliveryInfoDto getDeliveryInfoByOrderId(@Param("orderOnlineId") Integer orderOnlineId);
//
//	    Optional<DeliveryInfo> findByOrderOnline_OrderOnlineId(Integer orderId);
}