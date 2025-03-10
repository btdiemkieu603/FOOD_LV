package com.kt.backend.service.Impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.backend.dto.OrderTableDto;
import com.kt.backend.dto.TableItemDto;
import com.kt.backend.entity.Account;
import com.kt.backend.entity.OrderStatus;
import com.kt.backend.entity.OrderTable;
import com.kt.backend.entity.Product;
import com.kt.backend.entity.Staff;
import com.kt.backend.entity.TableInfo;
import com.kt.backend.entity.TableItem;
import com.kt.backend.exception.ResourceNotFoundException;
import com.kt.backend.repository.AccountRepository;
import com.kt.backend.repository.OrderStatusRepository;
import com.kt.backend.repository.OrderTableRepository;
import com.kt.backend.repository.ProductRepository;
import com.kt.backend.repository.StaffRepository;
import com.kt.backend.repository.TableInfoRepository;
import com.kt.backend.repository.TableItemRepository;
import com.kt.backend.service.OrderTableService;


@Service
public class OrderTableServiceImpl implements OrderTableService{

	
	@Autowired
    private OrderTableRepository orderTableRepository;
	
	@Autowired
   // private StaffRepository staffRepository;
	private AccountRepository accountRepository;
	
	@Autowired
    private OrderStatusRepository orderStatusRepository;
	@Autowired
    private TableItemRepository tableItemRepository;
	
	@Autowired
    private TableInfoRepository tableInfoRepository;

	@Autowired
    private ProductRepository productRepository;

	
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public OrderTableDto createOrder(OrderTableDto orderTableDto) {
    
    	 // Lấy TableInfo từ cơ sở dữ liệu
        TableInfo tableInfo = tableInfoRepository.findById(orderTableDto.getTableId())
            .orElseThrow(() -> new ResourceNotFoundException("TableInfo", "tableId", orderTableDto.getTableId()));
        
    	
    	OrderTable orderTable = modelMapper.map(orderTableDto, OrderTable.class);

        orderTable.setCreatedAt( LocalDateTime.now().withSecond(0).withNano(0));
       
        orderTable.setTableInfo(tableInfo);
//        Staff staff = staffRepository.findByEmail(orderTableDto.getStaffEmail())
//                .orElseThrow(() -> new ResourceNotFoundException("Staff", "email", orderTableDto.getStaffEmail()));
//        orderTable.setStaff(staff);
//        
        Account account = accountRepository.findById(orderTableDto.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "email", orderTableDto.getAccountId()));
        orderTable.setAccount(account);
        // Lấy trạng thái đơn hàng
        OrderStatus orderStatus = orderStatusRepository.findById(orderTableDto.getOrderStatusId())
                .orElseThrow(() -> new ResourceNotFoundException("OrderStatus", "orderStatusId", orderTableDto.getOrderStatusId()));
       orderTable.setOrderStatus(orderStatus);
   
       
// Tìm các TableItem có orderTableId là null và tableId tương ứng
       List<TableItem> tableItems = tableItemRepository.findByTableInfo_TableId(orderTableDto.getTableId())
           .stream()
           .filter(item -> item.getOrderTable() == null)  // Chỉ lấy các TableItem chưa có orderTableId
           .collect(Collectors.toList());
       
       
        // Lấy danh sách TableItem dựa trên tableId từ orderTableDto
      // List<TableItem> tableItems = tableItemRepository.findByTableInfo_TableId(orderTableDto.getTableId());
        
//        List<TableItem> savedTableItems = new ArrayList<>();
//        for (TableItem item : tableItems) {
//            TableItem newItem = new TableItem();
//            newItem.setFood(item.getFood());
//            newItem.setQuantity(item.getQuantity());
//            newItem.setPrice(item.getPrice());
//            newItem.setOrderTable(orderTable); // Thiết lập mối quan hệ ngược
//            savedTableItems.add(newItem);
//        }
        
//        // Cập nhật thông tin cho các TableItem
//        tableItems.forEach(item -> {
//            item.setTableInfo(tableInfo);
//            item.setOrderTable(orderTable); // Liên kết TableItem với OrderTable
//        });
        
        // Chỉ cập nhật orderTable cho các TableItem có orderTableId bằng null
//        tableItems.forEach(item -> {
//            if (item.getOrderTable() == null) {
//            	item.setOrderTable(orderTable); // Gán orderTable khi orderTableId là null
//                 // Cập nhật lại trong cơ sở dữ liệu
//            }
//        });
       double totalAmount = tableItems.stream()
   	        .mapToDouble(TableItem::getPrice) // Lấy giá của từng item từ thuộc tính `price`
   	        .sum();
   	    orderTable.setTotalAmount(totalAmount);
   	    
   	 tableItems.forEach(item -> {
       //item.setTableInfo(tableInfo);
       item.setOrderTable(orderTable); // Liên kết TableItem với OrderTable
       
   });
   	 orderTable.setTableItems(tableItems);
//      tableItems.forEach(item -> {
//      if (item.getOrderTable() == null) {
//      	item.setOrderTable(orderTable); // Gán orderTable khi orderTableId là null
//           // Cập nhật lại trong cơ sở dữ liệu
//      }
//  });
   		 
