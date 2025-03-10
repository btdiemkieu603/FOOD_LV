package com.kt.backend.service;

import java.util.List;

import com.kt.backend.dto.OrderDiscountDto;
import com.kt.backend.dto.OrderDto;
import com.kt.backend.dto.ProductDto;
import com.kt.backend.dto.ResOrderDto;

public interface OrderService {

	ResOrderDto createOrder(OrderDto orderDto, Integer accountId, Integer checkoutId);
	
	OrderDto getOrder(Integer orderId);
	
	void deleteOrder(Integer orderId);
	
	ResOrderDto changeStatusOfOrder(Integer orderId, Integer orderStatusId);
	
	List<ProductDto> getThreeProductBestOrder();
	
	List<ResOrderDto> getOrdersByOrderStatus(Integer orderStatusId);
	
	List<ResOrderDto> getOrdersByAccountAndAcStatus(Integer accountId, Integer orderStatusId);
	
	Integer getTotalOrderByCustomer(Integer accountId);
	
	ResOrderDto applyDiscountForOrder(Integer orderId, String code);
	
	ResOrderDto createOrderDiscount(OrderDiscountDto orderDiscountDto, Integer accountId, Integer checkoutId);
	
	List<ResOrderDto> getAllOrder();
	
Integer getAllPurchasesInStore();
	
	Integer getAllPurchasesByProductInStore(Integer productId);
}
