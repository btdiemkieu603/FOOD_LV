package com.kt.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.backend.dto.ItemDto;
import com.kt.backend.response.ApiResponse;
import com.kt.backend.service.ItemService;

//@CrossOrigin(origins = "http://localhost:3000")

//@CrossOrigin(origins = "http://192.168.234.28:30030")
@CrossOrigin(origins = "${allowedOrigins}")
@RestController
@RequestMapping("/api/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@PostMapping("/add/{cartId}/{productId}")
	public ResponseEntity<ItemDto> createProductItem(@RequestBody ItemDto itemDto,
			@PathVariable Integer cartId,
			@PathVariable Integer productId) {
		ItemDto createItem = this.itemService.createItem(itemDto, cartId, productId);
		return new ResponseEntity<ItemDto>(createItem, HttpStatus.CREATED);
	}
	
	
//	@PostMapping("/addDiscount/{cartId}/{productId}")
//	public ResponseEntity<ResItemDiscountDto> createProductItemDiscount(@RequestBody ItemDiscountDto itemDiscountDto,
//			@PathVariable Integer cartId,
//			@PathVariable Integer productId) {
//		ResItemDiscountDto createItem = this.itemService.createItemDiscount(itemDiscountDto, cartId, productId);
//		return new ResponseEntity<ResItemDiscountDto>(createItem, HttpStatus.CREATED);
//	}
//	
//	@PutMapping("/discount/{itemId}/{code}")
//	public ResponseEntity<ResItemDiscountDto> applyDiscountForItem(
//			@PathVariable Integer itemId,
//			@PathVariable String code) {
//		ResItemDiscountDto applyItem = this.itemService.applyDiscount(itemId, code);
//		return new ResponseEntity<ResItemDiscountDto>(applyItem, HttpStatus.OK);
//	}
	
	
	@PutMapping("/{itemId}")
	public ResponseEntity<ItemDto> updateItem(@RequestBody ItemDto itemDto,
			@PathVariable Integer itemId) {
		ItemDto updatedItem = this.itemService.updateItem(itemDto, itemId);
		return new ResponseEntity<ItemDto>(updatedItem, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{itemId}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Integer itemId) {
		this.itemService.deleteItem(itemId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Item is deleted successfully!", true),
				HttpStatus.OK);
	}
	
}