   	 //List<TableItem> tableItem2 = tableItemRepository.findByTableInfo_TableId(orderTableDto.getTableId());
   	 //tableItems.forEach(item -> item.setOrderTable(orderTable));
  
        // Cập nhật thông tin cho các TableItem
       // tableItems.forEach(item -> item.setTableInfo(tableInfo));
//       orderTable.setTableItems(tableItems);
      
       
        //double totalAmount = tableItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    
     
    	    
        OrderTable savedOrderTable = orderTableRepository.save(orderTable);
       
        // Chuyển đổi OrderTable đã lưu thành OrderTableDto
        OrderTableDto neworderTableDto = modelMapper.map(savedOrderTable, OrderTableDto.class);
    
        List<TableItemDto> tableItemsDto = savedOrderTable.getTableItems().stream()
        		.filter(tableItem -> tableItem.getOrderTable().getOrderTableId().equals(savedOrderTable.getOrderTableId()))
        		.map(tableItem -> {
                TableItemDto itemDto = modelMapper.map(tableItem, TableItemDto.class);
                // Không thiết lập tableId cho TableItemDto
                itemDto.setOrderTId(tableItem.getOrderTable().getOrderTableId());
                itemDto.setTableId(tableItem.getTableInfo().getTableId()); // Hoặc không gọi dòng này để không có tableId
                return itemDto;
            })
            .collect(Collectors.toList());  
        neworderTableDto.setTableItems(tableItemsDto);
        neworderTableDto.setTableId(orderTableDto.getTableId());
//        newOrderTableDto.setStaffEmail(savedOrderTable.getAccount().getEmail());

        //neworderTableDto.setTableId(orderTableDto.getTableId()); 
        
        return neworderTableDto;
    }
    
//    @Override
//    public OrderTableDto createOrder(OrderTableDto orderTableDto) {
//        OrderTable orderTable = modelMapper.map(orderTableDto, OrderTable.class);
//        List<TableItem> tableItems = orderTableDto.getTableItems().stream()
//                .map(itemDto -> modelMapper.map(itemDto, TableItem.class))
//                .collect(Collectors.toList());
//        orderTable.setTableItems(tableItems);
//        OrderTable savedOrderTable = orderTableRepository.save(orderTable);
//        return modelMapper.map(savedOrderTable, OrderTableDto.class);
//    }


//    @Override
//    public OrderTableDto getOrderTableById(Integer orderTableId) {
//        OrderTable orderTable = orderTableRepository.findById(orderTableId).orElse(null);
//        return orderTable != null ? modelMapper.map(orderTable, OrderTableDto.class) : null;
//    }
    
    public OrderTableDto getOrderTableById(Integer orderTableId) {
 //       OrderTable orderTable = orderTableRepository.findById(orderTableId)
 //               .orElseThrow(() -> new ResourceNotFoundException("OrderTable", "orderTableId", orderTableId));

//        // Ánh xạ OrderTable thành OrderTableDto
//        OrderTableDto newOrderTableDto = modelMapper.map(orderTable, OrderTableDto.class);
//
//        // Ánh xạ danh sách TableItem sang TableItemDto
//        List<TableItemDto> tableItemsDto = orderTable.getTableItems().stream()
//                .map(tableItem -> {
//                    TableItemDto itemDto = modelMapper.map(tableItem, TableItemDto.class);
//                    // Thiết lập tableId cho TableItemDto từ TableInfo
//                    if (tableItem.getTableInfo() != null) {
//                        itemDto.setTableId(tableItem.getTableInfo().getTableId());
//                    } else {
//                        itemDto.setTableId(null); // Hoặc giá trị mặc định nếu không có TableInfo
//                    }
//                    return itemDto;
//                })
//                .collect(Collectors.toList());
//
//        // Thiết lập danh sách TableItemDto cho OrderTableDto
//        newOrderTableDto.setTableItems(tableItemsDto);
//
//        // Nếu cần thiết, bạn có thể thiết lập tableId cho OrderTableDto từ TableInfo
//        if (orderTable.getTableInfo() != null) {
//            newOrderTableDto.setTableId(orderTable.getTableInfo().getTableId());
//        } else {
//            newOrderTableDto.setTableId(null); // Hoặc giá trị mặc định nếu không có TableInfo
//        }
//
//        return newOrderTableDto;
        
    	  // Lấy thông tin đơn hàng
        OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderTable", "orderTableId", orderTableId));
       
        // Chuyển đổi sang DTO
        OrderTableDto orderTableDto = modelMapper.map(orderTable, OrderTableDto.class);

     // Lấy `tableId` từ TableInfo của `TableItem` đầu tiên trong danh sách `tableItems` (nếu có)
//        Integer tableId = null;
//        if (!orderTable.getTableItems().isEmpty() && orderTable.getTableItems().get(0).getTableInfo() != null) {
//            tableId = orderTable.getTableItems().get(0).getTableInfo().getTableId();
//        }
//        orderTableDto.setTableId(tableId);
//        
        
        if (orderTable.getTableInfo() != null) {
            orderTableDto.setTableId(orderTable.getTableInfo().getTableId());
        }

        // Chuyển đổi danh sách tableItems sang DTO
        List<TableItemDto> tableItemsDto = orderTable.getTableItems().stream()
                .map(tableItem -> {
                    TableItemDto dto = modelMapper.map(tableItem, TableItemDto.class); // ánh xạ tableItem sang dto
                    if (tableItem.getTableInfo() != null) {
                        dto.setTableId(tableItem.getTableInfo().getTableId()); // Ánh xạ đúng tableId từ TableInfo
                    }
                    return dto; // trả về dto
                })
                .collect(Collectors.toList());

        orderTableDto.setTableItems(tableItemsDto); // thiết lập danh sách tableItems vào DTO
        
        return orderTableDto; 
    }
