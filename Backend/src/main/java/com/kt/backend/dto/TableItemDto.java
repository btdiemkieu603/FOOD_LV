package com.kt.backend.dto;

import java.math.BigDecimal;

//import com.kt.backend.entity.Discount;
//import com.kt.backend.entity.Product;

//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

//@Setter
//@Getter
@NoArgsConstructor
public class TableItemDto {

	 private Integer tableItemId; // ID của món ăn trong đơn
	    private Integer orderTId; // ID của đơn hàng
	    private Integer productId; // ID của món ăn
	    private Integer quantity; // Số lượng món ăn
	    private Double price; // Giá của món ăn
	    private Integer tableId;   
	 // Getters and Setters
	   
		
		public Integer getTableItemId() {
			return tableItemId;
		}
//		public Integer getOrderTableId() {
//			return orderTableId;
//		}
//		public void setOrderTableId(Integer orderTableId) {
//			this.orderTableId = orderTableId;
//		}
		
		public Integer getTableId() {
			return tableId;
		}
		public Integer getOrderTId() {
			return orderTId;
		}

		public void setOrderTId(Integer orderTId) {
			this.orderTId = orderTId;
		}

		public void setTableId(Integer tableId) {
			this.tableId = tableId;
		}
		public void setTableItemId(Integer tableItemId) {
			this.tableItemId = tableItemId;
		}
		
		public Integer getProductId() {
			return productId;
		}
		public void setProductId(Integer productId) {
			this.productId = productId;
		}
		public Integer getQuantity() {
			return quantity;
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
		
	
	
	
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
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
//	public Discount getDiscount() {
//		return discount;
//	}
//
//	public void setDiscount(Discount discount) {
//		this.discount = discount;
//	}
//
//	private Integer id;
//	
//	private int quantity;
//	
//	private Double price;
//
//	private Product product;
//	
//	private Discount discount;
	
}
