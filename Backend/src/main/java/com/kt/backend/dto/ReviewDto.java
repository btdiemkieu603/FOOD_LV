package com.kt.backend.dto;

import com.kt.backend.entity.Account;
import com.kt.backend.entity.Product;

//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

//@Setter
//@Getter
@NoArgsConstructor
public class ReviewDto {

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumberofstar() {
		return numberofstar;
	}

	public void setNumberofstar(Integer numberofstar) {
		this.numberofstar = numberofstar;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDatereview() {
		return datereview;
	}

	public void setDatereview(String datereview) {
		this.datereview = datereview;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	private Integer id;
	
	private Integer numberofstar;
	
	private String content;
	
	private String datereview;
	
	private Account account;
	
	private Product product;
}
