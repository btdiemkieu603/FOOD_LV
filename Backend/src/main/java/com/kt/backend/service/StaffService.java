package com.kt.backend.service;

import java.util.List;

import com.kt.backend.dto.ResAccountDto;
import com.kt.backend.dto.StaffDto;
import com.kt.backend.dto.UpdateAccountDto;

import com.kt.backend.dto.AddressDto;
import com.kt.backend.dto.ChangePasswordDto;
import com.kt.backend.dto.LoginDto;

public interface StaffService {
	
	  StaffDto createStaff(StaffDto staffDto);
	    StaffDto updateStaff(StaffDto staffDto, String email);
	    void deleteStaff(String email);
	    StaffDto getStaffByEmail(String email);
	    List<StaffDto> getAllStaffs();

//	ResAccountDto createCustomerAccount(AccountDto accountDto, Integer roleId);
//	
//	ResAccountDto createStaffAccount(AccountDto AccountDto, Integer roleId);
//	
//	void deleteAccount(Integer accountId);
//	
//	List<ResAccountDto> getAccountsByRole(Integer roleId);
//	
//	ResAccountDto blockAccount(Integer accountId);
//	
//	ResAccountDto openAccount(Integer accountId);
//	
//	ResAccountDto updateAddressForAccount(Integer accountId, AddressDto addressDto);
//	
//	ResAccountDto getAccount(Integer accountId);
//	
//	ResAccountDto login(LoginDto loginDto);
//	
//	ResAccountDto updateAccount(UpdateAccountDto updateAccountDto, Integer accountId);
//	
//	boolean changePassword(Integer accountId, ChangePasswordDto changePassword);

}
