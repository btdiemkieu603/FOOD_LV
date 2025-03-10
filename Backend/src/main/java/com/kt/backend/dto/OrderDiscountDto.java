package com.kt.backend.dto;

//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

//@Setter
//@Getter
@NoArgsConstructor
public class OrderDiscountDto {

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private Integer id;

	private String orderdate;
	
	private String code;
	
}
