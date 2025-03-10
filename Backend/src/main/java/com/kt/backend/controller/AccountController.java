package com.kt.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.backend.dto.ResAccountDto;
import com.kt.backend.dto.UpdateAccountDto;
import com.kt.backend.dto.AccountDto;
import com.kt.backend.dto.AddressDto;
import com.kt.backend.dto.ChangePasswordDto;
import com.kt.backend.response.ApiResponse;
import com.kt.backend.service.AccountService;

@RestController
//@CrossOrigin(origins = "http://localhost:5173")
//@CrossOrigin(origins = "http://192.168.234.28:30030")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "${allowedOrigins}")
@RequestMapping("/api/account")



public class AccountController {

	@Autowired
	private AccountService accountService;

//	@Value("${allowed.origins}")
 //   private String allowedOrigins;
	
//	@CrossOrigin(origins = "${allowedOrigins}")
	
	@PostMapping("/addCustomer/{roleId}")
	public ResponseEntity<ResAccountDto> addCustomerAccount(@RequestBody AccountDto accountDto,
			@PathVariable Integer roleId) {
		ResAccountDto newAccountDto = this.accountService.createCustomerAccount(accountDto, roleId);
		return new ResponseEntity<ResAccountDto>(newAccountDto, HttpStatus.CREATED);
	}

	
	@PostMapping("/addStaff/{roleId}")
	public ResponseEntity<ResAccountDto> addStaffAccount(@RequestBody AccountDto accountDto,
			@PathVariable Integer roleId) {
		ResAccountDto newAccountDto = this.accountService.createStaffAccount(accountDto, roleId);
		return new ResponseEntity<ResAccountDto>(newAccountDto, HttpStatus.CREATED);
	}

	@GetMapping("/{roleId}")
	public ResponseEntity<List<ResAccountDto>> getListAccountByRole(@PathVariable Integer roleId) {
		List<ResAccountDto> accountDtos = this.accountService.getAccountsByRole(roleId);
		return new ResponseEntity<List<ResAccountDto>>(accountDtos, HttpStatus.OK);
	}

	@GetMapping("/totalaccount/{roleId}")
	public ResponseEntity<Integer> getTotalCustomer(@PathVariable Integer roleId) {
		List<ResAccountDto> accountDtos = this.accountService.getAccountsByRole(roleId);
		return new ResponseEntity<Integer>(accountDtos.size(), HttpStatus.OK);
	}
	
	@DeleteMapping("/{accountId}")
	public ResponseEntity<ApiResponse> deleteAccount(@PathVariable Integer accountId) {
		this.accountService.deleteAccount(accountId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Account is deleted successfully!", true),
				HttpStatus.OK);
	}

	@PutMapping("/block/{accountId}")
	public ResponseEntity<ResAccountDto> changeStatusAccountToBlock(@PathVariable Integer accountId) {
		ResAccountDto updatedAc = this.accountService.blockAccount(accountId);
		return new ResponseEntity<ResAccountDto>(updatedAc, HttpStatus.OK);
	}

	@PutMapping("/open/{accountId}")
	public ResponseEntity<ResAccountDto> changeStatusAccountToOpen(@PathVariable Integer accountId) {
		ResAccountDto updatedAc = this.accountService.openAccount(accountId);
		return new ResponseEntity<ResAccountDto>(updatedAc, HttpStatus.OK);
	}

	@PutMapping("/address/{accountId}")
	public ResponseEntity<ResAccountDto> updateAddressForAccountById(@RequestBody AddressDto addressDto,
			@PathVariable Integer accountId) {
		ResAccountDto updatedAc = this.accountService.updateAddressForAccount(accountId, addressDto);
		return new ResponseEntity<ResAccountDto>(updatedAc, HttpStatus.OK);
	}
	
	@GetMapping("/get/{accountId}")
	public ResponseEntity<ResAccountDto> getAccountDetail(@PathVariable Integer accountId) {
		ResAccountDto accountInfo = this.accountService.getAccount(accountId);
		return new ResponseEntity<ResAccountDto>(accountInfo, HttpStatus.OK);
	}
	
	@PutMapping("/{accountId}")
	public ResponseEntity<ResAccountDto> updateInfoAccountById(@RequestBody UpdateAccountDto updateAccountDto,
			@PathVariable Integer accountId) {
		ResAccountDto updatedAc = this.accountService.updateAccount(updateAccountDto, accountId);
		return new ResponseEntity<ResAccountDto>(updatedAc, HttpStatus.OK);
	}
	
	@PutMapping("/changepassword/{accountId}")
	public ResponseEntity<ApiResponse> changePasswordForAccount(@RequestBody ChangePasswordDto changePasswordDto,
			@PathVariable Integer accountId) {
		boolean result = this.accountService.changePassword(accountId, changePasswordDto);
		if(result == true) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Password is changed successfully!", true),
					HttpStatus.OK);
		}else {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Password is not correct", false),
					HttpStatus.OK);
		}
		
	}
}
