//package com.kt.backend.Payment;
//
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.Getter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//@Configuration
//public class VNPayConfig {
//    @Getter
//    @Value("${PAY_URL}")
//    private String vnp_PayUrl;
//
//    @Value("${RETURN_URL}")
//    private String vnp_ReturnUrl;
//
//    @Value("${TMN_CODE}")
//    private String vnp_TmnCode;
//
//    @Getter
//    @Value("${SECRET_KEY}")
//    private String secretKey;
//
//    @Value("${VERSION}")
//    private String vnp_Version;
//
//    @Value("${COMMAND}")
//    private String vnp_Command;
//
//    @Value("${ORDER_TYPE}")
//    private String orderType;
//
//    // Cấu hình VNPay
////    public Map<String, String> getVNPayConfig() {
////        Map<String, String> vnpParamsMap = new HashMap<>();
////        vnpParamsMap.put("vnp_Version", this.vnp_Version);
////        vnpParamsMap.put("vnp_Command", this.vnp_Command);
////        vnpParamsMap.put("vnp_TmnCode", this.vnp_TmnCode);
////        vnpParamsMap.put("vnp_CurrCode", "VND");
////        vnpParamsMap.put("vnp_TxnRef", VNPayUtils.getRandomNumber(8)); // Mã giao dịch
////        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan don hang: " + VNPayUtils.getRandomNumber(8));
////        vnpParamsMap.put("vnp_OrderType", this.orderType);
////        vnpParamsMap.put("vnp_Locale", "vn");
////        vnpParamsMap.put("vnp_ReturnUrl", this.vnp_ReturnUrl);
////
////        // Lấy thời gian tạo và hết hạn giao dịch
////        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
////        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
////        String vnpCreateDate = formatter.format(calendar.getTime());
////        vnpParamsMap.put("vnp_CreateDate", vnpCreateDate);
////
////        // Thêm thời gian hết hạn giao dịch
////        calendar.add(Calendar.MINUTE, 15);
////        String vnp_ExpireDate = formatter.format(calendar.getTime());
////        vnpParamsMap.put("vnp_ExpireDate", vnp_ExpireDate);
////
////        return vnpParamsMap;
////    }
//}
//
//public static String getRandomNumber(int len) {
//    Random rnd = new Random();
//    String chars = "0123456789";
//    StringBuilder sb = new StringBuilder(len);
//    for (int i = 0; i < len; i++) {
//        sb.append(chars.charAt(rnd.nextInt(chars.length())));
//    }
//    return sb.toString();





