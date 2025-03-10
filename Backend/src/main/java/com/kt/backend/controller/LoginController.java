package com.kt.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.backend.dto.LoginDto;
import com.kt.backend.dto.ResAccountDto;
import com.kt.backend.service.AccountService;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://192.168.234.28:30030")
@CrossOrigin(origins = "${allowedOrigins}")
@RequestMapping("/api/login")
public class LoginController {

	@Autowired
	private AccountService accountService;
	
	//@Value("${allowed.origins}")
   // private String allowedOrigins;
	
	//@CrossOrigin(origins = "${allowedOrigins}")
	
	@PostMapping("/")
	public ResponseEntity<ResAccountDto> loginAccount(@RequestBody LoginDto loginDto) {
		ResAccountDto loginAccountDto = this.accountService.login(loginDto);
		return new ResponseEntity<ResAccountDto>(loginAccountDto, HttpStatus.OK);
	}
}
