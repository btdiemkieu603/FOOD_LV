package com.kt.backend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.kt.backend.dto.TableItemDto;
import com.kt.backend.entity.TableItem;

public interface TableItemService {
	
	//TableItemDto createTableItem(TableItemDto tableItemDto);
	TableItemDto createTableItem(TableItemDto tableItemDto, Integer tableId, Integer productId);
	
    TableItemDto updateTableItem(TableItemDto tableItemDto, Integer tableItemId);
    void deleteTableItem(Integer tableItemId);
    List<TableItemDto> getAllTableItems();
    
    void deleteTableItemsByOrderTableIsNullAndTableId(Integer tableId);
	
//	ItemDto createItem(ItemDto itemDto, Integer cartId, Integer productId);
//	
////	ResItemDiscountDto createItemDiscount(ItemDiscountDto itemDto, Integer cartId, Integer productId);
//	
////	ResItemDiscountDto applyDiscount(Integer itemId, String code);
//	
//	ItemDto updateItem(ItemDto itemDto, Integer itemId);
//	
//	void deleteItem(Integer itemId);
//	
//	List<ItemDto> getItemsByCart(Integer cartId);
//	
//	List<ItemDto> getItemsCurrentByCart(Integer cartId);
//	
//	Map.Entry<Integer, Integer> getProductTopOrder(HashMap<Integer, Integer> listProducts);
}
