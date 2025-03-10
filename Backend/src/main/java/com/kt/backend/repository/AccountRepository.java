package com.kt.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kt.backend.entity.Account;
import com.kt.backend.entity.Role;

public interface AccountRepository extends JpaRepository<Account, Integer>{

	List<Account> findByRole(Role role);
	
	@Query(value = "select * from account where account.phonenumber = :phonenumber and account.password = :password", nativeQuery = true)
	Account findAccountByLoginDto(@Param("phonenumber") String phonenumber, @Param("password") String password);
	
	@Query(value = "select * from account where account.phonenumber = :phonenumber or  account.email = :email", nativeQuery = true)
	List<Account> isValidOfPhoneNumberAndEmail(@Param("phonenumber") String phonenumber, @Param("email") String email);
	
	Optional<Account> findByEmail(String email);

}
