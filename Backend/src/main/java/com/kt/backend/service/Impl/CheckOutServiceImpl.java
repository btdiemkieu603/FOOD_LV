package com.kt.backend.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.backend.dto.CheckOutDto;
import com.kt.backend.entity.CheckOut;
import com.kt.backend.exception.ResourceNotFoundException;
import com.kt.backend.repository.CheckOutRepository;
import com.kt.backend.service.CheckOutService;

@Service
public class CheckOutServiceImpl implements CheckOutService{

	@Autowired
	private CheckOutRepository checkOutRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CheckOutDto createCheckOut(CheckOutDto checkOutDto) {
		CheckOut checkOut = this.modelMapper.map(checkOutDto, CheckOut.class);
		CheckOut addCheckOut = this.checkOutRepository.save(checkOut);
		return this.modelMapper.map(addCheckOut, CheckOutDto.class);
	}

	@Override
	public void deleteCheckOut(Integer checkOutId) {
		CheckOut checkOut = this.checkOutRepository.findById(checkOutId)
				.orElseThrow(()-> new ResourceNotFoundException("CheckOut ","CheckOutId", checkOutId));
		this.checkOutRepository.delete(checkOut);
	}

	@Override
	public List<CheckOutDto> getCheckOuts() {
		List<CheckOut> checkOuts = this.checkOutRepository.findAll();
		List<CheckOutDto> checkOutDtos = checkOuts.stream().map((checkOut)-> this.modelMapper.map(checkOut, CheckOutDto.class)).collect(Collectors.toList());	
		return checkOutDtos;
	}

}
