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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kt.backend.dto.OrderDiscountDto;
import com.kt.backend.dto.OrderTableDto;

import com.kt.backend.dto.ResOrderDto;
import com.kt.backend.dto.TableItemDto;
import com.kt.backend.response.ApiResponse;
import com.kt.backend.service.OrderTableService;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://192.168.234.28:30030")
@CrossOrigin(origins = "${allowedOrigins}")
@RequestMapping("/api/orderTable")
public class OrderTableController {
	
	 @Autowired
	    private OrderTableService orderTableService;

	 
	    @PostMapping("/add")
	    public ResponseEntity<OrderTableDto> createOrder(@RequestBody OrderTableDto orderTableDto) {
	        OrderTableDto createdOrder = orderTableService.createOrder(orderTableDto);
	        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
	    }

	    @GetMapping("/all")
	    public ResponseEntity<List<OrderTableDto>> getAllOrders() {
	        List<OrderTableDto> orders = orderTableService.getAllOrders();
	        return ResponseEntity.ok(orders);
	    }

//	    @PutMapping("/{id}")
//	    public ResponseEntity<OrderTableDto> updateOrder(@PathVariable Integer id, @RequestBody OrderTableDto orderTableDto) {
//	        OrderTableDto updatedOrder = orderTableService.updateOrder(orderTableDto, id);
//	        return ResponseEntity.ok(updatedOrder);
//	    }
	    
//	    // Cập nhật đơn hàng theo ID
//	    @PutMapping("/{orderTableId}")
//	    public ResponseEntity<OrderTableDto> updateOrder(@PathVariable Integer orderTableId, @RequestBody OrderTableDto orderTableDto) {
//	        OrderTableDto updatedOrder = orderTableService.updateOrder(orderTableId, orderTableDto);
//	        return ResponseEntity.ok(updatedOrder);
//	    }

	    
	    // Cập nhật đơn hàng
	    @PutMapping("/{orderTableId}")
	    public ResponseEntity<OrderTableDto> updateOrder(
	            @PathVariable Integer orderTableId, @RequestBody OrderTableDto orderTableDto) {
	        OrderTableDto updatedOrder = orderTableService.updateOrder(orderTableId, orderTableDto);
	        return ResponseEntity.ok(updatedOrder);
	    }

	    // Thêm món ăn vào đơn hàng
//	    @PostMapping("/{orderTableId}/items")
//	    public ResponseEntity<Void> addTableItem(@PathVariable Integer orderTableId, @RequestBody TableItemDto tableItemDto) {
//	        orderTableService.addTableItem(orderTableId, tableItemDto);
//	        return ResponseEntity.ok().build();
//	    }
//
//	    // Cập nhật món ăn trong đơn hàng
//	    @PutMapping("/{orderTableId}/items/{tableItemId}")
//	    public ResponseEntity<Void> updateTableItem(@PathVariable Integer orderTableId, @PathVariable Integer tableItemId, @RequestBody TableItemDto tableItemDto) {
//	        orderTableService.updateTableItem(orderTableId, tableItemId, tableItemDto);
//	        return ResponseEntity.ok().build();
//	    }
//
//	    // Xóa món ăn khỏi đơn hàng
//	    @DeleteMapping("/{orderTableId}/items/{tableItemId}")
//	    public ResponseEntity<Void> deleteTableItem(@PathVariable Integer orderTableId, @PathVariable Integer tableItemId) {
//	        orderTableService.deleteTableItem(orderTableId, tableItemId);
//	        return ResponseEntity.ok().build();
//	    }
	    
//	    // Thêm món ăn vào đơn hàng
//	    @PostMapping("/{orderTableId}/items")
//	    public ResponseEntity<TableItemDto> addTableItem(@PathVariable Integer orderTableId, @RequestBody TableItemDto tableItemDto) {
//	        TableItemDto newTableItem = orderTableService.addTableItem(orderTableId, tableItemDto);
//	        return ResponseEntity.ok(newTableItem);
//	    }

	    @PostMapping("/add/{orderTableId}/{tableId}/{foodId}")
	    public ResponseEntity<TableItemDto> addTableItemToOrder(
	            @PathVariable Integer orderTableId,
	            @PathVariable Integer tableId,
	            @PathVariable Integer foodId,
	            @RequestParam int quantity) {
	          //  @RequestBody Integer quantity) {
	        TableItemDto newTableItem = orderTableService.addTableItemToOrder(orderTableId, tableId, foodId, quantity);
	        return ResponseEntity.ok(newTableItem);
	    }
	    
