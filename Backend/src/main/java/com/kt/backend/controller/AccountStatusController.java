package com.kt.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.backend.dto.AccountStatusDto;
import com.kt.backend.response.ApiResponse;
import com.kt.backend.service.AccountStatusService;


@RestController
//@CrossOrigin(origins = "http://192.168.234.28:30030")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "${allowedOrigins}")
@RequestMapping("/api/account_status")
public class AccountStatusController {

	@Autowired
	private AccountStatusService acStatusService;
	
	@PostMapping("/add")
	public ResponseEntity<AccountStatusDto> addAccountStatus(@RequestBody AccountStatusDto acStatusDto) {
		AccountStatusDto createAcStatusDto = this.acStatusService.createAccountStatus(acStatusDto);
		return new ResponseEntity<AccountStatusDto>(createAcStatusDto, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{acStatusId}")
	public ResponseEntity<ApiResponse> deleteRole(@PathVariable Integer acStatusId) {
		this.acStatusService.deleteAccountStatus(acStatusId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Account status is deleted successfully!", true),
				HttpStatus.OK);
	}	
	
	@GetMapping("/")
	public ResponseEntity<List<AccountStatusDto>> getAllAccountStatus() {
		List<AccountStatusDto> acStatusDtos = this.acStatusService.getAccountStatuses();
		return ResponseEntity.ok(acStatusDtos);
	}
}