//    @Override
//    public OrderTableDto updateOrder(OrderTableDto orderTableDto, Integer orderTableId) {
//        OrderTable existingOrder = orderTableRepository.findById(orderTableId)
//                .orElseThrow(() -> new ResourceNotFoundException("OrderTable", "id", orderTableId));
//        OrderTable updatedOrder = modelMapper.map(orderTableDto, OrderTable.class);
//        updatedOrder.setOrderTableId(existingOrder.getOrderTableId());
//        OrderTable savedOrderTable = orderTableRepository.save(updatedOrder);
//        return modelMapper.map(savedOrderTable, OrderTableDto.class);
//    }
    
//    @Override
//    public OrderTableDto updateOrder(Integer orderTableId, OrderTableDto orderTableDto) {
//        // Lấy OrderTable từ cơ sở dữ liệu
//        OrderTable orderTable = orderTableRepository.findById(orderTableId)
//                .orElseThrow(() -> new ResourceNotFoundException("OrderTable", "orderTableId", orderTableId));
//        
//        // Cập nhật thông tin cơ bản của OrderTable
//        orderTable.setCreatedAt(orderTableDto.getCreatedAt());
//        
//        // Cập nhật TableInfo nếu tableId thay đổi
//        if (!orderTable.getTableInfo().getTableId().equals(orderTableDto.getTableId())) {
//            TableInfo tableInfo = tableInfoRepository.findById(orderTableDto.getTableId())
//                .orElseThrow(() -> new ResourceNotFoundException("TableInfo", "tableId", orderTableDto.getTableId()));
//            orderTable.setTableInfo(tableInfo);
//        }
//
//        // Cập nhật Staff nếu email thay đổi
//        if (!orderTable.getStaff().getEmail().equals(orderTableDto.getStaffEmail())) {
//            Staff staff = staffRepository.findByEmail(orderTableDto.getStaffEmail())
//                .orElseThrow(() -> new ResourceNotFoundException("Staff", "email", orderTableDto.getStaffEmail()));
//            orderTable.setStaff(staff);
//        }
//
//        // Cập nhật trạng thái đơn hàng nếu orderStatusId thay đổi
//        if (!orderTable.getOrderStatus().getOrderStatusId().equals(orderTableDto.getOrderStatusId())) {
//            OrderStatus orderStatus = orderStatusRepository.findById(orderTableDto.getOrderStatusId())
//                .orElseThrow(() -> new ResourceNotFoundException("OrderStatus", "orderStatusId", orderTableDto.getOrderStatusId()));
//            orderTable.setOrderStatus(orderStatus);
//        }
//
//        // Cập nhật danh sách TableItem
//        List<TableItem> updatedTableItems = new ArrayList<>();
//        for (TableItemDto tableItemDto : orderTableDto.getTableItems()) {
//            TableItem tableItem;
//
//            if (tableItemDto.getTableItemId() != null) {
//                // Tìm kiếm và cập nhật TableItem nếu nó đã tồn tại
//                tableItem = tableItemRepository.findById(tableItemDto.getTableItemId())
//                    .orElseThrow(() -> new ResourceNotFoundException("TableItem", "tableItemId", tableItemDto.getTableItemId()));
//            } else {
//                // Tạo mới TableItem nếu không có tableItemId
//                tableItem = new TableItem();
//                tableItem.setOrderTable(orderTable); // Liên kết với OrderTable hiện tại
//            }
//
//            // Cập nhật thông tin của TableItem từ TableItemDto
//            tableItem.setQuantity(tableItemDto.getQuantity());
//            tableItem.setPrice(tableItemDto.getPrice());
//
//            // Cập nhật Food cho TableItem
//            Food food = foodRepository.findById(tableItemDto.getFoodId())
//                .orElseThrow(() -> new ResourceNotFoundException("Food", "foodId", tableItemDto.getFoodId()));
//            tableItem.setFood(food);
//
//            // Cập nhật TableInfo cho TableItem
//            TableInfo tableInfo = tableInfoRepository.findById(tableItemDto.getTableId())
//                .orElseThrow(() -> new ResourceNotFoundException("TableInfo", "tableId", tableItemDto.getTableId()));
//            tableItem.setTableInfo(tableInfo);
//
//            updatedTableItems.add(tableItem);
//        }
//
//        // Xóa các TableItem không còn nằm trong updatedTableItems
//        List<TableItem> existingTableItems = orderTable.getTableItems();
//        for (TableItem existingItem : existingTableItems) {
//            if (!updatedTableItems.contains(existingItem)) {
//                tableItemRepository.delete(existingItem);
//            }
//        }
//
//        // Cập nhật lại danh sách TableItem trong OrderTable
//        orderTable.setTableItems(updatedTableItems);
//
//        // Tính lại tổng giá trị của đơn hàng
//        double totalAmount = updatedTableItems.stream()
//            .mapToDouble(item -> item.getPrice() * item.getQuantity())
//            .sum();
//        orderTable.setTotalAmount(totalAmount);
//
//        // Lưu OrderTable đã cập nhật vào cơ sở dữ liệu
//        OrderTable updatedOrderTable = orderTableRepository.save(orderTable);
//
//        // Chuyển đổi sang OrderTableDto để trả về
//        OrderTableDto updatedOrderTableDto = modelMapper.map(updatedOrderTable, OrderTableDto.class);
//        List<TableItemDto> tableItemsDto = updatedOrderTable.getTableItems().stream()
//            .map(tableItem -> modelMapper.map(tableItem, TableItemDto.class))
//            .collect(Collectors.toList());
//        updatedOrderTableDto.setTableItems(tableItemsDto);
//
//        return updatedOrderTableDto;
//    }
    
    
    
    //update, them, capnhat, xoa food
