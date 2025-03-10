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

import com.kt.backend.dto.CartDto;
import com.kt.backend.dto.ItemDto;
import com.kt.backend.response.ApiResponse;
import com.kt.backend.service.CartService;
import com.kt.backend.service.ItemService;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://192.168.234.28:30030")
@CrossOrigin(origins = "${allowedOrigins}")
@RequestMapping("/api/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ItemService itemService;
	
	@PostMapping("/add")
	public ResponseEntity<CartDto> createCart(@RequestBody CartDto cartDto) {
		CartDto createCart = this.cartService.createCart(cartDto);
		return new ResponseEntity<CartDto>(createCart, HttpStatus.CREATED);
	}
	
	@GetMapping("/{cartId}")
	public ResponseEntity<CartDto> getCart(@PathVariable Integer cartId) {
		CartDto cartDto = this.cartService.getCart(cartId);
		return new ResponseEntity<CartDto>(cartDto, HttpStatus.OK);
	}

	@GetMapping("/all/{cartId}")
	public ResponseEntity<List<ItemDto>> getAllExistInCart(@PathVariable Integer cartId) {
		List<ItemDto> itemDtos = this.itemService.getItemsByCart(cartId);
		return new ResponseEntity<List<ItemDto>>(itemDtos, HttpStatus.OK);
	}
	
	@GetMapping("/current/{cartId}")
	public ResponseEntity<List<ItemDto>> getListItemCurrentInCart(@PathVariable Integer cartId) {
		List<ItemDto> itemDtos = this.itemService.getItemsCurrentByCart(cartId);
		return new ResponseEntity<List<ItemDto>>(itemDtos, HttpStatus.OK);
	}
	
	@DeleteMapping("/{cartId}")
	public ResponseEntity<ApiResponse> deleteCartById(@PathVariable Integer cartId) {
		this.cartService.deleteCart(cartId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Cart is deleted successfully!", true),
				HttpStatus.OK);
	}	
	@GetMapping("/totalprice/{cartId}")
	public Double getTotalPriceOfCart(@PathVariable Integer cartId) {
		return cartService.getTotalPriceOfCartCurrent(cartId);
	}
}
