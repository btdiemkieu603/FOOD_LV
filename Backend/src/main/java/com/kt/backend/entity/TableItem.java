package com.kt.backend.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

//@Setter
//@Getter
@NoArgsConstructor
@Entity
@Table(name = "tableItem")
public class TableItem {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer tableItemId;  // Mã bảng item

//	 @ManyToOne
//	 @JoinColumn(name = "order_table_id", nullable = true)  // Không bắt buộc phải có orderTable khi tạo mới TableItem
//	 private OrderTable orderTable;

//	  @ManyToOne
//	    @JoinColumn(name = "order_table_id")
//	    private OrderTable orderTable;  // Mã đơn hàng tại quán

	 @ManyToOne
	 @JoinColumn(name = "order_table_id", nullable = true)
	
	 private OrderTable orderTable;
	 
	    @ManyToOne
	    @JoinColumn(name = "product_id")
	    private Product product;  // Mã món ăn

	    @Column(name = "quantity")
	    private Integer quantity;  // Số lượng món ăn

	    @Column(name = "price")
	    private Double price;  // Giá của món ăn
	    
//	    @ManyToOne
//	    @JoinColumn(name = "table_id")
//	    private TableInfo tableInfo;
	    @ManyToOne
	    @JoinColumn(name = "table_id")
	    @JsonIgnore  // Ngăn vòng lặp khi ánh xạ JSON
	    private TableInfo tableInfo;
	 // Getters and Setters
	   
		
		public Integer getTableItemId() {
			return tableItemId;
		}

		public TableInfo getTableInfo() {
			return tableInfo;
		}

		public void setTableInfo(TableInfo tableInfo) {
			this.tableInfo = tableInfo;
		}

		public void setTableItemId(Integer tableItemId) {
			this.tableItemId = tableItemId;
		}

		public OrderTable getOrderTable() {
			return orderTable;
		}

		public void setOrderTable(OrderTable orderTable) {
			this.orderTable = orderTable;
		}

//		public Food getFood() {
//			return food;
//		}
//
//		public void setFood(Food food) {
//			this.food = food;
//		}
		
		

		public Integer getQuantity() {
			return quantity;
		}

		public Product getProduct() {
			return product;
		}

		public void setProduct(Product product) {
			this.product = product;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		public Double getPrice() {
			return price;
		}

		public void setPrice(Double price) {
			this.price = price;
		}
	    
	
	   

	    
	    
	
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer Id;
//	
//	@Column(name = "quantity")
//	private int quantity;
//	
//	@Column(name = "price")
//	private Double price;
//	
//	
//	@ManyToOne
//	@JoinColumn(name = "product_id")
//	private Product product;
//	
//	
//	public Integer getId() {
//		return Id;
//	}
//
//	public void setId(Integer id) {
//		Id = id;
//	}
//
//	public int getQuantity() {
//		return quantity;
//	}
//
//	public void setQuantity(int quantity) {
//		this.quantity = quantity;
//	}
//
//	public Double getPrice() {
//		return price;
//	}
//
//	public void setPrice(Double price) {
//		this.price = price;
//	}
//
//	public Product getProduct() {
//		return product;
//	}
//
//	public void setProduct(Product product) {
//		this.product = product;
//	}
//
//	public Cart getCart() {
//		return cart;
//	}
//
//	public void setCart(Cart cart) {
//		this.cart = cart;
//	}
//
//	public Order getOrder() {
//		return order;
//	}
//
//	public void setOrder(Order order) {
//		this.order = order;
//	}
//
//	@JsonIgnore
//	@ManyToOne
//	@JoinColumn(name = "cart_id")
//	private Cart cart;
//	
////	@JsonBackReference
////	@ManyToOne
////	@JoinColumn(name = "discount_id")
////	private Discount discount;
//	
//	@JsonIgnore
//	@ManyToOne
//	@JoinColumn(name = "order_id")
//	private Order order;

}