//    
    @Override
    public OrderTableDto updateOrder(Integer orderTableId, OrderTableDto orderTableDto) {
        OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderTable", "orderTableId", orderTableId));

        // Cập nhật thông tin từ DTO
      //Cập nhật Staff nếu email thay đổi
//      if (!orderTable.getStaff().getEmail().equals(orderTableDto.getStaffEmail())) {
//          Staff staff = staffRepository.findByEmail(orderTableDto.getStaffEmail())
//              .orElseThrow(() -> new ResourceNotFoundException("Staff", "email", orderTableDto.getStaffEmail()));
//          orderTable.setStaff(staff);
//      }
        
        if (!orderTable.getAccount().getEmail().equals(orderTableDto.getAccountId())) {
        	 Account account = accountRepository.findById(orderTableDto.getAccountId())
                     .orElseThrow(() -> new ResourceNotFoundException("Account", "email", orderTableDto.getAccountId()));
             orderTable.setAccount(account);
        }
        
      // Cập nhật trạng thái đơn hàng nếu orderStatusId thay đổi
      if (!orderTable.getOrderStatus().getId().equals(orderTableDto.getOrderStatusId())) {
          OrderStatus orderStatus = orderStatusRepository.findById(orderTableDto.getOrderStatusId())
              .orElseThrow(() -> new ResourceNotFoundException("OrderStatus", "orderStatusId", orderTableDto.getOrderStatusId()));
          orderTable.setOrderStatus(orderStatus);
      }
//        orderTable.setStaff(orderTableDto.getStaffEmail());
//        orderTable.setOrderStatus(orderTableDto.getOrderStatusId());
//        orderTable.setTableId(orderTableDto.getTableId());

        // Cập nhật tổng tiền
        double totalAmount = tableItemRepository.findByOrderTableOrderTableId(orderTableId).stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        orderTable.setTotalAmount(totalAmount);

        // Lưu đơn hàng đã cập nhật
        orderTable = orderTableRepository.save(orderTable);
        return modelMapper.map(orderTable, OrderTableDto.class);
    }
//
//    @Override
//    public void addTableItem(Integer orderTableId, TableItemDto tableItemDto) {
//        OrderTable orderTable = orderTableRepository.findById(orderTableId)
//                .orElseThrow(() -> new ResourceNotFoundException("OrderTable", "orderTableId", orderTableId));
//        
//        TableItem tableItem = modelMapper.map(tableItemDto, TableItem.class);
//        tableItem.setOrderTable(orderTable);
//        tableItemRepository.save(tableItem);
//    }
//
//    @Override
//    public void updateTableItem(Integer orderTableId, Integer tableItemId, TableItemDto tableItemDto) {
//        TableItem tableItem = tableItemRepository.findById(tableItemId)
//                .orElseThrow(() -> new ResourceNotFoundException("TableItem", "tableItemId", tableItemId));
//        
//        // Kiểm tra xem tableItem có thuộc về orderTable không
//        if (!tableItem.getOrderTable().getOrderTableId().equals(orderTableId)) {
//            throw new ResourceNotFoundException("TableItem", "orderTableId", orderTableId);
//        }
//
//        // Cập nhật thông tin món ăn
//        tableItem.setQuantity(tableItemDto.getQuantity());
//        tableItem.setPrice(tableItemDto.getPrice());
//        tableItemRepository.save(tableItem);
//    }
//
//    @Override
//    public void deleteTableItem(Integer orderTableId, Integer tableItemId) {
//        TableItem tableItem = tableItemRepository.findById(tableItemId)
//                .orElseThrow(() -> new ResourceNotFoundException("TableItem", "tableItemId", tableItemId));
//        
//        // Kiểm tra xem tableItem có thuộc về orderTable không
//        if (!tableItem.getOrderTable().getOrderTableId().equals(orderTableId)) {
//            throw new ResourceNotFoundException("TableItem", "orderTableId", orderTableId);
//        }
//
//        tableItemRepository.delete(tableItem);
//    }
    
    
    
    //them, sua, xoa tableItem
  

