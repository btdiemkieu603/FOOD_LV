package com.kt.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kt.backend.entity.Staff;
import com.kt.backend.entity.Role;

public interface StaffRepository extends JpaRepository<Staff, Integer>{
//
//	List<Staff> findByRole(Role role);
//	
//	@Query(value = "select * from account where account.phonenumber = :phonenumber and account.password = :password", nativeQuery = true)
//	Staff findAccountByLoginDto(@Param("phonenumber") String phonenumber, @Param("password") String password);
//	
//	@Query(value = "select * from account where account.phonenumber = :phonenumber or  account.email = :email", nativeQuery = true)
//	List<Staff> isValidOfPhoneNumberAndEmail(@Param("phonenumber") String phonenumber, @Param("email") String email);
//	

	 Optional<Staff> findByEmail(String email); // Phương thức tìm theo email
	// Phương thức kiểm tra xem email đã tồn tại hay chưa
	    boolean existsByEmail(String email);
}
