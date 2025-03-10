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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.backend.dto.OrderDiscountDto;
import com.kt.backend.dto.OrderDto;
import com.kt.backend.dto.ProductDto;
import com.kt.backend.dto.ResOrderDto;
import com.kt.backend.response.ApiResponse;
import com.kt.backend.service.OrderService;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://192.168.234.28:30030")
@CrossOrigin(origins = "${allowedOrigins}")
@RequestMapping("/api/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/add/{accountId}/{checkoutId}")
	public ResponseEntity<ResOrderDto> createOrder(
			@RequestBody OrderDto orderDto,
			@PathVariable Integer accountId,
			@PathVariable Integer checkoutId
			) {
		ResOrderDto createOrder = this.orderService.createOrder(orderDto, accountId, checkoutId);
		return new ResponseEntity<ResOrderDto>(createOrder, HttpStatus.CREATED);
	}
	
	@PostMapping("/addDiscount/{accountId}/{checkoutId}")
	public ResponseEntity<ResOrderDto> createOrderDiscount(
			@RequestBody OrderDiscountDto orderDiscountDto,
			@PathVariable Integer accountId,
			@PathVariable Integer checkoutId
			) {
		ResOrderDto createOrder = this.orderService.createOrderDiscount(orderDiscountDto, accountId, checkoutId);
		return new ResponseEntity<ResOrderDto>(createOrder, HttpStatus.CREATED);
	}
	
	@PutMapping("/discount/{orderId}/{code}")
	public ResponseEntity<ResOrderDto> applyDiscountForOrder(
			@PathVariable Integer orderId,
			@PathVariable String code) {
		ResOrderDto applyOrder = this.orderService.applyDiscountForOrder(orderId, code);
	return new ResponseEntity<ResOrderDto>(applyOrder, HttpStatus.OK);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderDto> getOrder(@PathVariable Integer orderId) {
		OrderDto orderDto = this.orderService.getOrder(orderId);
		return new ResponseEntity<OrderDto>(orderDto, HttpStatus.OK);
	}

	@GetMapping("/totalOrder/account/{accountId}")
	public ResponseEntity<Integer> getTotalOrderByCustomer(@PathVariable Integer accountId) {	
		return new ResponseEntity<Integer>(this.orderService.getTotalOrderByCustomer(accountId), HttpStatus.OK);
	}
	
	@DeleteMapping("/{orderId}")
	public ResponseEntity<ApiResponse> deleteOrderById(@PathVariable Integer orderId) {
		this.orderService.deleteOrder(orderId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Order is deleted successfully!", true),
				HttpStatus.OK);
	}	
	
	@PutMapping("/{orderId}/{orderStatusId}")
	public ResponseEntity<ResOrderDto> changeOrderStatusById(@PathVariable Integer orderId,
			@PathVariable Integer orderStatusId
			) {
		ResOrderDto updatedOr = this.orderService.changeStatusOfOrder(orderId, orderStatusId);
		return new ResponseEntity<ResOrderDto>(updatedOr, HttpStatus.OK);
	}	
	
	@GetMapping("/toporder")
	public ResponseEntity<List<ProductDto>> getTopOrderCurrent() {
		List<ProductDto> productDtos = this.orderService.getThreeProductBestOrder();
		return new ResponseEntity<List<ProductDto>>(productDtos, HttpStatus.OK);
	}
	
	@GetMapping("/orderStatus/{orderStatusId}")
	public ResponseEntity<List<ResOrderDto>> getOrdersByOrderStatusID(@PathVariable Integer orderStatusId) {
		List<ResOrderDto> orderDtos = this.orderService.getOrdersByOrderStatus(orderStatusId);
		return new ResponseEntity<List<ResOrderDto>>(orderDtos, HttpStatus.OK);
	}
	
	@GetMapping("/{accountId}/{orderStatusId}")
	public ResponseEntity<List<ResOrderDto>> fillterOrderByAccountAndAcStatus(
			@PathVariable Integer accountId,
			@PathVariable Integer orderStatusId
			) {
		List<ResOrderDto> orderDtos = this.orderService.getOrdersByAccountAndAcStatus(accountId, orderStatusId);
		return new ResponseEntity<List<ResOrderDto>>(orderDtos, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<ResOrderDto>> getAllOrder() {
		List<ResOrderDto> orderDtos = this.orderService.getAllOrder();
		return new ResponseEntity<List<ResOrderDto>>(orderDtos, HttpStatus.OK);
	}
	
	@GetMapping("/purchases/")
	public ResponseEntity<Integer> getTotalPurchases() {	
		return new ResponseEntity<Integer>(this.orderService.getAllPurchasesInStore(), HttpStatus.OK);
	}
	
	@GetMapping("/purchases/{productId}")
	public ResponseEntity<Integer> getTotalPurchasesOfProduct(@PathVariable Integer productId) {	
		return new ResponseEntity<Integer>(this.orderService.getAllPurchasesByProductInStore(productId), HttpStatus.OK);
	}
}
