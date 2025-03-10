package com.kt.backend.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.backend.dto.TableItemDto;
import com.kt.backend.entity.OrderTable;
import com.kt.backend.entity.Product;
import com.kt.backend.entity.TableInfo;
import com.kt.backend.entity.TableItem;
import com.kt.backend.exception.ResourceNotFoundException;
import com.kt.backend.repository.ProductRepository;
import com.kt.backend.repository.TableInfoRepository;
import com.kt.backend.repository.TableItemRepository;
import com.kt.backend.service.TableItemService;


@Service
public class TableItemServiceImpl implements TableItemService {

	@Autowired
    private TableItemRepository tableItemRepository;
	
	 @Autowired
	    private ProductRepository productRepository;
	 
	 @Autowired
	    private TableInfoRepository tableInfoRepository;

    @Autowired
    private ModelMapper modelMapper;


//    @Override
//    public TableItemDto createTableItem(TableItemDto tableItemDto) {
//        TableItem tableItem = modelMapper.map(tableItemDto, TableItem.class);
//        TableItem savedTableItem = tableItemRepository.save(tableItem);
//        return modelMapper.map(savedTableItem, TableItemDto.class);
//    }

//
//    @Override
//    public TableItemDto createTableItem(TableItemDto tableItemDto, Integer tableId, Integer productId) {
//        Product product = this.productRepository.findById(productId)
//                .orElseThrow(() -> new ResourceNotFoundException(" Product", "productId", productId));
//
//        TableInfo tableInfo = this.tableInfoRepository.findById(tableId)
//                .orElseThrow(() -> new ResourceNotFoundException("TableInfo", "tableId", tableId));
//
//     // Tạo ánh xạ tùy chỉnh cho ModelMapper để chỉ định rõ ràng
////        modelMapper.typeMap(CartItem.class, CartItemDto.class).addMappings(mapper -> {
////            mapper.map(src -> src.getCart().getCartId(), CartItemDto::setCartId);
////        });
//        // Tạo ánh xạ tùy chỉnh cho ModelMapper để chỉ rõ ràng
//        // Tạo ánh xạ tùy chỉnh cho ModelMapper để chỉ rõ ràng
//        modelMapper.typeMap(TableItem.class, TableItemDto.class).addMappings(mapper -> {
//            // Chỉ ánh xạ orderTableId từ OrderTable trong trường hợp cần thiết (khi đã có order)
//            mapper.map(src -> src.getOrderTable() != null ? src.getOrderTable().getOrderTableId() : null, TableItemDto::setOrderTableId);
//        });
//        
//        TableItem item = modelMapper.map(tableItemDto, TableItem.class);
//        item.setProduct(product);
//        item.setTableInfo(tableInfo);
//        item.setPrice(product.getPrice() * tableItemDto.getQuantity()); // Tính giá tổng
//        item.setOrderTable(null);
//        
//        
//        TableItem newItem = this.tableItemRepository.save(item);
//        //return this.modelMapper.map(newItem, CartItemDto.class);
//        TableItemDto newItemDto = modelMapper.map(newItem, TableItemDto.class);
//        
//        // Thiết lập tableId2 từ đối tượng TableInfo
//        newItemDto.setTableId(tableInfo.getTableId());
//        newItemDto.setOrderTableId(null);
//
//        return newItemDto;
//    }
    
