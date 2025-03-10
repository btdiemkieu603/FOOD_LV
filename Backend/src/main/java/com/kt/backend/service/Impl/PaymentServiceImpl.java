//package com.kt.backend.service.impl;
//
//import com.kt.backend.dto.PaymentDTO;
//import com.kt.backend.service.PaymentService;
//
//import org.springframework.stereotype.Service;
//import jakarta.servlet.http.HttpServletRequest;
//
//@Service
//public class PaymentServiceImpl implements PaymentService {
//
//    @Override
//    public PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request) {
//        // Giả sử VNPayUtils có phương thức để tạo đơn thanh toán
//        String orderId = VNPayUtilsGG.createOrderId();
//        String amount = VNPayUtilsGG.getAmountFromRequest(request);
//        
//        // Trả về kết quả dưới dạng DTO
//        return new PaymentDTO.VNPayResponse("00", "Success", "Payment order created successfully");
//    }
//
//    @Override
//    public PaymentDTO.VNPayResponse handleVnPayCallback(HttpServletRequest request) {
//        // Xử lý callback từ VNPay
//        String status = request.getParameter("vnp_ResponseCode");
//        if ("00".equals(status)) {
//            // Cập nhật trạng thái thanh toán nếu cần
//            return new PaymentDTO.VNPayResponse("00", "Success", "Payment successful");
//        } else {
//            return new PaymentDTO.VNPayResponse("01", "Failed", "Payment failed");
//        }
//    }
//}
