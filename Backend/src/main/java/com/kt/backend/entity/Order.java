package com.kt.backend.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;


//@Setter
//@Getter
@NoArgsConstructor
@Entity
@Table(name = "order_food")
public class Order {

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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String orderdate;
	
	private Double totalprice;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "discount_id")
	private Discount discount;
	
	@JsonBackReference
	@ManyToOne
    @JoinColumn(name = "order_status_id")
	private OrderStatus orderStatus;
	
	@OneToOne
    @JoinColumn(name = "bill_id")
	private Bill bill;
	
	@ManyToOne
    @JoinColumn(name = "checkout_id")
	private CheckOut checkout;
	
	@JsonManagedReference
	@JsonIgnore
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Item> items;
}
