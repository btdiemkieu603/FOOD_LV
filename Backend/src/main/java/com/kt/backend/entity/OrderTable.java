package com.kt.backend.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "order_table")
public class OrderTable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderTableId;
	
	@ManyToOne
    @JoinColumn(name = "table_id")
    private TableInfo tableInfo;
//
//    @ManyToOne
//    @JoinColumn(name = "staff_email")
//    private Staff staff;
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

    @ManyToOne
    @JoinColumn(name = "orderStatus_id")
    private OrderStatus orderStatus;

    @Column(name = "totalAmount")
    private Double totalAmount;

   
    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

//    @ManyToOne
//    @JoinColumn(name = "discount_id")
//    private Discount discount;
//    
    @OneToMany(mappedBy = "orderTable", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TableItem> tableItems = new ArrayList<>(); // Danh sách món ăn
    //get set

	public Integer getOrderTableId() {
		return orderTableId;
	}

	public void setOrderTableId(Integer orderTableId) {
		this.orderTableId = orderTableId;
	}

	public TableInfo getTableInfo() {
		return tableInfo;
	}

	public void setTableInfo(TableInfo tableInfo) {
		this.tableInfo = tableInfo;
	}

//	public Staff getStaff() {
//		return staff;
//	}
//
//	public void setStaff(Staff staff) {
//		this.staff = staff;
//	}

	
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
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

//	public Discount getDiscount() {
//		return discount;
//	}
//
//	public void setDiscount(Discount discount) {
//		this.discount = discount;
//	}

	public List<TableItem> getTableItems() {
		return tableItems;
	}

	public void setTableItems(List<TableItem> tableItems) {
		this.tableItems = tableItems;
	}
	




}
