package com.kt.backend.service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.backend.dto.OrderStatusDto;
import com.kt.backend.entity.OrderStatus;
import com.kt.backend.exception.ResourceNotFoundException;
import com.kt.backend.repository.OrderStatusRepository;
import com.kt.backend.service.OrderStatusService;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {

	@Autowired
	private OrderStatusRepository orderStatusRepository;

	@Autowired
	private ModelMapper modelMapper;

	
	@Override
	public OrderStatusDto createOrderStatus(OrderStatusDto orderStatusDto) {
		OrderStatus orderStatus = this.modelMapper.map(orderStatusDto, OrderStatus.class);
		OrderStatus addOrderStatus = this.orderStatusRepository.save(orderStatus);
		return this.modelMapper.map(addOrderStatus, OrderStatusDto.class);
	}

	@Override
	public void deleteOrderStatus(Integer orderStatusId) {
		OrderStatus orderStatus = this.orderStatusRepository.findById(orderStatusId)
				.orElseThrow(() -> new ResourceNotFoundException("OrderStatus ", "OrderStatusId", orderStatusId));
		this.orderStatusRepository.delete(orderStatus);
	}

	@Override
	public List<OrderStatusDto> getOrderStatuses() {
		List<OrderStatus> orderStatuses = this.orderStatusRepository.findAll();
		List<OrderStatusDto> orderStatusDtos = orderStatuses.stream().map((orderStatus)-> this.modelMapper.map(orderStatus, OrderStatusDto.class)).collect(Collectors.toList());	
		return orderStatusDtos;
	}

	@Override
	public Optional<OrderStatus> findOrderStatus(String status) {
		OrderStatus orderStatus = this.orderStatusRepository.findOrderStatusByStatus(status);
		return Optional.ofNullable(orderStatus);
	}

}
