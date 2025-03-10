package com.kt.backend.service.Impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.backend.dto.DeliveryInfoDto;
import com.kt.backend.dto.DeliveryRatesDto;
import com.kt.backend.dto.DiscountDto;
import com.kt.backend.entity.Address;
import com.kt.backend.entity.DeliveryInfo;
import com.kt.backend.entity.Discount;
import com.kt.backend.entity.Order;
//import com.kt.backend.entity.OrderOnline;
import com.kt.backend.exception.ResourceNotFoundException;
import com.kt.backend.repository.DeliveryInfoRepository;
import com.kt.backend.repository.DiscountRepository;
import com.kt.backend.repository.OrderRepository;
//import com.kt.backend.repository.OrderOnlineRepository;
import com.kt.backend.service.AddressService;
import com.kt.backend.service.DeliveryInfoService;
import com.kt.backend.service.DeliveryRatesService;
import com.kt.backend.service.DiscountService;

import java.util.stream.Collectors;


@Service
public class DeliveryInfoServiceImpl implements DeliveryInfoService{
//
//	@Autowired
//    private DeliveryInfoRepository deliveryInfoRepository;
//	 @Autowired
//	    private AddressService addressService;
//	 @Autowired
//	    private OrderRepository orderOnlineRepository;
//	 @Autowired
//	    private DeliveryRatesService deliveryRatesService;
//
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @Override
//    public DeliveryInfoDto createDeliveryInfo(DeliveryInfoDto deliveryInfoDto) {
//        DeliveryInfo deliveryInfo = modelMapper.map(deliveryInfoDto, DeliveryInfo.class);
//        DeliveryInfo savedDeliveryInfo = deliveryInfoRepository.save(deliveryInfo);
//        return modelMapper.map(savedDeliveryInfo, DeliveryInfoDto.class);
//    }
//
//  // @Override
////    public DeliveryInfoDto createDeliveryInfo(Integer orderOnlineId, String address) {
////        DeliveryInfo deliveryInfo = new DeliveryInfo();
////        deliveryInfo.setOrderOnlineId(orderOnlineId);
////        deliveryInfo.setAddress(address);
////        deliveryInfo.setDeliveryFee(10.0); // Giả sử là phí cố định
////        deliveryInfo.setDeliveryTime(LocalDateTime.now());
////
////        deliveryInfo = deliveryInfoRepository.save(deliveryInfo);
////
////        return modelMapper.map(deliveryInfo, DeliveryInfoDto.class);
////    }
//   
//// Kiểm tra phương thức tạo DeliveryInfo trong deliveryInfoService
////public DeliveryInfoDto createDeliveryInfo(Integer orderOnlineId, String deliveryAddress) {
////    DeliveryInfo deliveryInfo = new DeliveryInfo();
////    deliveryInfo.setOrderOnline(orderOnlineRepository.findById(orderOnlineId).orElseThrow(() -> new RuntimeException("Order not found")));
////    
////    // Giả sử bạn đã có phương thức để lấy Address từ địa chỉ giao hàng
////    Address address = addressService.getAddressByDeliveryAddress(deliveryAddress);
////    deliveryInfo.setAddress(address);
////    
////    deliveryInfo.setDeliveryFee(calculateDeliveryFee(address)); // Ví dụ: tính phí giao hàng dựa trên địa chỉ
////
////    // Lưu thông tin giao hàng
////    deliveryInfo = deliveryInfoRepository.save(deliveryInfo);
////    return modelMapper.map(deliveryInfo, DeliveryInfoDto.class);
////}
//   
//    @Override
//    public DeliveryInfoDto createDeliveryInfo(Integer orderOnlineId, Integer addressId) {
//        DeliveryInfo deliveryInfo = new DeliveryInfo();
//
//        // Tìm đơn hàng theo ID
//        Order orderOnline = orderOnlineRepository.findById(orderOnlineId)
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//
//        // Tìm địa chỉ giao hàng
//        Address address = addressService.getAddressById(addressId);
//        deliveryInfo.setOrderOnline(orderOnline);
//        deliveryInfo.setAddress(address);
//
//        // Lấy phí giao hàng dựa trên thông tin tỉnh và huyện
//        DeliveryRatesDto deliveryRatesDto = deliveryRatesService.getDeliveryRateByProvinceAndDistrict(address.getProvince(), address.getDistrict());
//
//        // Cập nhật phí giao hàng
//        deliveryInfo.setDeliveryFee(deliveryRatesDto.getRate());
//
//        // Lưu thông tin giao hàng
//        deliveryInfo = deliveryInfoRepository.save(deliveryInfo);
//
//        return modelMapper.map(deliveryInfo, DeliveryInfoDto.class);
//    }
//   @Override
//   public DeliveryInfoDto getDeliveryInfoByOrderId(Integer orderOnlineId) {
//       DeliveryInfo deliveryInfo = deliveryInfoRepository.findByOrderOnline_OrderOnlineId(orderOnlineId)
//               .orElseThrow(() -> new RuntimeException("Delivery info not found"));
//       return modelMapper.map(deliveryInfo, DeliveryInfoDto.class);
//   }
//   
////    @Override
////    public DeliveryInfoDto getDeliveryInfoByOrderId(Integer orderOnlineId) {
////        return deliveryInfoRepository.getDeliveryInfoByOrderId(orderOnlineId);
////    }
//    @Override
//    public DeliveryInfoDto updateDeliveryInfo(Integer deliveryInfoId, DeliveryInfoDto deliveryInfoDto) {
//        DeliveryInfo deliveryInfo = deliveryInfoRepository.findById(deliveryInfoId)
//                .orElseThrow(() -> new ResourceNotFoundException("DeliveryInfo", "id", deliveryInfoId));
//        
////        deliveryInfo.setAddressId(deliveryInfoDto.getAddressId());
////        deliveryInfo.setDeliveryFee(deliveryInfoDto.getDeliveryFee());
////        deliveryInfo.setStaffEmail(deliveryInfoDto.getStaffEmail());
////        deliveryInfo.setDeliveryTime(deliveryInfoDto.getDeliveryTime());
////        
//        modelMapper.map(deliveryInfoDto, deliveryInfo);
//        DeliveryInfo updatedDeliveryInfo = deliveryInfoRepository.save(deliveryInfo);
//        return modelMapper.map(updatedDeliveryInfo, DeliveryInfoDto.class);
//    }
//
//    @Override
//    public void deleteDeliveryInfo(Integer deliveryInfoId) {
//        DeliveryInfo deliveryInfo = deliveryInfoRepository.findById(deliveryInfoId)
//                .orElseThrow(() -> new ResourceNotFoundException("DeliveryInfo", "id", deliveryInfoId));
//        deliveryInfoRepository.delete(deliveryInfo);
//    }
//
//    @Override
//    public List<DeliveryInfoDto> getAllDeliveryInfos() {
//        return deliveryInfoRepository.findAll().stream()
//                .map(deliveryInfo -> modelMapper.map(deliveryInfo, DeliveryInfoDto.class))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public DeliveryInfoDto getDeliveryInfoById(Integer deliveryInfoId) {
//        DeliveryInfo deliveryInfo = deliveryInfoRepository.findById(deliveryInfoId)
//                .orElseThrow(() -> new ResourceNotFoundException("DeliveryInfo", "id", deliveryInfoId));
//        return modelMapper.map(deliveryInfo, DeliveryInfoDto.class);
//    }
}
