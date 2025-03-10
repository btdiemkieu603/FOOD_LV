package com.kt.backend.service.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import com.kt.backend.dto.BillDto;
import com.kt.backend.dto.RevenueDto;
import com.kt.backend.entity.Bill;
import com.kt.backend.exception.ResourceNotFoundException;
import com.kt.backend.repository.BillRepository;
import com.kt.backend.service.BillService;
import java.util.List;
@Service
public class BillServiceImpl implements BillService{

	@Autowired
	private BillRepository billRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public BillDto createBill(BillDto billDto) {
		Bill bill = this.modelMapper.map(billDto, Bill.class);
		Bill addBill = this.billRepository.save(bill);
		return this.modelMapper.map(addBill, BillDto.class);
	}

	@Override
	public BillDto getBill(Integer billId) {
		Bill bill = this.billRepository.findById(billId).orElseThrow(()-> new ResourceNotFoundException("Bill","BillId", billId));
		return this.modelMapper.map(bill, BillDto.class);
	}

	@Override
	public void deleteBill(Integer billId) {
		Bill bill = this.billRepository.findById(billId).orElseThrow(()-> new ResourceNotFoundException("Bill","BillId", billId));
		this.billRepository.delete(bill);
		
	}

	@Override
	public RevenueDto getRevenueOfStore() {
		RevenueDto revenueDto = new RevenueDto();
		revenueDto.setRevenue(this.billRepository.getTotalRevenue());
		return revenueDto;
	}

	@Override
	public RevenueDto getRevenueOfStoreInMonth(String month) {
		RevenueDto revenueDto = new RevenueDto();
		revenueDto.setRevenue(this.billRepository.getRevenueByMonth(month));
		return revenueDto;
	}
	
	@Override
	public List<BillDto> getAllBill() {
		List<Bill> bills = this.billRepository.findAll();
		List<BillDto> billDtos = bills.stream().map((bill) -> this.modelMapper.map(bill, BillDto.class))
				.collect(Collectors.toList());
		return billDtos;
	}
	
}
