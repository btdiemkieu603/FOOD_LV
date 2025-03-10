package com.kt.backend.dto;

//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

//@Setter
//@Getter
@NoArgsConstructor
public class CategoryDto {
	private Integer id;
	private String title;
	private String url_image_category;
	 private Boolean isExist;
	 

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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl_image_category() {
		return url_image_category;
	}
	public void setUrl_image_category(String url_image_category) {
		this.url_image_category = url_image_category;
	}
	 
}
