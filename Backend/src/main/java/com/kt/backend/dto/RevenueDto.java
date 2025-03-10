package com.kt.backend.dto;

//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

//@Setter
//@Getter
@NoArgsConstructor
public class RevenueDto {

	public Double getRevenue() {
		return revenue;
	}

	public void setRevenue(Double revenue) {
		this.revenue = revenue;
	}

	Double revenue;
	
}
