package com.kt.backend.service.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

//import com.kt.backend.dto.BillOnlineDto;
import com.kt.backend.dto.BillTableDto;
import com.kt.backend.dto.RevenueDto;
import com.kt.backend.dto.TableItemDto;
//import com.kt.backend.entity.BillOnline;
import com.kt.backend.entity.BillTable;
import com.kt.backend.entity.OrderTable;
import com.kt.backend.exception.ResourceNotFoundException;
//import com.kt.backend.repository.BillOnlineRepository;
import com.kt.backend.repository.BillTableRepository;
import com.kt.backend.repository.OrderTableRepository;
//import com.kt.backend.service.BillOnlineService;
import com.kt.backend.service.BillTableService;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class BillTableServiceImpl implements BillTableService{

	  @Autowired
	    private BillTableRepository billTableRepository;
	  
	  @Autowired
	    private OrderTableRepository orderTableRepository;
	  

	    @Autowired
	    private ModelMapper modelMapper;

//	    @Override
//	    public BillTableDto createBillTable(BillTableDto billTableDto) {
//	        BillTable billTable = modelMapper.map(billTableDto, BillTable.class);
//	        BillTable savedBillTable = billTableRepository.save(billTable);
//	        return modelMapper.map(savedBillTable, BillTableDto.class);
//	    }
//	    public BillTableDto createBill(Integer orderTableId, Double receivedAmount) {
//	        // Tìm đơn hàng dựa trên orderTableId
//	        OrderTable orderTable = orderTableRepository.findById(orderTableId)
//	                .orElseThrow(() -> new ResourceNotFoundException("OrderTable", "orderTableId", orderTableId));
//
//	        // Tạo BillTable mới
//	        BillTable bill = new BillTable();
//	        bill.setOrderTable(orderTable);
//	        bill.setTotalItemAmount(orderTable.getTotalAmount());
//	        bill.setReceivedAmount(receivedAmount);
//	        bill.setChangeAmount(receivedAmount - orderTable.getTotalAmount());
//	        bill.setTotalPaymentAmount(orderTable.getTotalAmount()); // có thể điều chỉnh nếu có khuyến mãi
//	        bill.setCreatedAt(LocalDateTime.now());
//
//	        // Lưu BillTable vào cơ sở dữ liệu
//	        bill = billTableRepository.save(bill);
//
//	        // Trả về DTO
//	        return modelMapper.map(bill, BillTableDto.class);
//	    }
	    
	    public BillTableDto createBill(Integer orderTId, BillTableDto billTableDto) {
	        // Tìm đơn hàng dựa trên orderTableId
	        OrderTable orderTable = orderTableRepository.findById(orderTId)
	                .orElseThrow(() -> new ResourceNotFoundException("OrderTable", "orderTableId", orderTId));

	        
	        // Tạo hóa đơn mới
	        BillTable billTable = new BillTable();
	        billTable.setOrderTable(orderTable);
//	        billTable.setTotalItemAmount(billTableDto.getTotalItemAmount());
	        billTable.setReceivedAmount(billTableDto.getReceivedAmount());
	        billTable.setChangeAmount(billTableDto.getChangeAmount());
	        billTable.setTotalPaymentAmount(billTableDto.getTotalPaymentAmount());
	        billTable.setCreatedAt(LocalDateTime.now().withSecond(0).withNano(0));

	        // Lưu hóa đơn vào cơ sở dữ liệu
	        billTable = billTableRepository.save(billTable);
	        
	        BillTableDto newItemDto = modelMapper.map(billTable, BillTableDto.class);

	        // Thiết lập tableId từ đối tượng TableInfo
	        newItemDto.setOrderTId(orderTable.getOrderTableId());
	        
	        // Chuyển đổi sang DTO để trả về
	        return newItemDto;
	    }
	    
	    @Override
	    public BillTableDto getBillByOrderTableId(Integer orderTId) {
	        BillTable billTable = billTableRepository.findByOrderTable_OrderTableId(orderTId).orElse(null);
	        return billTable != null ? modelMapper.map(billTable, BillTableDto.class) : null;
	    }

	    @Override
	    public BillTableDto getBillTableById(Integer billId) {
	        BillTable billTable = billTableRepository.findById(billId)
	                .orElseThrow(() -> new ResourceNotFoundException("BillTable", "id", billId));
	        return modelMapper.map(billTable, BillTableDto.class);
	    }

//	    @Override
//	    public List<BillTableDto> getAllBillsTable() {
//	        return billTableRepository.findAll().stream()
//	                .map(billTable -> modelMapper.map(billTable, BillTableDto.class))
//	                .collect(Collectors.toList());
//	    }
	    
	    @Override
	    public List<BillTableDto> getAllBillsTable() {
	        return billTableRepository.findAll().stream()
	                .map(billTable ->{
	                	BillTableDto dto = modelMapper.map(billTable, BillTableDto.class);
	                	 if (billTable.getOrderTable() != null) {
	                         dto.setOrderTId(billTable.getOrderTable().getOrderTableId());
	                     }
	                     return dto;
	                })
	                .collect(Collectors.toList());
	    }
	
}
