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

import com.kt.backend.dto.OrderStatusDto;
import com.kt.backend.response.ApiResponse;
import com.kt.backend.service.OrderStatusService;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://192.168.234.28:30030")
@CrossOrigin(origins = "${allowedOrigins}")
@RequestMapping("/api/order_status")
public class OrderStatusController {

	@Autowired
	private OrderStatusService orStatusService;
	
	@PostMapping("/add")
	public ResponseEntity<OrderStatusDto> addOrderStatus(@RequestBody OrderStatusDto orStatusDto) {
		OrderStatusDto createOrStatusDto = this.orStatusService.createOrderStatus(orStatusDto);
		return new ResponseEntity<OrderStatusDto>(createOrStatusDto, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{orStatusId}")
	public ResponseEntity<ApiResponse> deleteRole(@PathVariable Integer orStatusId) {
		this.orStatusService.deleteOrderStatus(orStatusId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Order status is deleted successfully!", true),
				HttpStatus.OK);
	}	
	
	@GetMapping("/")
	public ResponseEntity<List<OrderStatusDto>> getAllOrderStatus() {
		List<OrderStatusDto> orStatusDtos = this.orStatusService.getOrderStatuses();
		return ResponseEntity.ok(orStatusDtos);
	}
}