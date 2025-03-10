package com.kt.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kt.backend.entity.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer>{

	@Query(value = "select * from orderstatus where status = :status", nativeQuery = true)
	 OrderStatus findOrderStatusByStatus(@Param("status") String status);
	
	@Query(value = "select * from orderstatus where id = :id", nativeQuery = true)
	 OrderStatus findOrderStatusByStatusID(@Param("id") Integer id);
}