	    // Cập nhật món ăn trong đơn hàng
//	    @PutMapping("/{orderTableId}/items/{tableItemId}")
//	    public ResponseEntity<TableItemDto> updateTableItem(@PathVariable Integer orderTableId, @PathVariable Integer tableItemId, @RequestBody TableItemDto tableItemDto) {
//	        TableItemDto updatedTableItem = orderTableService.updateTableItem(orderTableId, tableItemId, tableItemDto);
//	        return ResponseEntity.ok(updatedTableItem);
//	    }
	    
	    @PutMapping("/{orderTableId}/tableItem/{tableItemId}")
	    public ResponseEntity<TableItemDto> updateTableItemQuantity(
	            @PathVariable Integer orderTableId,
	            @PathVariable Integer tableItemId,
	            @RequestParam int newQuantity) {

	        TableItemDto updatedTableItem = orderTableService.updateTableItemQuantity(orderTableId, tableItemId, newQuantity);
	        return ResponseEntity.ok(updatedTableItem);
	    }

	    // Xóa món ăn khỏi đơn hàng
	    @DeleteMapping("/{orderTableId}/items/{tableItemId}")
	    public ResponseEntity<Void> deleteTableItem(@PathVariable Integer orderTableId, @PathVariable Integer tableItemId) {
	        orderTableService.deleteTableItem(orderTableId, tableItemId);
	        return ResponseEntity.ok().build();
	    }
	    
	    @PutMapping("/{orderTableId}/status/{orderStatusId}")
	    public ResponseEntity<OrderTableDto> updateOrderStatus(
	            @PathVariable Integer orderTableId,
	            @PathVariable Integer orderStatusId) {

	        OrderTableDto updatedOrderTable = orderTableService.updateOrderStatus(orderTableId, orderStatusId);
	        return ResponseEntity.ok(updatedOrderTable);
	    }
	    
	    @DeleteMapping("/{id}")
	    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable Integer id) {
	        orderTableService.deleteOrder(id);
	        return new ResponseEntity<>(new ApiResponse("Order deleted successfully", true), HttpStatus.OK);
	    }
    
	    @GetMapping("/{orderTableId}")
	    public OrderTableDto getOrderTableById(@PathVariable Integer orderTableId) {
	        return orderTableService.getOrderTableById(orderTableId);
	    }

//	
//	    @Override
//	    public TableItemDto addTableItem(Integer orderTableId, TableItemDto tableItemDto) {
//	        OrderTable orderTable = orderTableRepository.findById(orderTableId)
//	                .orElseThrow(() -> new ResourceNotFoundException("OrderTable", "orderTableId", orderTableId));
//	        
//	        TableItem tableItem = modelMapper.map(tableItemDto, TableItem.class);
//	        tableItem.setOrderTable(orderTable);
//	        
//	        TableItem savedItem = tableItemRepository.save(tableItem);
//	        orderTable.updateTotalAmount();
//	        orderTableRepository.save(orderTable);
//
//	        return modelMapper.map(savedItem, TableItemDto.class);
//	    }
//
//	    @Override
//	    public TableItemDto updateTableItem(Integer orderTableId, Integer itemId, TableItemDto tableItemDto) {
//	        TableItem tableItem = tableItemRepository.findById(itemId)
//	                .orElseThrow(() -> new ResourceNotFoundException("TableItem", "itemId", itemId));
//
//	        tableItem.setQuantity(tableItemDto.getQuantity());
//	        tableItem.setPrice(tableItemDto.getPrice());
//
//	        TableItem updatedItem = tableItemRepository.save(tableItem);
//	        OrderTable orderTable = updatedItem.getOrderTable();
//	        orderTable.updateTotalAmount();
//	        orderTableRepository.save(orderTable);
//
//	        return modelMapper.map(updatedItem, TableItemDto.class);
//	    }
//
//	    @Override
//	    public void deleteTableItem(Integer orderTableId, Integer itemId) {
//	        TableItem tableItem = tableItemRepository.findById(itemId)
//	                .orElseThrow(() -> new ResourceNotFoundException("TableItem", "itemId", itemId));
//
//	        tableItemRepository.delete(tableItem);
//
//	        OrderTable orderTable = tableItem.getOrderTable();
//	        orderTable.updateTotalAmount();
//	        orderTableRepository.save(orderTable);
//	    }  
	    
