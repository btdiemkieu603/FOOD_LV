package com.kt.backend.service.Impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.backend.dto.BillDto;
import com.kt.backend.dto.OrderDiscountDto;
import com.kt.backend.dto.OrderDto;
import com.kt.backend.dto.ProductDto;
import com.kt.backend.dto.ResOrderDto;
import com.kt.backend.entity.Account;
import com.kt.backend.entity.Bill;
import com.kt.backend.entity.CheckOut;
import com.kt.backend.entity.Discount;
import com.kt.backend.entity.Item;
import com.kt.backend.entity.Order;
import com.kt.backend.entity.OrderStatus;
import com.kt.backend.exception.ResourceNotFoundException;
import com.kt.backend.repository.AccountRepository;
import com.kt.backend.repository.CheckOutRepository;
import com.kt.backend.repository.DiscountRepository;
import com.kt.backend.repository.ItemRepository;
import com.kt.backend.repository.OrderRepository;
import com.kt.backend.repository.OrderStatusRepository;
import com.kt.backend.repository.ProductRepository;
import com.kt.backend.service.BillService;
import com.kt.backend.service.CartService;
import com.kt.backend.service.ItemService;
import com.kt.backend.service.OrderService;
import com.kt.backend.service.ProductService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CheckOutRepository checkOutRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private DiscountRepository discountRepository;
	
	@Autowired
	private BillService billService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ProductService prodService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderStatusRepository orderStatusRepository;
	
	@Autowired
	private ModelMapper modelMapper;

