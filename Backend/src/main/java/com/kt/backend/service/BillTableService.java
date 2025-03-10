package com.kt.backend.service;


import com.kt.backend.dto.BillTableDto;
import com.kt.backend.dto.RevenueDto;
import java.util.List;
public interface BillTableService {

    //BillTableDto createBillTable(BillTableDto billTableDto);
    BillTableDto getBillTableById(Integer billId);
    List<BillTableDto> getAllBillsTable();
    
    BillTableDto getBillByOrderTableId(Integer orderTId);
    
   // BillTableDto createBill(Integer orderTableId, Double receivedAmount);
    
    BillTableDto createBill(Integer orderTId, BillTableDto billTableDto);
//	BillOnlineDto createBill(BillOnlineDto billDto);
//	
//	BillOnlineDto getBill(Integer billId);
//	
//	void deleteBill(Integer billId);
//	
//	RevenueDto getRevenueOfStore();
//	
//	RevenueDto getRevenueOfStoreInMonth(String month);
//	
//	List<BillOnlineDto> getAllBill();
}
