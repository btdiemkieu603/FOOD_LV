package com.kt.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kt.backend.entity.Bill;

public interface BillRepository extends JpaRepository<Bill, Integer>{

	@Query(value = "select sum(totalprice) from bill", nativeQuery = true)
	Double getTotalRevenue();
	
	@Query(value = "select sum(totalprice) from bill where issuedate like :month", nativeQuery = true)
	Double getRevenueByMonth(@Param("month") String month);
}
