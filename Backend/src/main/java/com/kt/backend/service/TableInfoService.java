package com.kt.backend.service;

import java.util.List;

import com.kt.backend.dto.CartDto;
import com.kt.backend.dto.DiscountDto;
import com.kt.backend.dto.TableInfoDto;


public interface TableInfoService {
	
	TableInfoDto createTableInfo(TableInfoDto tableInfoDto);
    List<TableInfoDto> getAllTableInfos();
    TableInfoDto updateTableInfo(TableInfoDto tableInfoDto, Integer id);
    void deleteTableInfo(Integer id);
    
    TableInfoDto getTableById(Integer tableId);
	//Double getTotalPriceOfCartCurrent(Integer cartId);
    
    TableInfoDto updateTableStatus(Integer id, String status);
    
    TableInfoDto changeExist(Integer id);
}
