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
import com.kt.backend.dto.DiscountDto;
import com.kt.backend.entity.Discount;
import com.kt.backend.response.ApiResponse;
import com.kt.backend.service.DeliveryInfoService;
import com.kt.backend.service.DiscountService;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://192.168.234.28:30030")
@CrossOrigin(origins = "${allowedOrigins}")
@RequestMapping("/api/delivery_info")
public class DeliveryInfoController {

//	@Autowired
//    private DeliveryInfoService deliveryInfoService;
//
//    @PostMapping
//    public ResponseEntity<DeliveryInfoDto> createDeliveryInfo(@RequestBody DeliveryInfoDto deliveryInfoDto) {
//        return new ResponseEntity<>(deliveryInfoService.createDeliveryInfo(deliveryInfoDto), HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<DeliveryInfoDto> updateDeliveryInfo(@PathVariable("id") Integer id,
//                                                                @RequestBody DeliveryInfoDto deliveryInfoDto) {
//        return new ResponseEntity<>(deliveryInfoService.updateDeliveryInfo(id, deliveryInfoDto), HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteDeliveryInfo(@PathVariable("id") Integer id) {
//        deliveryInfoService.deleteDeliveryInfo(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<DeliveryInfoDto>> getAllDeliveryInfos() {
//        return new ResponseEntity<>(deliveryInfoService.getAllDeliveryInfos(), HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<DeliveryInfoDto> getDeliveryInfoById(@PathVariable("id") Integer id) {
//        return new ResponseEntity<>(deliveryInfoService.getDeliveryInfoById(id), HttpStatus.OK);
//    }
//    // Lấy thông tin giao hàng theo ID đơn hàng
//    @GetMapping("/order/{orderOnlineId}")
//    public ResponseEntity<DeliveryInfoDto> getDeliveryInfoByOrderId(@PathVariable Integer orderOnlineId) {
//        DeliveryInfoDto deliveryInfoDto = deliveryInfoService.getDeliveryInfoByOrderId(orderOnlineId);
//        return new ResponseEntity<>(deliveryInfoDto, HttpStatus.OK);
//    }
//    
}
