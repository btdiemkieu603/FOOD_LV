package com.kt.backend.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

//@Setter
//@Getter
@NoArgsConstructor
public class BillTableDto {
	 private Integer billId;
	    private Integer orderTId;
//	    private Double totalItemAmount;  // Tổng giá trị hàng hóa
	    private Double receivedAmount;   // Số tiền khách hàng đưa
	    private Double changeAmount;     // Số tiền thối lại
	    private Double totalPaymentAmount; // Tổng số tiền thanh toán
	   //them dinh dang ngay
	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
	    private LocalDateTime createdAt;
	    //get set
		public Integer getBillId() {
			return billId;
		}
		public void setBillId(Integer billId) {
			this.billId = billId;
		}
//		public Integer getOrderTableId() {
//			return orderTableId;
//		}
//		public void setOrderTableId(Integer orderTableId) {
//			this.orderTableId = orderTableId;
//		}
		
//		public Double getTotalItemAmount() {
//			return totalItemAmount;
//		}
//		public void setTotalItemAmount(Double totalItemAmount) {
//			this.totalItemAmount = totalItemAmount;
//		}
		public Double getReceivedAmount() {
			return receivedAmount;
		}
		public Integer getOrderTId() {
			return orderTId;
		}
		public void setOrderTId(Integer orderTId) {
			this.orderTId = orderTId;
		}
		public void setReceivedAmount(Double receivedAmount) {
			this.receivedAmount = receivedAmount;
		}
		public Double getChangeAmount() {
			return changeAmount;
		}
		public void setChangeAmount(Double changeAmount) {
			this.changeAmount = changeAmount;
		}
		public Double getTotalPaymentAmount() {
			return totalPaymentAmount;
		}
		public void setTotalPaymentAmount(Double totalPaymentAmount) {
			this.totalPaymentAmount = totalPaymentAmount;
		}
		public LocalDateTime getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}
	    
}
