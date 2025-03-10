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

import com.kt.backend.dto.BillDto;
import com.kt.backend.dto.RevenueDto;
import com.kt.backend.response.ApiResponse;
import com.kt.backend.service.BillService;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://192.168.234.28:30030")
@CrossOrigin(origins = "${allowedOrigins}")
@RequestMapping("/api/bill")
public class BillController {
	@Autowired
	private BillService billService;
	
	@PostMapping("/add")
	public ResponseEntity<BillDto> createBill(@RequestBody BillDto billDto) {
		BillDto createBill = this.billService.createBill(billDto);
		return new ResponseEntity<BillDto>(createBill, HttpStatus.CREATED);
	}
	
	@GetMapping("/{billId}")
	public ResponseEntity<BillDto> getCart(@PathVariable Integer billId) {
		BillDto billDto = this.billService.getBill(billId);
		return new ResponseEntity<BillDto>(billDto, HttpStatus.OK);
	}

	@DeleteMapping("/{billId}")
	public ResponseEntity<ApiResponse> deleteBillById(@PathVariable Integer billId) {
		this.billService.deleteBill(billId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Bill is deleted successfully!", true),
				HttpStatus.OK);
	}	
	
	@GetMapping("/revenue/")
	public ResponseEntity<RevenueDto> getTotalRevenue() {
		RevenueDto revenueDto = this.billService.getRevenueOfStore();
		return new ResponseEntity<RevenueDto>(revenueDto, HttpStatus.OK);
	}

	@GetMapping("/revenue/{month}")
	public ResponseEntity<RevenueDto> getTotalRevenue(@PathVariable Integer month) {
		String monthS = "%/0"+month+"/%";
		RevenueDto revenueDto = this.billService.getRevenueOfStoreInMonth(monthS);
		return new ResponseEntity<RevenueDto>(revenueDto, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<BillDto>> getAllBill() {
		List<BillDto> billDtos = this.billService.getAllBill();
		return new ResponseEntity<List<BillDto>>(billDtos, HttpStatus.OK);
	}
}