package com.kt.backend.dto;

import com.kt.backend.entity.Category;

//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

//@Setter
//@Getter
@NoArgsConstructor
public class ProductDto {
	private Integer id;
	private String name;
	private Double price;
	private String description;
	private Boolean isExist;
	private String url_image_product;
	private Category category;
	
	 
	public Boolean getIsExist() {
		return isExist;
	}
	public void setIsExist(Boolean isExist) {
		this.isExist = isExist;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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

}
