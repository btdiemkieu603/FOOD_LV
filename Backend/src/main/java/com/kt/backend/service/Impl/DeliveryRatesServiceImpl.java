package com.kt.backend.service.Impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.backend.dto.DeliveryInfoDto;
import com.kt.backend.dto.DeliveryRatesDto;
import com.kt.backend.dto.DiscountDto;
import com.kt.backend.entity.DeliveryInfo;
import com.kt.backend.entity.DeliveryRates;
import com.kt.backend.entity.Discount;
import com.kt.backend.exception.ResourceNotFoundException;
import com.kt.backend.repository.DeliveryInfoRepository;
import com.kt.backend.repository.DeliveryRatesRepository;
import com.kt.backend.repository.DiscountRepository;
import com.kt.backend.service.DeliveryInfoService;
import com.kt.backend.service.DeliveryRatesService;
import com.kt.backend.service.DiscountService;

import java.util.stream.Collectors;


@Service
public class DeliveryRatesServiceImpl implements DeliveryRatesService{

	 @Autowired
	    private DeliveryRatesRepository deliveryRatesRepository;

	    @Autowired
	    private ModelMapper modelMapper;

	    @Override
	    public DeliveryRatesDto createDeliveryRate(DeliveryRatesDto deliveryRatesDto) {
	        DeliveryRates deliveryRates = modelMapper.map(deliveryRatesDto, DeliveryRates.class);
	        DeliveryRates savedDeliveryRates = deliveryRatesRepository.save(deliveryRates);
	        return modelMapper.map(savedDeliveryRates, DeliveryRatesDto.class);
	    }

	    @Override
	    public DeliveryRatesDto updateDeliveryRate(Integer deliveryRateId, DeliveryRatesDto deliveryRatesDto) {
	        DeliveryRates deliveryRates = deliveryRatesRepository.findById(deliveryRateId)
	                .orElseThrow(() -> new ResourceNotFoundException("DeliveryRates", "id", deliveryRateId));
	        modelMapper.map(deliveryRatesDto, deliveryRates);
	        DeliveryRates updatedDeliveryRates = deliveryRatesRepository.save(deliveryRates);
	        return modelMapper.map(updatedDeliveryRates, DeliveryRatesDto.class);
	    }
	    @Override
	    public DeliveryRatesDto getDeliveryRateByProvinceAndDistrict(String province, String district) {
	        DeliveryRates deliveryRates = deliveryRatesRepository.findByProvinceAndDistrict(province, district)
	                .orElseThrow(() -> new RuntimeException("Delivery rate not found for city: " + province + ", district: " + district));
	        return modelMapper.map(deliveryRates, DeliveryRatesDto.class);
	    }

	    @Override
	    public void deleteDeliveryRate(Integer deliveryRateId) {
	        DeliveryRates deliveryRates = deliveryRatesRepository.findById(deliveryRateId)
	                .orElseThrow(() -> new ResourceNotFoundException("DeliveryRates", "id", deliveryRateId));
	        deliveryRatesRepository.delete(deliveryRates);
	    }

	    @Override
	    public List<DeliveryRatesDto> getAllDeliveryRates() {
	        return deliveryRatesRepository.findAll().stream()
	                .map(deliveryRates -> modelMapper.map(deliveryRates, DeliveryRatesDto.class))
	                .collect(Collectors.toList());
	    }

	    @Override
	    public DeliveryRatesDto getDeliveryRateById(Integer deliveryRateId) {
	        DeliveryRates deliveryRates = deliveryRatesRepository.findById(deliveryRateId)
	                .orElseThrow(() -> new ResourceNotFoundException("DeliveryRates", "id", deliveryRateId));
	        return modelMapper.map(deliveryRates, DeliveryRatesDto.class);
	    }
}
