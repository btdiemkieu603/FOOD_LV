package com.kt.backend.controller;

import com.kt.backend.Payment.VNPayUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/vnpay")
public class VNPayController {

    private static final String vnp_TmnCode = "Z19WD11X"; // Mã Merchant
    private static final String vnp_HashSecret = "7IB7KPE4RCQD9PONLPI9QMSOK6PH5I6S"; // Khóa bí mật VNPay
    private static final String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html"; // URL thanh toán
    private static final String vnp_ReturnUrl = "https://jollibee.com.vn/"; // URL quay lại sau khi thanh toán
    private static final String vnp_NotifyUrl = "http://localhost:8080/api/vnpay/ipn"; // URL thông báo kết quả thanh toán
    private static final String bankCode = "NCB"; // Ngân hàng mặc định

    // Tạo số ngẫu nhiên với độ dài xác định
    private String getRandomNumber(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10)); // Thêm một số ngẫu nhiên từ 0 đến 9
        }
        return sb.toString();
    }

    // Tạo URL thanh toán
    @GetMapping("/create-payment")
    public ResponseEntity<?> createPayment(@RequestParam double amount) {
        try {
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";

            // Tạo mã giao dịch (TxnRef) bằng số ngẫu nhiên 8 ký tự
            String vnp_TxnRef = getRandomNumber(8); // Sử dụng phương thức getRandomNumber để tạo TxnRef
            String vnp_IpAddr = "127.0.0.1"; // Địa chỉ IP (giả sử localhost cho phát triển)

            // Định dạng thời gian theo yêu cầu của VNPay
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = dateFormat.format(new Date());

            // Tạo thời gian hết hạn: 15 phút sau thời gian tạo giao dịch
            Calendar expireCalendar = Calendar.getInstance();
            expireCalendar.setTime(new Date());
            expireCalendar.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = dateFormat.format(expireCalendar.getTime());

            // Tạo map chứa các tham số cần thiết
            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);  // Mã merchant
            vnp_Params.put("vnp_Amount", String.valueOf((int) (amount * 100)));  // Số tiền, VNPay yêu cầu tính theo đồng
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_BankCode", bankCode); // Ngân hàng mặc định
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef); // Sử dụng số ngẫu nhiên làm TxnRef
            vnp_Params.put("vnp_OrderInfo", "Payment for Order #" + vnp_TxnRef);
            vnp_Params.put("vnp_Locale", "vn"); // Ngôn ngữ
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
            vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
            vnp_Params.put("vnp_NotifyUrl", vnp_NotifyUrl); // URL IPN
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            // Tạo chữ ký
            String queryString = VNPayUtils.hashAllFields(vnp_Params, vnp_HashSecret);

            // Xây dựng URL thanh toán
            String paymentUrl = vnp_PayUrl + "?" + queryString;

            return ResponseEntity.ok(paymentUrl);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi: " + e.getMessage());
        }
    }

    // Xử lý kết quả trả về từ VNPay
    @GetMapping("/return")
    public ResponseEntity<?> vnpReturn(@RequestParam Map<String, String> allParams) {
        String vnp_ResponseCode = allParams.get("vnp_ResponseCode");
        if ("00".equals(vnp_ResponseCode)) {
            return ResponseEntity.ok("Thanh toán thành công!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thanh toán không thành công!");
        }
    }

    // Xử lý thông báo kết quả thanh toán (IPN)
    @GetMapping("/ipn")
    public ResponseEntity<?> vnpIpn(@RequestParam Map<String, String> allParams) {
        String vnp_ResponseCode = allParams.get("vnp_ResponseCode");
        if ("00".equals(vnp_ResponseCode)) {
            // Xử lý khi thanh toán thành công
            return ResponseEntity.ok("Giao dịch thành công");
        } else {
            // Xử lý khi thanh toán không thành công
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Giao dịch không thành công");
        }
    }
}
