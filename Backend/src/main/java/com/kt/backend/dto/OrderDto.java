package com.kt.backend.dto;

//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

//@Setter
//@Getter
@NoArgsConstructor
public class OrderDto {

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

	private Integer id;

	private String orderdate;
	
}
