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

import com.kt.backend.dto.CheckOutDto;
import com.kt.backend.response.ApiResponse;
import com.kt.backend.service.CheckOutService;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://192.168.234.28:30030")
@CrossOrigin(origins = "${allowedOrigins}")
@RequestMapping("/api/checkout")
public class CheckOutController {

	@Autowired
	private CheckOutService checkOutService;
	
	@PostMapping("/add")
	public ResponseEntity<CheckOutDto> addCheckOut(@RequestBody CheckOutDto checkOutDto) {
		CheckOutDto createCheckOut = this.checkOutService.createCheckOut(checkOutDto);
		return new ResponseEntity<CheckOutDto>(createCheckOut, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{roleId}")
	public ResponseEntity<ApiResponse> deleteCheckOut(@PathVariable Integer checkOutId) {
		this.checkOutService.deleteCheckOut(checkOutId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Checkout is deleted successfully!", true),
				HttpStatus.OK);
	}	
	
	@GetMapping("/")
	public ResponseEntity<List<CheckOutDto>> getAllCheckOuts() {
		List<CheckOutDto> checkOuts = this.checkOutService.getCheckOuts();
		return ResponseEntity.ok(checkOuts);
	}
}
