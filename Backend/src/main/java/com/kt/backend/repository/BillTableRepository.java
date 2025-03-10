package com.kt.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.kt.backend.entity.BillTable;

@Repository
public interface BillTableRepository extends JpaRepository<BillTable, Integer> {
	@Query("SELECT b FROM BillTable b WHERE b.orderTable.orderTableId = :orderTId")
    Optional<BillTable> findByOrderTable_OrderTableId(@Param("orderTId") Integer orderTId);

}