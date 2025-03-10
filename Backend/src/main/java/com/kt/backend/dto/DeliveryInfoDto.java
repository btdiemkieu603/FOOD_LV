package com.kt.backend.dto;

import java.util.Date;

//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

//@Setter
//@Getter
@NoArgsConstructor
public class DeliveryInfoDto {

	private Integer deliveryInfoId;
    private Integer orderOnlineId; // Chỉ định ID đơn hàng online
    private Integer addressId; // Chỉ định ID địa chỉ
    private String staffEmail;
    private Double deliveryFee;
    private Date deliveryTime;
    // get set
	public Integer getDeliveryInfoId() {
		return deliveryInfoId;
	}
	public void setDeliveryInfoId(Integer deliveryInfoId) {
		this.deliveryInfoId = deliveryInfoId;
	}
	public Integer getOrderOnlineId() {
		return orderOnlineId;
	}
	public void setOrderOnlineId(Integer orderOnlineId) {
		this.orderOnlineId = orderOnlineId;
	}
	public Integer getAddressId() {
		return addressId;
	}
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	public String getStaffEmail() {
		return staffEmail;
	}
	public void setStaffEmail(String staffEmail) {
		this.staffEmail = staffEmail;
	}
	public Double getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(Double deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
    
}
