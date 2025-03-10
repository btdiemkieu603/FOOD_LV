package com.kt.backend.dto;

import java.util.List;

import com.kt.backend.entity.Account;
import com.kt.backend.entity.Bill;
import com.kt.backend.entity.CheckOut;
import com.kt.backend.entity.Discount;
import com.kt.backend.entity.Item;
import com.kt.backend.entity.OrderStatus;

//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

//@Setter
//@Getter
@NoArgsConstructor
public class ResOrderDto {


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

	public Double getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(Double totalprice) {
		this.totalprice = totalprice;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public CheckOut getCheckout() {
		return checkout;
	}

	public void setCheckout(CheckOut checkout) {
		this.checkout = checkout;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	private Integer id;
	
	private String orderdate;
	
	private Double totalprice;
	
	private Account account;
	
	private Discount discount;
	
	private OrderStatus orderStatus;

	private Bill bill;

	private CheckOut checkout;

	private List<Item> items;
}
