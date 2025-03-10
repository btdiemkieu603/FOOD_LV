package com.kt.backend.entity;

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

@NoArgsConstructor
//@Setter
//@Getter
@Entity
@Table(name = "review")
public class Review {

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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer numberofstar;
	
	private String content;
	
	private String datereview;
	
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

}