//	@Autowired
//	private OrderTableService orderService;
//	
//	@PostMapping("/add/{accountId}/{checkoutId}")
//	public ResponseEntity<ResOrderDto> createOrder(
//			@RequestBody OrderDto orderDto,
//			@PathVariable Integer accountId,
//			@PathVariable Integer checkoutId
//			) {
//		ResOrderDto createOrder = this.orderService.createOrder(orderDto, accountId, checkoutId);
//		return new ResponseEntity<ResOrderDto>(createOrder, HttpStatus.CREATED);
//	}
//	
//	@PostMapping("/addDiscount/{accountId}/{checkoutId}")
//	public ResponseEntity<ResOrderDto> createOrderDiscount(
//			@RequestBody OrderDiscountDto orderDiscountDto,
//			@PathVariable Integer accountId,
//			@PathVariable Integer checkoutId
//			) {
//		ResOrderDto createOrder = this.orderService.createOrderDiscount(orderDiscountDto, accountId, checkoutId);
//		return new ResponseEntity<ResOrderDto>(createOrder, HttpStatus.CREATED);
//	}
//	
//	@PutMapping("/discount/{orderId}/{code}")
//	public ResponseEntity<ResOrderDto> applyDiscountForOrder(
//			@PathVariable Integer orderId,
//			@PathVariable String code) {
//		ResOrderDto applyOrder = this.orderService.applyDiscountForOrder(orderId, code);
//	return new ResponseEntity<ResOrderDto>(applyOrder, HttpStatus.OK);
//	}
//	
//	@GetMapping("/{orderId}")
//	public ResponseEntity<OrderDto> getOrder(@PathVariable Integer orderId) {
//		OrderDto orderDto = this.orderService.getOrder(orderId);
//		return new ResponseEntity<OrderDto>(orderDto, HttpStatus.OK);
//	}
//
//	@GetMapping("/totalOrder/account/{accountId}")
//	public ResponseEntity<Integer> getTotalOrderByCustomer(@PathVariable Integer accountId) {	
//		return new ResponseEntity<Integer>(this.orderService.getTotalOrderByCustomer(accountId), HttpStatus.OK);
//	}
//	
//	@DeleteMapping("/{orderId}")
//	public ResponseEntity<ApiResponse> deleteOrderById(@PathVariable Integer orderId) {
//		this.orderService.deleteOrder(orderId);
//		return new ResponseEntity<ApiResponse>(new ApiResponse("Order is deleted successfully!", true),
//				HttpStatus.OK);
//	}	
//	
//	@PutMapping("/{orderId}/{orderStatusId}")
//	public ResponseEntity<ResOrderDto> changeOrderStatusById(@PathVariable Integer orderId,
//			@PathVariable Integer orderStatusId
//			) {
//		ResOrderDto updatedOr = this.orderService.changeStatusOfOrder(orderId, orderStatusId);
//		return new ResponseEntity<ResOrderDto>(updatedOr, HttpStatus.OK);
//	}	
//	
//	@GetMapping("/toporder")
//	public ResponseEntity<List<ProductDto>> getTopOrderCurrent() {
//		List<ProductDto> productDtos = this.orderService.getThreeProductBestOrder();
//		return new ResponseEntity<List<ProductDto>>(productDtos, HttpStatus.OK);
//	}
//	
//	@GetMapping("/orderStatus/{orderStatusId}")
//	public ResponseEntity<List<ResOrderDto>> getOrdersByOrderStatusID(@PathVariable Integer orderStatusId) {
//		List<ResOrderDto> orderDtos = this.orderService.getOrdersByOrderStatus(orderStatusId);
//		return new ResponseEntity<List<ResOrderDto>>(orderDtos, HttpStatus.OK);
//	}
//	
//	@GetMapping("/{accountId}/{orderStatusId}")
//	public ResponseEntity<List<ResOrderDto>> fillterOrderByAccountAndAcStatus(
//			@PathVariable Integer accountId,
//			@PathVariable Integer orderStatusId
//			) {
//		List<ResOrderDto> orderDtos = this.orderService.getOrdersByAccountAndAcStatus(accountId, orderStatusId);
//		return new ResponseEntity<List<ResOrderDto>>(orderDtos, HttpStatus.OK);
//	}
//	
//	@GetMapping("/all")
//	public ResponseEntity<List<ResOrderDto>> getAllOrder() {
//		List<ResOrderDto> orderDtos = this.orderService.getAllOrder();
//		return new ResponseEntity<List<ResOrderDto>>(orderDtos, HttpStatus.OK);
//	}
//	
//	@GetMapping("/purchases/")
//	public ResponseEntity<Integer> getTotalPurchases() {	
//		return new ResponseEntity<Integer>(this.orderService.getAllPurchasesInStore(), HttpStatus.OK);
//	}
//	
//	@GetMapping("/purchases/{productId}")
//	public ResponseEntity<Integer> getTotalPurchasesOfProduct(@PathVariable Integer productId) {	
//		return new ResponseEntity<Integer>(this.orderService.getAllPurchasesByProductInStore(productId), HttpStatus.OK);
//	}
}
