package com.kt.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.backend.dto.DeliveryInfoDto;
import com.kt.backend.dto.DeliveryRatesDto;
import com.kt.backend.dto.DiscountDto;
import com.kt.backend.entity.Discount;
import com.kt.backend.response.ApiResponse;
import com.kt.backend.service.DeliveryInfoService;
import com.kt.backend.service.DeliveryRatesService;
import com.kt.backend.service.DiscountService;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://192.168.234.28:30030")
@CrossOrigin(origins = "${allowedOrigins}")
@RequestMapping("/api/delivery_rates")
public class DeliveryRatesController {

	 @Autowired
	    private DeliveryRatesService deliveryRatesService;

	    @PostMapping("/add")
	    public ResponseEntity<DeliveryRatesDto> createDeliveryRate(@RequestBody DeliveryRatesDto deliveryRatesDto) {
	        return new ResponseEntity<>(deliveryRatesService.createDeliveryRate(deliveryRatesDto), HttpStatus.CREATED);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<DeliveryRatesDto> updateDeliveryRate(@PathVariable("id") Integer id,
	                                                                @RequestBody DeliveryRatesDto deliveryRatesDto) {
	        return new ResponseEntity<>(deliveryRatesService.updateDeliveryRate(id, deliveryRatesDto), HttpStatus.OK);
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteDeliveryRate(@PathVariable("id") Integer id) {
	        deliveryRatesService.deleteDeliveryRate(id);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }

	    @GetMapping
	    public ResponseEntity<List<DeliveryRatesDto>> getAllDeliveryRates() {
	        return new ResponseEntity<>(deliveryRatesService.getAllDeliveryRates(), HttpStatus.OK);
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<DeliveryRatesDto> getDeliveryRateById(@PathVariable("id") Integer id) {
	        return new ResponseEntity<>(deliveryRatesService.getDeliveryRateById(id), HttpStatus.OK);
	    }
	    
	    // Lấy mức phí giao hàng theo thành phố và quận
	    @GetMapping("/province/{province}/district/{district}")
	    //public ResponseEntity<DeliveryRatesDto> getDeliveryRateByCityAndDistrict(@PathVariable String city, @PathVariable String district) {
	    public ResponseEntity<Double> getDeliveryRateByCityAndDistrict(@PathVariable String province, @PathVariable String district) {
	        
	    DeliveryRatesDto deliveryRatesDto = deliveryRatesService.getDeliveryRateByProvinceAndDistrict(province, district);
	        //return new ResponseEntity<>(deliveryRatesDto, HttpStatus.OK);
	     // Trả về chỉ mức phí giao hàng
	        return new ResponseEntity<>(deliveryRatesDto.getRate(), HttpStatus.OK);
	    }
	    
	    
}
