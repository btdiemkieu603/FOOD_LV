package com.kt.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kt.backend.entity.AccountStatus;

public interface AccountStatusRepository extends JpaRepository<AccountStatus, Integer>{

	 @Query(value = "select * from accountstatus where status = :status", nativeQuery = true)
	 AccountStatus findAccountStatusByStatus(@Param("status") String status);
	 
}
