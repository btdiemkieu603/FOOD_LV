package com.kt.backend.service;

import java.util.List;
import java.util.Optional;

import com.kt.backend.dto.DeliveryInfoDto;
import com.kt.backend.dto.DeliveryRatesDto;
import com.kt.backend.dto.DiscountDto;
import com.kt.backend.entity.Discount;

public interface DeliveryRatesService {

	 DeliveryRatesDto createDeliveryRate(DeliveryRatesDto deliveryRatesDto);
	    DeliveryRatesDto updateDeliveryRate(Integer deliveryRateId, DeliveryRatesDto deliveryRatesDto);
	    void deleteDeliveryRate(Integer deliveryRateId);
	    List<DeliveryRatesDto> getAllDeliveryRates();
	    DeliveryRatesDto getDeliveryRateById(Integer deliveryRateId);
	    
	    DeliveryRatesDto getDeliveryRateByProvinceAndDistrict(String province, String district);
}
