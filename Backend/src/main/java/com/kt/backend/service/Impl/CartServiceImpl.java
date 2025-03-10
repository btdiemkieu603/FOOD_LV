package com.kt.backend.service.Impl;


import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.backend.dto.CartDto;
import com.kt.backend.entity.Cart;
import com.kt.backend.entity.Item;
import com.kt.backend.exception.ResourceNotFoundException;
import com.kt.backend.repository.CartRepository;
import com.kt.backend.repository.ItemRepository;
import com.kt.backend.service.CartService;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CartDto createCart(CartDto cartDto) {
		Cart cart = this.modelMapper.map(cartDto, Cart.class);
		Cart addCart = this.cartRepo.save(cart);
		return this.modelMapper.map(addCart, CartDto.class);
	}

	@Override
	public CartDto getCart(Integer cartId) {
		Cart cart = this.cartRepo.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Cart","CartId", cartId));
		return this.modelMapper.map(cart, CartDto.class);
	}

	@Override
	public void deleteCart(Integer cartId) {
		Cart cart = this.cartRepo.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Cart","CartId", cartId));
		this.cartRepo.delete(cart);	
	}

	@Override
	public Double getTotalPriceOfCartCurrent(Integer cartId) {
		List<Item> items = this.itemRepository.findItemsCurrentByCart(cartId);
		Double totalprice = 0.0;
		for(Item item: items) {
			totalprice += item.getPrice();
		}
		return totalprice;
	}



}
