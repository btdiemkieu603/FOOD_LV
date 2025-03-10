package com.kt.backend.service;

import java.util.List;

import com.kt.backend.dto.OrderDiscountDto;
import com.kt.backend.dto.OrderTableDto;
//import com.kt.backend.dto.FoodDto;
import com.kt.backend.dto.ResOrderDto;
import com.kt.backend.dto.TableItemDto;
import com.kt.backend.entity.OrderTable;

public interface OrderTableService {
	
	OrderTableDto createOrder(OrderTableDto orderTableDto);
    OrderTableDto updateOrder(Integer orderTableId, OrderTableDto orderTableDto);
    void deleteOrder(Integer orderTableId);
    List<OrderTableDto> getAllOrders();
    
    OrderTableDto getOrderTableById(Integer orderTableId);
    
//    void addTableItem(Integer orderTableId, TableItemDto tableItemDto);
//    void updateTableItem(Integer orderTableId, Integer tableItemId, TableItemDto tableItemDto);
//    void deleteTableItem(Integer orderTableId, Integer tableItemId);

    
   // TableItemDto addTableItem(Integer orderTableId, TableItemDto tableItemDto);
   // TableItemDto updateTableItem(Integer orderTableId, Integer tableItemId, TableItemDto tableItemDto);
    void deleteTableItem(Integer orderTableId, Integer tableItemId);
    
    
    TableItemDto addTableItemToOrder(Integer orderTableId, Integer tableId, Integer productId, int quantity);
    
    TableItemDto updateTableItemQuantity(Integer orderTableId, Integer tableItemId,int  newQuantity);
    
    OrderTableDto updateOrderStatus(Integer orderTableId, Integer orderStatusId);
//	ResOrderDto createOrder(OrderDto orderDto, Integer accountId, Integer checkoutId);
//	
//	OrderDto getOrder(Integer orderId);
//	
//	void deleteOrder(Integer orderId);
//	
//	ResOrderDto changeStatusOfOrder(Integer orderId, Integer orderStatusId);
//	
//	List<ProductDto> getThreeProductBestOrder();
//	
//	List<ResOrderDto> getOrdersByOrderStatus(Integer orderStatusId);
//	
//	List<ResOrderDto> getOrdersByAccountAndAcStatus(Integer accountId, Integer orderStatusId);
//	
//	Integer getTotalOrderByCustomer(Integer accountId);
//	
//	ResOrderDto applyDiscountForOrder(Integer orderId, String code);
//	
//	ResOrderDto createOrderDiscount(OrderDiscountDto orderDiscountDto, Integer accountId, Integer checkoutId);
//	
//	List<ResOrderDto> getAllOrder();
//	
//Integer getAllPurchasesInStore();
//	
//	Integer getAllPurchasesByProductInStore(Integer productId);
}
