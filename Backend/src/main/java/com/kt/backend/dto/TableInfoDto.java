package com.kt.backend.dto;

import java.util.List;

import com.kt.backend.entity.TableItem;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

//@Setter
//@Getter
@NoArgsConstructor
public class TableInfoDto {
	
	private Integer tableId; // Mã bàn
    private Integer tableNumber; // Số bàn
    private Integer seatingCapacity; // Số lượng chỗ ngồi
    private String status; // Trạng thái bàn
    private Boolean isExist; 
  
public Boolean getIsExist() {
		return isExist;
	}
	public void setIsExist(Boolean isExist) {
		this.isExist = isExist;
	}
private List<TableItemDto> tableItems;

	//get set
    public List<TableItemDto> getTableItems() {
		return tableItems;
	}
	public void setTableItems(List<TableItemDto> tableItems) {
		this.tableItems = tableItems;
	}
	public Integer getTableId() {
		return tableId;
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