//	public static String createOrderId() {
//        long timestamp = System.currentTimeMillis();
//        Random random = new Random();
//        int randomNum = random.nextInt(1000); // Tạo số ngẫu nhiên từ 0 đến 999
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//        String formattedDate = sdf.format(new Date()); // Định dạng ngày giờ theo "yyyyMMddHHmmss"
//        return "ORD" + formattedDate + randomNum; // Kết hợp thời gian và số ngẫu nhiên
//    }

	
	@Override
	public ResOrderDto createOrder(OrderDto orderDto, Integer accountId, Integer checkoutId) {
		Account account = this.accountRepository.findById(accountId).orElseThrow(()-> new ResourceNotFoundException("Account","AccountId", accountId));
		CheckOut checkout = this.checkOutRepository.findById(checkoutId).orElseThrow(()-> new ResourceNotFoundException("CheckOut","CheckOutId", checkoutId));
		Order order = this.modelMapper.map(orderDto, Order.class);
		order.setAccount(account);
		order.setCheckout(checkout);
		order.setItems(this.itemRepository.findItemsCurrentByCart(account.getCart().getId()));
		order.setTotalprice(this.cartService.getTotalPriceOfCartCurrent(account.getCart().getId()));
		BillDto billDto = new BillDto();
		billDto.setIssuedate(order.getOrderdate());
		billDto.setTotalprice(order.getTotalprice()+10000);
		BillDto responseBill = this.billService.createBill(billDto);		
		Bill bill = this.modelMapper.map(responseBill, Bill.class);
		order.setBill(bill);	
		OrderStatus orStatus = this.orderStatusRepository.findOrderStatusByStatusID(1);
		order.setOrderStatus(orStatus);
		Order addOrder = this.orderRepository.save(order);
		 // Update orderId for items in the list
	    List<Item> items = order.getItems();
	    for (Item item : items) {
	        item.setOrder(order); // Assuming there's a setter for orderId in the Item class
	    }
	    // Save the updated items back to the repository if needed
	    this.itemRepository.saveAll(items);
		return this.modelMapper.map(addOrder, ResOrderDto.class);
	}

	@Override
	public OrderDto getOrder(Integer orderId) {
		Order order = this.orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order","OrderId", orderId));
		return this.modelMapper.map(order, OrderDto.class);
	}

	@Override
	public void deleteOrder(Integer orderId) {
		Order order = this.orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order","OrderId", orderId));
		Integer billId = order.getBill().getId();
		this.orderRepository.delete(order);
		this.billService.deleteBill(billId);
	}

	@Override
	public ResOrderDto changeStatusOfOrder(Integer orderId, Integer orderStatusId) {
		Order order = this.orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order","OrderId", orderId));
		OrderStatus orderStatus = this.orderStatusRepository.findById(orderStatusId).orElseThrow(()-> new ResourceNotFoundException("OrderStatus","OrderStatusId", orderStatusId));
		order.setOrderStatus(orderStatus);
		Order updateOrder = this.orderRepository.save(order);		
		return this.modelMapper.map(updateOrder, ResOrderDto.class);
	}


	@Override
	public List<ProductDto> getThreeProductBestOrder() {
		HashMap<Integer, Integer> listProducts = new HashMap<>();
		for(Integer e: this.productRepository.getAllIdOfProduct()) {
			listProducts.put(e, 0);
		}
		ArrayList<Integer> listProductsOrder = new ArrayList<>();
		for(Integer e: this.itemRepository.getAllIdOfProductOrder()) {
			listProductsOrder.add(e);
		}
		for(Integer e: listProductsOrder) {
			listProducts.put(e, listProducts.get(e) + 1);
		}
		List<ProductDto> bestSeller = new ArrayList<>();
		bestSeller.add(this.prodService.getProductById(this.itemService.getProductTopOrder(listProducts).getKey()));
		bestSeller.add(this.prodService.getProductById(this.itemService.getProductTopOrder(listProducts).getKey()));
		bestSeller.add(this.prodService.getProductById(this.itemService.getProductTopOrder(listProducts).getKey()));
		return bestSeller;
	}

	@Override
	public List<ResOrderDto> getOrdersByOrderStatus(Integer orderStatusId) {
		OrderStatus order_status = this.orderStatusRepository.findById(orderStatusId).orElseThrow(()-> new ResourceNotFoundException("OrderStatus","OrderStatusId", orderStatusId));
		List<Order> orders = this.orderRepository.findByOrderStatus(order_status);
		List<ResOrderDto> orderDtos = orders.stream().map((order) -> this.modelMapper.map(order, ResOrderDto.class))
				.collect(Collectors.toList());	
		return orderDtos;
	}

	@Override
	public List<ResOrderDto> getOrdersByAccountAndAcStatus(Integer accountId, Integer orderStatusId) {
		Account account = this.accountRepository.findById(accountId).orElseThrow(()-> new ResourceNotFoundException("Account","AccountId", accountId));
		List<Order> orders = this.orderRepository.findByAccount(account);
		List<Order> ordersResult = new ArrayList<>();
		for(Order order: orders) {
			if(order.getOrderStatus().getId() == orderStatusId) {
				ordersResult.add(order);
			}
		}
		List<ResOrderDto> orderDtos = ordersResult.stream().map((order) -> this.modelMapper.map(order, ResOrderDto.class))
				.collect(Collectors.toList());	
		return orderDtos;
	}

	@Override
	public Integer getTotalOrderByCustomer(Integer accountId) {
		Account account = this.accountRepository.findById(accountId).orElseThrow(()-> new ResourceNotFoundException("Account","AccountId", accountId));
		List<Order> orders = this.orderRepository.findByAccount(account);
		if(orders == null) {
			return 0;
		}else {
			return orders.size();
		}	
	}

	@Override
	public ResOrderDto applyDiscountForOrder(Integer orderId, String code) {
		Order order = this.orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order ", "OrderId", orderId));
		if(order.getDiscount()==null) {
		Discount dis = this.discountRepository.findDiscountByCode(code);
		order.setDiscount(dis);
		order.setTotalprice(order.getTotalprice()*dis.getPercent());
		Order applyOrder = this.orderRepository.save(order);
		return this.modelMapper.map(applyOrder, ResOrderDto.class);
		}else {
			return this.modelMapper.map(order, ResOrderDto.class);
		}	
	}

	@Override
	public ResOrderDto createOrderDiscount(OrderDiscountDto orderDiscountDto, Integer accountId, Integer checkoutId) {
		Account account = this.accountRepository.findById(accountId).orElseThrow(()-> new ResourceNotFoundException("Account","AccountId", accountId));
		CheckOut checkout = this.checkOutRepository.findById(checkoutId).orElseThrow(()-> new ResourceNotFoundException("CheckOut","CheckOutId", checkoutId));
		Discount dis = this.discountRepository.findDiscountByCode(orderDiscountDto.getCode());		
		Order order = this.modelMapper.map(orderDiscountDto, Order.class);
		order.setAccount(account);
		order.setCheckout(checkout);
		order.setDiscount(dis);
		order.setItems(this.itemRepository.findItemsCurrentByCart(account.getCart().getId()));
		if(dis == null) {
			order.setTotalprice(this.cartService.getTotalPriceOfCartCurrent(account.getCart().getId()));
		}else {
			Double price = this.cartService.getTotalPriceOfCartCurrent(account.getCart().getId());
			order.setTotalprice(price - price*dis.getPercent());
		}
		BillDto billDto = new BillDto();
		billDto.setIssuedate(order.getOrderdate());
		billDto.setTotalprice(order.getTotalprice()+10000);
		BillDto responseBill = this.billService.createBill(billDto);		
		Bill bill = this.modelMapper.map(responseBill, Bill.class);
		order.setBill(bill);	
		OrderStatus orStatus = this.orderStatusRepository.findOrderStatusByStatusID(1);
		order.setOrderStatus(orStatus);
		Order addOrder = this.orderRepository.save(order);
		 // Update orderId for items in the list
	    List<Item> items = order.getItems();
	    for (Item item : items) {
	        item.setOrder(order); // Assuming there's a setter for orderId in the Item class
	    }
	    // Save the updated items back to the repository if needed
	    this.itemRepository.saveAll(items);
		return this.modelMapper.map(addOrder, ResOrderDto.class);
	}

	@Override
	public List<ResOrderDto> getAllOrder() {
		List<Order> orders = this.orderRepository.findAll();
		List<ResOrderDto> orderDtos = orders.stream().map((order) -> this.modelMapper.map(order, ResOrderDto.class))
				.collect(Collectors.toList());
		return orderDtos;
	} 
	
	@Override
	public Integer getAllPurchasesInStore() {
		return this.orderRepository.getAllPurchases();
	}

	@Override
	public Integer getAllPurchasesByProductInStore(Integer productId) {
		return this.orderRepository.getPurchasesByProduct(productId);
	}

}
