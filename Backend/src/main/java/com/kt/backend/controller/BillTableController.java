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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.kt.backend.dto.BillTableDto;
import com.kt.backend.dto.RevenueDto;
import com.kt.backend.response.ApiResponse;
import com.kt.backend.service.BillTableService;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://192.168.234.28:30030")
@CrossOrigin(origins = "${allowedOrigins}")
@RequestMapping("/api/bill-table")
public class BillTableController {
	   @Autowired
	    private BillTableService billTableService;

//	    @PostMapping
//	    public ResponseEntity<BillTableDto> createBillTable(@RequestBody BillTableDto billTableDto) {
//	        return new ResponseEntity<>(billTableService.createBillTable(billTableDto), HttpStatus.CREATED);
//	    }

	   
//	   @PostMapping("/{orderTableId}")
//	    public ResponseEntity<BillTableDto> createBill(
//	            @PathVariable Integer orderTableId,
//	            @RequestParam Double receivedAmount) {
//
//	        BillTableDto billDto = billTableService.createBill(orderTableId, receivedAmount);
//	        return ResponseEntity.ok(billDto);
//	    }
	   
	   @PostMapping("/{orderTableId}")
	    public ResponseEntity<BillTableDto> createBill(@PathVariable Integer orderTableId, @RequestBody BillTableDto billTableDto) {
	        BillTableDto createdBill = billTableService.createBill(orderTableId, billTableDto);
	        return ResponseEntity.ok(createdBill);
	    }
	   
	    @GetMapping("/bill/{billId}")
	    public ResponseEntity<BillTableDto> getBillTableById(@PathVariable Integer billId) {
	        return new ResponseEntity<>(billTableService.getBillTableById(billId), HttpStatus.OK);
	    }
	    
	    @GetMapping("/orderTable/{orderTableId}")
	    public ResponseEntity<BillTableDto> getBillByOrderTableId(@PathVariable Integer orderTableId) {
	        return new ResponseEntity<>(billTableService.getBillByOrderTableId(orderTableId), HttpStatus.OK);
	    }

	    @GetMapping("/all")
	    public ResponseEntity<List<BillTableDto>> getAllBillsTable() {
	        return new ResponseEntity<>(billTableService.getAllBillsTable(), HttpStatus.OK);
	    }
}