    @Override
    public TableItemDto createTableItem(TableItemDto tableItemDto, Integer tableId, Integer productId) {
        // Lấy thông tin từ các repository
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        TableInfo tableInfo = this.tableInfoRepository.findById(tableId)
                .orElseThrow(() -> new ResourceNotFoundException("TableInfo", "tableId", tableId));

        // Đảm bảo ánh xạ orderTableId chỉ khi có orderTable
//        modelMapper.typeMap(TableItem.class, TableItemDto.class).addMappings(mapper -> {
//            // Chỉ ánh xạ orderTableId từ OrderTable trong trường hợp có OrderTable
//            mapper.map(src -> src.getOrderTable() != null ? src.getOrderTable().getOrderTableId() : null, TableItemDto::setOrderTableId);
//        });

        // Ánh xạ TableItemDto sang TableItem entity
        TableItem item = modelMapper.map(tableItemDto, TableItem.class);
        item.setProduct(product);
        item.setTableInfo(tableInfo);
        item.setPrice(product.getPrice() * tableItemDto.getQuantity()); // Tính giá tổng
        item.setOrderTable(null);  // Đảm bảo không ánh xạ OrderTable vào TableItem

        // Lưu vào cơ sở dữ liệu
        TableItem newItem = this.tableItemRepository.save(item);

        // Ánh xạ lại sang TableItemDto để trả về
        TableItemDto newItemDto = modelMapper.map(newItem, TableItemDto.class);

        // Thiết lập tableId từ đối tượng TableInfo
        newItemDto.setTableId(tableInfo.getTableId());
       // newItemDto.setOrderTableId(null);  
        return newItemDto;
    }
//    @Override
//    public TableItemDto updateTableItem(TableItemDto tableItemDto, Integer tableItemId) {
//        TableItem existingTableItem = tableItemRepository.findById(tableItemId)
//                .orElseThrow(() -> new ResourceNotFoundException("TableItem", "id", tableItemId));
//        TableItem updatedTableItem = modelMapper.map(tableItemDto, TableItem.class);
//        updatedTableItem.setTableItemId(existingTableItem.getTableItemId());
//        TableItem savedTableItem = tableItemRepository.save(updatedTableItem);
//        return modelMapper.map(savedTableItem, TableItemDto.class);
//    }
    @Override
    public TableItemDto updateTableItem(TableItemDto tableItemDto, Integer tableItemId) {
        TableItem existingTableItem = tableItemRepository.findById(tableItemId)
                .orElseThrow(() -> new ResourceNotFoundException("TableItem", "id", tableItemId));

        // Lấy giá món ăn (price) từ TableItem hiện tại hoặc có thể lấy từ sản phẩm (product) nếu bạn cần tính toán lại giá
        Integer currentQuantity = existingTableItem.getQuantity();
        Double unitPrice = existingTableItem.getPrice() / currentQuantity; 
        
        // Cập nhật số lượng mới
        Integer newQuantity = tableItemDto.getQuantity();
        existingTableItem.setQuantity(newQuantity);

     // Cập nhật tổng giá trị (price * quantity)
        existingTableItem.setPrice(unitPrice * newQuantity);

        // Lưu lại đối tượng đã chỉnh sửa
        TableItem savedTableItem = tableItemRepository.save(existingTableItem);

        return modelMapper.map(savedTableItem, TableItemDto.class);
    }

    @Override
    public void deleteTableItem(Integer tableItemId) {
        TableItem tableItem = tableItemRepository.findById(tableItemId)
                .orElseThrow(() -> new ResourceNotFoundException("TableItem", "id", tableItemId));
        tableItemRepository.delete(tableItem);
    }

//    @Override
//    public List<TableItemDto> getAllTableItems() {
//        return tableItemRepository.findAll().stream()
//                .map(tableItem -> modelMapper.map(tableItem, TableItemDto.class))
//                .collect(Collectors.toList());
//    }
    
//    @Override
//    public List<TableItemDto> getAllTableItems() {
//        return tableItemRepository.findAll().stream()
//                .map(tableItem -> {
//                    TableItemDto dto = modelMapper.map(tableItem, TableItemDto.class);
//                    dto.setTableId(tableItem.getTableInfo().getTableId()); // Ánh xạ cartId từ Cart
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }
    
    @Override
    public List<TableItemDto> getAllTableItems() {
        return tableItemRepository.findAll().stream()
                .map(tableItem -> {
                    TableItemDto dto = modelMapper.map(tableItem, TableItemDto.class);
                    if (tableItem.getTableInfo() != null) {
                        dto.setTableId(tableItem.getTableInfo().getTableId()); // Ánh xạ đúng tableId
                    }
                    if (tableItem.getOrderTable() != null) {
                        dto.setOrderTId(tableItem.getOrderTable().getOrderTableId());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    //xóa các TableItem có orderId=null khi thoát khỏi bàn, không order
    @Override
    public void deleteTableItemsByOrderTableIsNullAndTableId(Integer tableId) {
        // Tìm tất cả các TableItem có orderTableId là null và có tableId là tableId được truyền vào
        List<TableItem> tableItemsToDelete = tableItemRepository.findByOrderTableIsNullAndTableInfoTableId(tableId);

        // Xóa các TableItem tìm được
        tableItemRepository.deleteAll(tableItemsToDelete);
    }
   
	
}
