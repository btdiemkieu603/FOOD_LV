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
import org.springframework.web.bind.annotation.RestController;


import com.kt.backend.dto.TableItemDto;
import com.kt.backend.entity.TableItem;
import com.kt.backend.response.ApiResponse;
import com.kt.backend.service.TableItemService;


//@CrossOrigin(origins = "http://localhost:3000")

//@CrossOrigin(origins = "http://192.168.234.28:30030")
@CrossOrigin(origins = "${allowedOrigins}")
@RestController
@RequestMapping("/api/tableItem")
public class TableItemController {

	@Autowired
    private TableItemService tableItemService;

//    @PostMapping
//    public ResponseEntity<TableItemDto> createTableItem(@RequestBody TableItemDto tableItemDto) {
//        TableItemDto createdTableItem = tableItemService.createTableItem(tableItemDto);
//        return new ResponseEntity<>(createdTableItem, HttpStatus.CREATED);
//    }

    @PostMapping("/add/{tableId}/{productId}")
    public ResponseEntity<TableItemDto> createTableItem(
            @RequestBody TableItemDto tableItemDto,
            @PathVariable Integer tableId,
            @PathVariable Integer productId) {

        // Gọi service để thêm CartItem vào giỏ hàng
    	TableItemDto createTableItem = tableItemService.createTableItem(tableItemDto, tableId, productId);

        // Trả về kết quả và mã trạng thái HTTP 201 CREATED
        return new ResponseEntity<>(createTableItem, HttpStatus.CREATED);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<TableItemDto>> getAllTableItems() {
        List<TableItemDto> tableItems = tableItemService.getAllTableItems();
        return ResponseEntity.ok(tableItems);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TableItemDto> updateTableItem(@PathVariable Integer id, @RequestBody TableItemDto tableItemDto) {
        TableItemDto updatedTableItem = tableItemService.updateTableItem(tableItemDto, id);
        return ResponseEntity.ok(updatedTableItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTableItem(@PathVariable Integer id) {
        tableItemService.deleteTableItem(id);
        return new ResponseEntity<>(new ApiResponse("Table item deleted successfully", true), HttpStatus.OK);
    }
	
    @DeleteMapping("/delete/{tableId}")
    public ResponseEntity<String> deleteTableItems(@PathVariable Integer tableId) {
        tableItemService.deleteTableItemsByOrderTableIsNullAndTableId(tableId);
        return ResponseEntity.ok("Deleted table items with orderTableId = null and tableId = " + tableId);
    }
}
