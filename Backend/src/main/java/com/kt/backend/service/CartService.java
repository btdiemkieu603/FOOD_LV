package com.kt.backend.service;

import com.kt.backend.dto.CartDto;


public interface CartService {
	
	CartDto createCart(CartDto cartDto);
	
	CartDto getCart(Integer cartId);
	
	void deleteCart(Integer cartId);
	
	Double getTotalPriceOfCartCurrent(Integer cartId);
}
