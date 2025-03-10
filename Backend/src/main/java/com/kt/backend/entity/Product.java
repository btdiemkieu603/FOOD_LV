package com.kt.backend.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

//@Setter
//@Getter
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
	private Boolean isExist;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	
	@JsonManagedReference
	@JsonIgnore
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items;
	
	@JsonManagedReference
	@JsonIgnore
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Review> reviews;
	
	
	public Boolean getIsExist() {
		return isExist;
	}

	public void setIsExist(Boolean isExist) {
		this.isExist = isExist;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl_image_product() {
		return url_image_product;
	}

	public void setUrl_image_product(String url_image_product) {
		this.url_image_product = url_image_product;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	@Column(name = "name",length = 200, nullable = false)
	private String name;
	
	@Column(name = "price", nullable = false)
	private Double price;
	
	@Column(name = "description",length = 500, nullable = false)
	private String description;
	
	@Column(name = "url_image_product", nullable = false)
	private String url_image_product;
	
	
	
}
