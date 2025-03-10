package com.kt.backend.service;

import com.kt.backend.dto.AddressDto;
import com.kt.backend.entity.Address;

public interface AddressService {

	AddressDto createAddress(AddressDto addressDto);

	AddressDto getAddress(Integer addressId);

	void deleteAddress(Integer addressId);
	
	
	 Address getAddressByDetails(String deliveryAddress);
	    AddressDto getAddressByDeliveryDetails(String ward, String district, String province);
	    
	    Address getAddressById(Integer addressId);
}
