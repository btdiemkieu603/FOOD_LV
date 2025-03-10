package com.kt.backend.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

//@Setter
//@Getter
@NoArgsConstructor
@Entity
@Table(name = "billTable")
public class BillTable {

	
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer billId;

	    @ManyToOne
	    @JoinColumn(name = "orderTable_id", nullable = false)
	    private OrderTable orderTable;

//	    @Column(name = "totalItemAmount", nullable = false)
//	    private Double totalItemAmount; // Tổng giá trị hàng hóa

	    @Column(name = "receivedAmount", nullable = false)
	    private Double receivedAmount; // Số tiền khách hàng đưa

	    @Column(name = "changeAmount", nullable = false)
	    private Double changeAmount; // Số tiền thối lại

	    @Column(name = "totalPaymentAmount", nullable = false)
	    private Double totalPaymentAmount; // Tổng số tiền thanh toán (đã trừ khuyến mãi)

	    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	    private LocalDateTime createdAt;
	    // get set

		public Integer getBillId() {
			return billId;
		}

		public void setBillId(Integer billId) {
			this.billId = billId;
		}

		public OrderTable getOrderTable() {
			return orderTable;
		}

		public void setOrderTable(OrderTable orderTable) {
			this.orderTable = orderTable;
		}

//		public Double getTotalItemAmount() {
//			return totalItemAmount;
//		}
//
//		public void setTotalItemAmount(Double totalItemAmount) {
//			this.totalItemAmount = totalItemAmount;
//		}

		public Double getReceivedAmount() {
			return receivedAmount;
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
