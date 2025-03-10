package com.kt.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kt.backend.dto.CartDto;
import com.kt.backend.dto.DiscountDto;
import com.kt.backend.dto.TableInfoDto;
import com.kt.backend.response.ApiResponse;
import com.kt.backend.service.CartService;
import com.kt.backend.service.TableInfoService;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://192.168.234.28:30030")
@CrossOrigin(origins = "${allowedOrigins}")
@RequestMapping("/api/tableInfo")
public class TableInfoController {
	@Autowired
    private TableInfoService tableInfoService;

    @PostMapping("/add")
    public ResponseEntity<TableInfoDto> createTableInfo(@RequestBody TableInfoDto tableInfoDto) {
        TableInfoDto createdTableInfo = tableInfoService.createTableInfo(tableInfoDto);
        return new ResponseEntity<>(createdTableInfo, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TableInfoDto>> getAllTableInfos() {
        List<TableInfoDto> tableInfos = tableInfoService.getAllTableInfos();
        return ResponseEntity.ok(tableInfos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TableInfoDto> updateTableInfo(@PathVariable Integer id, @RequestBody TableInfoDto tableInfoDto) {
        TableInfoDto updatedTableInfo = tableInfoService.updateTableInfo(tableInfoDto, id);
        return ResponseEntity.ok(updatedTableInfo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTableInfo(@PathVariable Integer id) {
        tableInfoService.deleteTableInfo(id);
        return new ResponseEntity<>(new ApiResponse("Table info deleted successfully", true), HttpStatus.OK);
    }
    @GetMapping("/{tableId}")
    public TableInfoDto getTableById(@PathVariable Integer tableId) {
        return tableInfoService.getTableById(tableId);
    }
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<TableInfoDto> updateTableStatus(@PathVariable Integer id, @RequestParam String status) {
        TableInfoDto updatedTable = tableInfoService.updateTableStatus(id, status);
        return ResponseEntity.ok(updatedTable);
    }
    
    @PutMapping("/hidden/{tableId}")
    public ResponseEntity<TableInfoDto> changeExist(@PathVariable Integer tableId) {
        TableInfoDto updatedTable = tableInfoService.changeExist(tableId);
        return ResponseEntity.ok(updatedTable);

    }
        
}
