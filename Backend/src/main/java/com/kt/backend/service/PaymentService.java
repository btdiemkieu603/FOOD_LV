//package com.kt.backend.service;
//
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import com.kt.backend.Payment.VNPayConfig;
//import com.kt.backend.Payment.VNPayUtil;
//import com.kt.backend.dto.PaymentDTO;
//
//import java.util.*;
//
//@Service
//@RequiredArgsConstructor
//public class PaymentService {
//    private final VNPayConfig vnPayConfig;
//
//    public PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request) {
//        // Lấy số tiền và mã ngân hàng từ request
//        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
//        String bankCode = request.getParameter("bankCode");
//
//        // Lấy tham số cấu hình VNPay
//        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
//        vnpParamsMap.put("vnp_Amount", String.valueOf(amount)); // Số tiền thanh toán
//
//        // Nếu có mã ngân hàng, thêm vào tham số
//        if (bankCode != null && !bankCode.isEmpty()) {
//            vnpParamsMap.put("vnp_BankCode", bankCode);
//        }
//
//        // Thêm địa chỉ IP của người dùng
//        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
//
//        // Tạo URL thanh toán
//        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
//        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
//        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
//
//        // Thêm SecureHash vào query URL
//        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
//        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
//
//        return PaymentDTO.VNPayResponse.builder()
//                .code("ok")
//                .message("success")
//                .paymentUrl(paymentUrl).build();
//    }
//}


package com.kt.backend.service;

import com.kt.backend.dto.PaymentDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {

    PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request);
    PaymentDTO.VNPayResponse handleVnPayCallback(HttpServletRequest request);
}

