package com.kt.backend.dto;

import com.kt.backend.entity.Discount;

//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;


//@Setter
//@Getter
@NoArgsConstructor
public class ResItemDiscountDto {
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

	private Integer id;
	
	private int quantity;
	
	private Double price;
	
	private Discount discount;
}
