package com.kt.backend.service;

import java.util.List;
import java.util.Optional;

import com.kt.backend.dto.OrderStatusDto;
import com.kt.backend.entity.OrderStatus;

public interface OrderStatusService {

	OrderStatusDto createOrderStatus(OrderStatusDto orderStatusDto);

	void deleteOrderStatus(Integer orderStatusId);

	List<OrderStatusDto> getOrderStatuses();

	Optional<OrderStatus> findOrderStatus(String status);

}
