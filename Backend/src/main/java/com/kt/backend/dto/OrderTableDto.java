package com.kt.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderTableDto {

	private Integer orderTableId;
    private Integer tableId;  // ID của bàn
//    private String staffEmail; // Email của nhân viên
    private Integer accountId;
    private Integer orderStatusId; // ID của trạng thái đơn hàng
    private Double totalAmount; // Tổng số tiền
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime createdAt; // Thời gian tạo đơn
   // private Integer discountId; // ID của ưu đãi
    private List<TableItemDto> tableItems; // Danh sách các món ăn trong đơn hàngtrong đơn
    //get set
	public Integer getOrderTableId() {
		return orderTableId;
	}
	public void setOrderTableId(Integer orderTableId) {
		this.orderTableId = orderTableId;
	}
	public Integer getTableId() {
		return tableId;
	}
	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}
//	public String getStaffEmail() {
//		return staffEmail;
//	}
//	public void setStaffEmail(String staffEmail) {
//		this.staffEmail = staffEmail;
//	}
	
	public Integer getOrderStatusId() {
		return orderStatusId;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public void setOrderStatusId(Integer orderStatusId) {
		this.orderStatusId = orderStatusId;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
//	public Integer getDiscountId() {
//		return discountId;
//	}
//	public void setDiscountId(Integer discountId) {
//		this.discountId = discountId;
//	}
	public List<TableItemDto> getTableItems() {
		return tableItems;
	}
	public void setTableItems(List<TableItemDto> tableItems) {
		this.tableItems = tableItems;
	}
	
   
    
   

	

//
//	private Integer id;
//
//	private String orderdate;
	
}
