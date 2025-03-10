package com.kt.backend.service.Impl;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.backend.dto.DiscountDto;
import com.kt.backend.dto.TableInfoDto;
import com.kt.backend.dto.TableItemDto;
import com.kt.backend.entity.Discount;
import com.kt.backend.entity.TableInfo;
import com.kt.backend.repository.TableInfoRepository;
import com.kt.backend.service.TableInfoService;


import com.kt.backend.exception.ResourceNotFoundException;


@Service
public class TableInfoServiceImpl implements TableInfoService{

	 @Autowired
	    private TableInfoRepository tableInfoRepository;

	    @Autowired
	    private ModelMapper modelMapper;

	    @Override
	    public TableInfoDto createTableInfo(TableInfoDto tableInfoDto) {
	        TableInfo tableInfo = modelMapper.map(tableInfoDto, TableInfo.class);
	     // Thiết lập trạng thái bàn là "order"
	      //  tableInfo.setStatus("order");
	        TableInfo savedTableInfo = tableInfoRepository.save(tableInfo);
	        return modelMapper.map(savedTableInfo, TableInfoDto.class);
	    }

//	    @Override
//	    public List<TableInfoDto> getAllTableInfos() {
//	        return tableInfoRepository.findAll().stream()
//	                .map(tableInfo -> modelMapper.map(tableInfo, TableInfoDto.class))
//	                .collect(Collectors.toList());
//	    }
	    

	    @Override
	    public List<TableInfoDto> getAllTableInfos() {
	        return tableInfoRepository.findAll().stream()
	                .map(tableInfo -> {
	                    TableInfoDto dto = modelMapper.map(tableInfo, TableInfoDto.class);
	                    // Ánh xạ tableItems và thiết lập tableId cho từng item
	                    dto.setTableItems(tableInfo.getTableItems().stream()
	                            .map(tableItem -> {
	                                TableItemDto itemDto = modelMapper.map(tableItem, TableItemDto.class);
	                                itemDto.setTableId(tableInfo.getTableId()); // Thiết lập tableId đúng
	                                return itemDto;
	                            })
	                            .collect(Collectors.toList()));
	                    return dto;
	                })
	                .collect(Collectors.toList());
	    }
	
	    
	    @Override
	    public TableInfoDto getTableById(Integer tableId) {
	        TableInfo table = tableInfoRepository.findById(tableId).orElse(null);
	        return table != null ? modelMapper.map(table, TableInfoDto.class) : null;
	    }
	    

	    @Override
	    public TableInfoDto updateTableInfo(TableInfoDto tableInfoDto, Integer id) {
	        TableInfo existingTableInfo = tableInfoRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("TableInfo", "id", id));
	        
	        existingTableInfo.setTableNumber(tableInfoDto.getTableNumber());
	        existingTableInfo.setSeatingCapacity(tableInfoDto.getSeatingCapacity());
	        existingTableInfo.setStatus(tableInfoDto.getStatus());
	        
	        TableInfo updatedTableInfo = tableInfoRepository.save(existingTableInfo);
	        return modelMapper.map(updatedTableInfo, TableInfoDto.class);
	    }

	    @Override
	    public void deleteTableInfo(Integer id) {
	        TableInfo tableInfo = tableInfoRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("TableInfo", "id", id));
	        
	        tableInfoRepository.delete(tableInfo);
	    }

	    @Override
	    public TableInfoDto updateTableStatus(Integer id, String status) {
	        TableInfo tableInfo = tableInfoRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("TableInfo", "id", id));

	        tableInfo.setStatus(status);
	        TableInfo updatedTable = tableInfoRepository.save(tableInfo);

	        return modelMapper.map(updatedTable, TableInfoDto.class);
	    }
	    
	    @Override
		public TableInfoDto changeExist(Integer id) {		
			TableInfo dis = this.tableInfoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("TableInfo ","id", id));
			dis.setIsExist(!dis.getIsExist());
			TableInfo updatedDis = this.tableInfoRepository.save(dis);
			return this.modelMapper.map(updatedDis, TableInfoDto.class);
		}
}
