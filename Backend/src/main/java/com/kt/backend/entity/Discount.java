package com.kt.backend.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

//@Setter
//@Getter
@NoArgsConstructor
@Entity
@Table(name = "discount")
public class Discount {

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public Boolean getIsExist() {
		return isExist;
	}

	public void setIsExist(Boolean isExist) {
		this.isExist = isExist;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String code;
	
	private Double percent;
	
	private Boolean isExist;
	
	private String startdate;
	
	private String enddate;
	
//	@JsonManagedReference
//	@JsonIgnore
//	@OneToMany(mappedBy = "discount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<Item> items = new ArrayList<>();
	
	@JsonManagedReference
	@JsonIgnore
	@OneToMany(mappedBy = "discount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Order> orders = new ArrayList<>();
}
