package com.kt.backend.service;

import com.kt.backend.dto.BillDto;
import com.kt.backend.dto.RevenueDto;
import java.util.List;
public interface BillService {

	BillDto createBill(BillDto billDto);
	
	BillDto getBill(Integer billId);
	
	void deleteBill(Integer billId);
	
	RevenueDto getRevenueOfStore();
	
	RevenueDto getRevenueOfStoreInMonth(String month);
	
	List<BillDto> getAllBill();
}
