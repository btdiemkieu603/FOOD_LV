package com.kt.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.backend.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{
	//List<Address> findByCustomerEmail(String customerEmail);
	 Optional<Address> findByWardAndProvinceAndDistrict(String ward, String district, String province);
	// Optional<Address> findByCustomer_Email(String customerEmail);
	 Optional<Address> findByWardAndDistrictAndProvince(String ward, String district, String province);

}
