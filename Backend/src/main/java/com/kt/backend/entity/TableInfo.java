package com.kt.backend.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

//@Setter
//@Getter
@NoArgsConstructor
@Entity
@Table(name="tableInfo")
public class TableInfo {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tableId; // Mã bàn

    private Integer tableNumber; // Số bàn

    private Integer seatingCapacity; // Số lượng chỗ ngồi

    private String status; // Trạng thái bàn (ví dụ: "trống", "đang phục vụ")

    @OneToMany(mappedBy = "tableInfo", cascade = CascadeType.ALL)
    private List<TableItem> tableItems;
    
    private Boolean isExist;
    //get set

	public Integer getTableId() {
		return tableId;
	}


	public Boolean getIsExist() {
		return isExist;
	}



	public void setIsExist(Boolean isExist) {
		this.isExist = isExist;
	}



	public List<TableItem> getTableItems() {
		return tableItems;
	}

	public void setTableItems(List<TableItem> tableItems) {
		this.tableItems = tableItems;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

	public Integer getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(Integer tableNumber) {
		this.tableNumber = tableNumber;
	}

	public Integer getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setSeatingCapacity(Integer seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
}
