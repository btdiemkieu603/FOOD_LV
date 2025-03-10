package com.kt.backend.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.backend.dto.ItemDto;
import com.kt.backend.entity.Cart;
import com.kt.backend.entity.Item;
import com.kt.backend.entity.Product;
import com.kt.backend.exception.ResourceNotFoundException;
import com.kt.backend.repository.CartRepository;
import com.kt.backend.repository.ItemRepository;
import com.kt.backend.repository.ProductRepository;
import com.kt.backend.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CartRepository cartRepository;

//	@Autowired
//	private DiscountRepository discountRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ItemDto createItem(ItemDto itemDto, Integer cartId, Integer productId) {
		Product product = this.productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product ", "productId", productId));

		Cart cart = this.cartRepository.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart ", "cartId", cartId));

		Item item = this.modelMapper.map(itemDto, Item.class);

		item.setProduct(product);
		item.setCart(cart);
		item.setPrice(product.getPrice() * itemDto.getQuantity());
		Item newItem = this.itemRepository.save(item);
		return this.modelMapper.map(newItem, ItemDto.class);
	}

//	@Override
//	public ResItemDiscountDto createItemDiscount(ItemDiscountDto itemDto, Integer cartId, Integer productId) {
//		Product product = this.productRepository.findById(productId)
//				.orElseThrow(() -> new ResourceNotFoundException("Product ", "productId", productId));
//
//		Cart cart = this.cartRepository.findById(cartId)
//				.orElseThrow(() -> new ResourceNotFoundException("Cart ", "cartId", cartId));
//	
//		Item item = this.modelMapper.map(itemDto, Item.class);
//
//		Discount dis = this.discountRepository.findDiscountByCode(itemDto.getCode());
//		
//		item.setDiscount(dis);
//		item.setProduct(product);
//		item.setCart(cart);
//		item.setPrice(product.getPrice() * itemDto.getQuantity()* dis.getPercent());
//		Item newItem = this.itemRepository.save(item);
//		return this.modelMapper.map(newItem, ResItemDiscountDto.class);
//	}
	
//	@Override
//	public ResItemDiscountDto applyDiscount(Integer itemId, String code) {
//		Item item = this.itemRepository.findById(itemId)
//				.orElseThrow(() -> new ResourceNotFoundException("Item ", "ItemId", itemId));
//		Discount dis = this.discountRepository.findDiscountByCode(code);
//		item.setDiscount(dis);
//		item.setPrice(item.getPrice() * dis.getPercent());
//		Item applyItem = this.itemRepository.save(item);
//
//		return this.modelMapper.map(applyItem, ResItemDiscountDto.class);
//	}
	
	@Override
	public ItemDto updateItem(ItemDto itemDto, Integer itemId) {
		Item item = this.itemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Item ", "ItemId", itemId));
		Product product = this.productRepository.findById(item.getProduct().getId())
				.orElseThrow(() -> new ResourceNotFoundException("Product ", "productId", item.getProduct().getId()));
		item.setQuantity(itemDto.getQuantity());
//		code cua item khi co discount
//		if(item.getDiscount()== null) {
//			item.setPrice(product.getPrice() * itemDto.getQuantity());
//		}else {
//			item.setPrice(product.getPrice() * itemDto.getQuantity() * item.getDiscount().getPercent());
//		}
		item.setPrice(product.getPrice() * itemDto.getQuantity());	
		Item updateItem = this.itemRepository.save(item);
		return this.modelMapper.map(updateItem, ItemDto.class);
	}

	@Override
	public void deleteItem(Integer itemId) {
		Item item = this.itemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Item", "ItemId", itemId));
		this.itemRepository.delete(item);

	}

	@Override
	public List<ItemDto> getItemsByCart(Integer cartId) {
		Cart cart = this.cartRepository.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));
		List<Item> items = this.itemRepository.findByCart(cart);
		List<ItemDto> itemDtos = items.stream().map((item) -> this.modelMapper.map(item, ItemDto.class))
				.collect(Collectors.toList());

		return itemDtos;
	}

	@Override
	public List<ItemDto> getItemsCurrentByCart(Integer cartId) {
		List<Item> items = this.itemRepository.findItemsCurrentByCart(cartId);
		List<ItemDto> itemDtos = items.stream().map((item) -> this.modelMapper.map(item, ItemDto.class))
				.collect(Collectors.toList());
		return itemDtos;
	}
	
	@Override
	public Entry<Integer, Integer> getProductTopOrder(HashMap<Integer, Integer> listProducts) {
		Map.Entry<Integer, Integer> maxPrice = null;
        for (Map.Entry<Integer, Integer> price : listProducts.entrySet()) {
            if (maxPrice == null || price.getValue().compareTo(maxPrice.getValue()) > 0) {
                maxPrice = price;
            }
        }
        listProducts.remove(maxPrice.getKey());
        return maxPrice;
	}
	
}