//    @Override
//    public TableItemDto addTableItem(Integer orderTableId, TableItemDto tableItemDto) {
//        OrderTable orderTable = orderTableRepository.findById(orderTableId)
//                .orElseThrow(() -> new ResourceNotFoundException("OrderTable", "orderTableId", orderTableId));
//        
//        TableItem tableItem = modelMapper.map(tableItemDto, TableItem.class);
//        tableItem.setOrderTable(orderTable);
//        tableItem = tableItemRepository.save(tableItem);
//
//        return modelMapper.map(tableItem, TableItemDto.class);
//    }
    
    @Override
    public TableItemDto addTableItemToOrder(Integer orderTableId, Integer tableId, Integer productId, int quantity) {
        // Tìm đơn hàng dựa trên orderTableId
        OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderTable", "orderTableId", orderTableId));

        // Tìm bàn dựa trên tableId
        TableInfo tableInfo = tableInfoRepository.findById(tableId)
                .orElseThrow(() -> new ResourceNotFoundException("TableInfo", "tableId", tableId));

        // Tìm món ăn dựa trên foodId
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        // Tạo mới TableItem
        TableItem tableItem = new TableItem();
        tableItem.setOrderTable(orderTable); // Liên kết với đơn hàng hiện tại
        tableItem.setTableInfo(tableInfo);   // Liên kết với bàn hiện tại
        tableItem.setProduct(product);             // Liên kết với món ăn
        tableItem.setQuantity(quantity);     // Số lượng
        tableItem.setPrice(product.getPrice() * quantity); // Tính tổng giá của món

        // Lưu TableItem mới
        tableItem = tableItemRepository.save(tableItem);

        // Cập nhật tổng số tiền của OrderTable
        double totalAmount = orderTable.getTotalAmount() + tableItem.getPrice();
        orderTable.setTotalAmount(totalAmount);
        orderTableRepository.save(orderTable); // Lưu lại đơn hàng

        // Trả về DTO của TableItem
        //return modelMapper.map(tableItem, TableItemDto.class);
     // Chuyển TableItem thành DTO để trả về
        TableItemDto tableItemDto = modelMapper.map(tableItem, TableItemDto.class);
        tableItemDto.setTableId(tableInfo.getTableId()); // Đảm bảo tableId được thiết lập đúng trong DTO
        
        return tableItemDto;
    }

