package com.kt.backend.service;

import java.util.List;

import com.kt.backend.dto.CheckOutDto;

public interface CheckOutService {

	CheckOutDto createCheckOut(CheckOutDto checkOutDto);

	void deleteCheckOut(Integer checkOutId);

	List<CheckOutDto> getCheckOuts();
}
