package com.kt.backend;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.kt.backend.dto.OrderTableDto;
import com.kt.backend.dto.TableItemDto;
import com.kt.backend.entity.OrderTable;
import com.kt.backend.entity.TableItem;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		  // Bỏ qua ánh xạ một số thuộc tính không cần thiết
//		 modelMapper.getConfiguration()
//         .setAmbiguityIgnored(true)  // Bỏ qua ánh xạ mơ hồ
//         .setSkipNullEnabled(true);  // Bỏ qua ánh xạ các giá trị null nếu không cần thiết

//		 modelMapper.typeMap(TableItem.class, TableItemDto.class).addMappings(mapper -> {
//			    mapper.map(src -> src.getOrderTable() != null ? src.getOrderTable().getOrderTableId() : null, TableItemDto::setOrderTableId);
//			    mapper.map(src -> src.getTableInfo() != null ? src.getTableInfo().getTableId() : null, TableItemDto::setTableId);
//			    mapper.map(TableItem::getQuantity, TableItemDto::setQuantity);
//			    mapper.map(TableItem::getPrice, TableItemDto::setPrice);
//			});
		 
//		  // Cấu hình ánh xạ cụ thể cho TableItem -> TableItemDto
//		    modelMapper.typeMap(TableItem.class, TableItemDto.class).addMappings(mapper -> {
//		        mapper.map(src -> src.getOrderTable() != null ? src.getOrderTable().getOrderTableId() : null, TableItemDto::setOrderTableId);
//		        mapper.map(src -> src.getProduct() != null ? src.getProduct().getId() : null, TableItemDto::setProductId);
//		        mapper.map(src -> src.getTableInfo() != null ? src.getTableInfo().getTableId() : null, TableItemDto::setTableId);
//		    });
//
//		    // Cấu hình ánh xạ cụ thể cho OrderTable -> OrderTableDto
//		    modelMapper.typeMap(OrderTable.class, OrderTableDto.class).addMappings(mapper -> {
//		        mapper.map(src -> src.getTableInfo() != null ? src.getTableInfo().getTableId() : null, OrderTableDto::setTableId);
//		        mapper.map(src -> src.getOrderStatus() != null ? src.getOrderStatus().getId() : null, OrderTableDto::setOrderStatusId);
//		    });
		return new ModelMapper();
	}
}