//    @Override
//    public TableItemDto updateTableItem(Integer orderTableId, Integer tableItemId, TableItemDto tableItemDto) {
//        TableItem tableItem = tableItemRepository.findById(tableItemId)
//                .orElseThrow(() -> new ResourceNotFoundException("TableItem", "tableItemId", tableItemId));
//        
//        // Kiểm tra xem tableItem có thuộc về orderTable không
//        if (!tableItem.getOrderTable().getOrderTableId().equals(orderTableId)) {
//            throw new ResourceNotFoundException("TableItem", "orderTableId", orderTableId);
//        }
//
//        // Cập nhật thông tin món ăn
//        tableItem.setQuantity(tableItemDto.getQuantity());
//        tableItem.setPrice(tableItemDto.getPrice());
//        tableItem = tableItemRepository.save(tableItem);
//
//        return modelMapper.map(tableItem, TableItemDto.class);
//    }
    
    @Override
    public TableItemDto updateTableItemQuantity(Integer orderTableId, Integer tableItemId, int newQuantity) {
        // Tìm `TableItem` dựa trên `tableItemId`
        TableItem tableItem = tableItemRepository.findById(tableItemId)
                .orElseThrow(() -> new ResourceNotFoundException("TableItem", "tableItemId", tableItemId));

        // Kiểm tra xem `TableItem` có thuộc về `OrderTable` không
        if (!tableItem.getOrderTable().getOrderTableId().equals(orderTableId)) {
            throw new ResourceNotFoundException("TableItem", "orderTableId", orderTableId);
        }

        // Lưu lại giá của `TableItem` trước khi cập nhật
        double oldTotalPrice = tableItem.getPrice();

        // Cập nhật số lượng và tổng giá của `TableItem`
        tableItem.setQuantity(newQuantity);
       // double newTotalPrice = tableItem.getFood().getPrice() * newQuantity;
        double newTotalPrice = tableItem.getProduct().getPrice();
        tableItem.setPrice(newTotalPrice);

        // Cập nhật lại tổng số tiền của `OrderTable`
        OrderTable orderTable = tableItem.getOrderTable();
        double updatedTotalAmount = orderTable.getTotalAmount() - oldTotalPrice + newTotalPrice* newQuantity;
        orderTable.setTotalAmount(updatedTotalAmount);

        // Lưu lại `TableItem` đã cập nhật
        tableItemRepository.save(tableItem);
        // Lưu lại `OrderTable` đã cập nhật tổng số tiền
        orderTableRepository.save(orderTable);

        // Trả về DTO của `TableItem` đã cập nhật
        return modelMapper.map(tableItem, TableItemDto.class);
    }

    @Override
    public void deleteTableItem(Integer orderTableId, Integer tableItemId) {
        TableItem tableItem = tableItemRepository.findById(tableItemId)
                .orElseThrow(() -> new ResourceNotFoundException("TableItem", "tableItemId", tableItemId));
        
        // Kiểm tra xem tableItem có thuộc về orderTable không
        if (!tableItem.getOrderTable().getOrderTableId().equals(orderTableId)) {
            throw new ResourceNotFoundException("TableItem", "orderTableId", orderTableId);
        }

        // Lưu lại giá của tableItem trước khi xóa
        double itemPrice = tableItem.getPrice();

        // Xóa tableItem khỏi cơ sở dữ liệu
        tableItemRepository.delete(tableItem);
        // Cập nhật lại tổng số tiền của OrderTable
        OrderTable orderTable = tableItem.getOrderTable();
        double newTotalAmount = orderTable.getTotalAmount() - itemPrice;
        
        // Đảm bảo tổng số tiền không âm
        orderTable.setTotalAmount(Math.max(newTotalAmount, 0.0));

        // Lưu lại OrderTable với tổng số tiền mới
        orderTableRepository.save(orderTable);
    }

    
    public OrderTableDto updateOrderStatus(Integer orderTableId, Integer orderStatusId) {
        // Tìm đơn hàng dựa trên orderTableId
        OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderTable", "orderTableId", orderTableId));

        // Tìm trạng thái dựa trên orderStatusId
        OrderStatus orderStatus = orderStatusRepository.findById(orderStatusId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderStatus", "orderStatusId", orderStatusId));

        // Cập nhật trạng thái cho đơn hàng
        orderTable.setOrderStatus(orderStatus);

        // Lưu lại đơn hàng đã cập nhật
        orderTableRepository.save(orderTable);

        // Trả về DTO của đơn hàng đã cập nhật
        return modelMapper.map(orderTable, OrderTableDto.class);
    }
    
//    @Override
//    public OrderTableDto updateOrderStatus(Integer orderTableId, Integer orderStatusId) {
//        OrderTable orderTable = orderTableRepository.findById(orderTableId)
//                .orElseThrow(() -> new ResourceNotFoundException("OrderTable", "orderTableId", orderTableId));
//
//        OrderStatus orderStatus = orderStatusRepository.findById(orderStatusId)
//                .orElseThrow(() -> new ResourceNotFoundException("OrderStatus", "orderStatusId", orderStatusId));
//
//        orderTable.setOrderStatus(orderStatus);
//        OrderTable updatedOrderTable = orderTableRepository.save(orderTable);
//        return modelMapper.map(updatedOrderTable, OrderTableDto.class);
//    }
    
    
    
    @Override
    public void deleteOrder(Integer orderTableId) {
        OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderTable", "id", orderTableId));
        orderTableRepository.delete(orderTable);
    }

//    @Override
//    public List<OrderTableDto> getAllOrders() {
//        return orderTableRepository.findAll().stream()
//                .map(orderTable -> modelMapper.map(orderTable, OrderTableDto.class))
//                .collect(Collectors.toList());
//    }
    
    @Override
    public List<OrderTableDto> getAllOrders() {
    	
        return orderTableRepository.findAll().stream()
                .map(orderTable -> {
                    // Ánh xạ OrderTable sang OrderTableDto
                    OrderTableDto orderTableDto = modelMapper.map(orderTable, OrderTableDto.class);
                    if (orderTable.getTableInfo() != null) {
                        orderTableDto.setTableId(orderTable.getTableInfo().getTableId());
                    }
                    // Chuyển đổi danh sách TableItem sang TableItemDto cho từng OrderTable
                    List<TableItemDto> tableItemsDto = orderTable.getTableItems().stream()
                            .map(tableItem -> {
                                TableItemDto itemDto = modelMapper.map(tableItem, TableItemDto.class);
                                // Gán thêm tableId vào TableItemDto
                                itemDto.setTableId(tableItem.getTableInfo().getTableId());
                                if (tableItem.getOrderTable() != null) {
                                	itemDto.setOrderTId(tableItem.getOrderTable().getOrderTableId());
                                }
                                return itemDto;
                            })
                            .collect(Collectors.toList());

                    // Gán danh sách TableItemDto vào OrderTableDto
                    orderTableDto.setTableItems(tableItemsDto);

                    return orderTableDto;
                })
                .collect(Collectors.toList());
    }

    
//	@Autowired
//	private OrderTableRepository orderRepository;
//	
//	@Autowired
//	private ProductRepository productRepository;
//	
//	@Autowired
//	private AccountRepository accountRepository;
//	
//	@Autowired
//	private CheckOutRepository checkOutRepository;
//	
//	@Autowired
//	private ItemRepository itemRepository;
//	
//	@Autowired
//	private DiscountRepository discountRepository;
//	
//	@Autowired
//	private BillService billService;
//	
//	@Autowired
//	private ItemService itemService;
//	
//	@Autowired
//	private ProductService prodService;
//	
//	@Autowired
//	private CartService cartService;
//	
//	@Autowired
//	private OrderStatusRepository orderStatusRepository;
//	
//	@Autowired
//	private ModelMapper modelMapper;
//
//	@Override
//	public ResOrderDto createOrder(OrderDto orderDto, Integer accountId, Integer checkoutId) {
//		Account account = this.accountRepository.findById(accountId).orElseThrow(()-> new ResourceNotFoundException("Account","AccountId", accountId));
//		CheckOut checkout = this.checkOutRepository.findById(checkoutId).orElseThrow(()-> new ResourceNotFoundException("CheckOut","CheckOutId", checkoutId));
//		Order order = this.modelMapper.map(orderDto, Order.class);
//		order.setAccount(account);
//		order.setCheckout(checkout);
//		order.setItems(this.itemRepository.findItemsCurrentByCart(account.getCart().getId()));
//		order.setTotalprice(this.cartService.getTotalPriceOfCartCurrent(account.getCart().getId()));
//		BillDto billDto = new BillDto();
//		billDto.setIssuedate(order.getOrderdate());
//		billDto.setTotalprice(order.getTotalprice()+10000);
//		BillDto responseBill = this.billService.createBill(billDto);		
//		Bill bill = this.modelMapper.map(responseBill, Bill.class);
//		order.setBill(bill);	
//		OrderStatus orStatus = this.orderStatusRepository.findOrderStatusByStatusID(1);
//		order.setOrderStatus(orStatus);
//		Order addOrder = this.orderRepository.save(order);
//		 // Update orderId for items in the list
//	    List<Item> items = order.getItems();
//	    for (Item item : items) {
//	        item.setOrder(order); // Assuming there's a setter for orderId in the Item class
//	    }
//	    // Save the updated items back to the repository if needed
//	    this.itemRepository.saveAll(items);
//		return this.modelMapper.map(addOrder, ResOrderDto.class);
//	}
//
//	@Override
//	public OrderDto getOrder(Integer orderId) {
//		Order order = this.orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order","OrderId", orderId));
//		return this.modelMapper.map(order, OrderDto.class);
//	}
//
//	@Override
//	public void deleteOrder(Integer orderId) {
//		Order order = this.orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order","OrderId", orderId));
//		Integer billId = order.getBill().getId();
//		this.orderRepository.delete(order);
//		this.billService.deleteBill(billId);
//	}
//
//	@Override
//	public ResOrderDto changeStatusOfOrder(Integer orderId, Integer orderStatusId) {
//		Order order = this.orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order","OrderId", orderId));
//		OrderStatus orderStatus = this.orderStatusRepository.findById(orderStatusId).orElseThrow(()-> new ResourceNotFoundException("OrderStatus","OrderStatusId", orderStatusId));
//		order.setOrderStatus(orderStatus);
//		Order updateOrder = this.orderRepository.save(order);		
//		return this.modelMapper.map(updateOrder, ResOrderDto.class);
//	}
//
//
//	@Override
//	public List<ProductDto> getThreeProductBestOrder() {
//		HashMap<Integer, Integer> listProducts = new HashMap<>();
//		for(Integer e: this.productRepository.getAllIdOfProduct()) {
//			listProducts.put(e, 0);
//		}
//		ArrayList<Integer> listProductsOrder = new ArrayList<>();
//		for(Integer e: this.itemRepository.getAllIdOfProductOrder()) {
//			listProductsOrder.add(e);
//		}
//		for(Integer e: listProductsOrder) {
//			listProducts.put(e, listProducts.get(e) + 1);
//		}
//		List<ProductDto> bestSeller = new ArrayList<>();
//		bestSeller.add(this.prodService.getProductById(this.itemService.getProductTopOrder(listProducts).getKey()));
//		bestSeller.add(this.prodService.getProductById(this.itemService.getProductTopOrder(listProducts).getKey()));
//		bestSeller.add(this.prodService.getProductById(this.itemService.getProductTopOrder(listProducts).getKey()));
//		return bestSeller;
//	}
//
//	@Override
//	public List<ResOrderDto> getOrdersByOrderStatus(Integer orderStatusId) {
//		OrderStatus order_status = this.orderStatusRepository.findById(orderStatusId).orElseThrow(()-> new ResourceNotFoundException("OrderStatus","OrderStatusId", orderStatusId));
//		List<Order> orders = this.orderRepository.findByOrderStatus(order_status);
//		List<ResOrderDto> orderDtos = orders.stream().map((order) -> this.modelMapper.map(order, ResOrderDto.class))
//				.collect(Collectors.toList());	
//		return orderDtos;
//	}
//
//	@Override
//	public List<ResOrderDto> getOrdersByAccountAndAcStatus(Integer accountId, Integer orderStatusId) {
//		Account account = this.accountRepository.findById(accountId).orElseThrow(()-> new ResourceNotFoundException("Account","AccountId", accountId));
//		List<Order> orders = this.orderRepository.findByAccount(account);
//		List<Order> ordersResult = new ArrayList<>();
//		for(Order order: orders) {
//			if(order.getOrderStatus().getId() == orderStatusId) {
//				ordersResult.add(order);
//			}
//		}
//		List<ResOrderDto> orderDtos = ordersResult.stream().map((order) -> this.modelMapper.map(order, ResOrderDto.class))
//				.collect(Collectors.toList());	
//		return orderDtos;
//	}
//
//	@Override
//	public Integer getTotalOrderByCustomer(Integer accountId) {
//		Account account = this.accountRepository.findById(accountId).orElseThrow(()-> new ResourceNotFoundException("Account","AccountId", accountId));
//		List<Order> orders = this.orderRepository.findByAccount(account);
//		if(orders == null) {
//			return 0;
//		}else {
//			return orders.size();
//		}	
//	}
//
//	@Override
//	public ResOrderDto applyDiscountForOrder(Integer orderId, String code) {
//		Order order = this.orderRepository.findById(orderId)
//				.orElseThrow(() -> new ResourceNotFoundException("Order ", "OrderId", orderId));
//		if(order.getDiscount()==null) {
//		Discount dis = this.discountRepository.findDiscountByCode(code);
//		order.setDiscount(dis);
//		order.setTotalprice(order.getTotalprice()*dis.getPercent());
//		Order applyOrder = this.orderRepository.save(order);
//		return this.modelMapper.map(applyOrder, ResOrderDto.class);
//		}else {
//			return this.modelMapper.map(order, ResOrderDto.class);
//		}	
//	}
//
//	@Override
//	public ResOrderDto createOrderDiscount(OrderDiscountDto orderDiscountDto, Integer accountId, Integer checkoutId) {
//		Account account = this.accountRepository.findById(accountId).orElseThrow(()-> new ResourceNotFoundException("Account","AccountId", accountId));
//		CheckOut checkout = this.checkOutRepository.findById(checkoutId).orElseThrow(()-> new ResourceNotFoundException("CheckOut","CheckOutId", checkoutId));
//		Discount dis = this.discountRepository.findDiscountByCode(orderDiscountDto.getCode());		
//		Order order = this.modelMapper.map(orderDiscountDto, Order.class);
//		order.setAccount(account);
//		order.setCheckout(checkout);
//		order.setDiscount(dis);
//		order.setItems(this.itemRepository.findItemsCurrentByCart(account.getCart().getId()));
//		if(dis == null) {
//			order.setTotalprice(this.cartService.getTotalPriceOfCartCurrent(account.getCart().getId()));
//		}else {
//			Double price = this.cartService.getTotalPriceOfCartCurrent(account.getCart().getId());
//			order.setTotalprice(price - price*dis.getPercent());
//		}
//		BillDto billDto = new BillDto();
//		billDto.setIssuedate(order.getOrderdate());
//		billDto.setTotalprice(order.getTotalprice()+10000);
//		BillDto responseBill = this.billService.createBill(billDto);		
//		Bill bill = this.modelMapper.map(responseBill, Bill.class);
//		order.setBill(bill);	
//		OrderStatus orStatus = this.orderStatusRepository.findOrderStatusByStatusID(1);
//		order.setOrderStatus(orStatus);
//		Order addOrder = this.orderRepository.save(order);
//		 // Update orderId for items in the list
//	    List<Item> items = order.getItems();
//	    for (Item item : items) {
//	        item.setOrder(order); // Assuming there's a setter for orderId in the Item class
//	    }
//	    // Save the updated items back to the repository if needed
//	    this.itemRepository.saveAll(items);
//		return this.modelMapper.map(addOrder, ResOrderDto.class);
//	}
//
//	@Override
//	public List<ResOrderDto> getAllOrder() {
//		List<Order> orders = this.orderRepository.findAll();
//		List<ResOrderDto> orderDtos = orders.stream().map((order) -> this.modelMapper.map(order, ResOrderDto.class))
//				.collect(Collectors.toList());
//		return orderDtos;
//	} 
//	
//	@Override
//	public Integer getAllPurchasesInStore() {
//		return this.orderRepository.getAllPurchases();
//	}
//
//	@Override
//	public Integer getAllPurchasesByProductInStore(Integer productId) {
//		return this.orderRepository.getPurchasesByProduct(productId);
//	}

}
