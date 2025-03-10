//package com.kt.backend.Payment;
//
//import java.security.MessageDigest;
//import java.util.*;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//
//public class VNPayUtils {
//
//    // Hàm này để tạo chữ ký từ các tham số yêu cầu
//    public static String hashAllFields(Map<String, String> fields, String secretKey) {
//        try {
//            // Sắp xếp các trường theo thứ tự từ điển
//            List<String> fieldNames = new ArrayList<>(fields.keySet());
//            Collections.sort(fieldNames);
//
//            // Xây dựng chuỗi dữ liệu theo định dạng "key=value&key=value"
//            StringBuilder sb = new StringBuilder();
//            for (String fieldName : fieldNames) {
//                String value = fields.get(fieldName);
//                if (value != null && !value.isEmpty()) {
//                    if (sb.length() > 0) {
//                        sb.append("&");
//                    }
//                    // Mã hóa URL cho tham số
//                    sb.append(fieldName).append("=").append(URLEncoder.encode(value, "UTF-8"));
//                }
//            }
//
//            // Thêm các tham số cần thiết cho chữ ký
//            sb.append("&vnp_SecureHashType=SHA256");
//
//            // Tạo HMAC SHA256
//            String hashString = secureHash(sb.toString(), secretKey);
//            sb.append("&vnp_SecureHash=" + hashString);
//
//            return sb.toString();
//        } catch (Exception e) {
//            throw new RuntimeException("Error while hashing fields", e);
//        }
//    }
//
//    // Hàm tạo chữ ký SHA256 thủ công
//    private static String secureHash(String data, String secretKey) throws Exception {
//        // Kết hợp dữ liệu và secret key
//        MessageDigest digest = MessageDigest.getInstance("SHA-256");
//        digest.update((data + secretKey).getBytes("UTF-8"));
//        byte[] hashBytes = digest.digest();
//        StringBuilder hexString = new StringBuilder();
//        for (byte b : hashBytes) {
//            String hex = Integer.toHexString(0xff & b);
//            if (hex.length() == 1) {
//                hexString.append('0');
//            }
//            hexString.append(hex);
//        }
//        return hexString.toString();
//    }
//}
package com.kt.backend.Payment;

import java.security.MessageDigest;
import java.util.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class VNPayUtils {

    // Hàm này để tạo chữ ký từ các tham số yêu cầu
    public static String hashAllFields(Map<String, String> fields, String secretKey) {
        try {
            // Sắp xếp các trường theo thứ tự từ điển
            List<String> fieldNames = new ArrayList<>(fields.keySet());
            Collections.sort(fieldNames);

            // Xây dựng chuỗi dữ liệu theo định dạng "key=value&key=value"
            StringBuilder sb = new StringBuilder();
            for (String fieldName : fieldNames) {
                String value = fields.get(fieldName);
                if (value != null && !value.isEmpty()) {
                    if (sb.length() > 0) {
                        sb.append("&");
                    }
                    // Mã hóa URL cho tham số
                    sb.append(fieldName).append("=").append(URLEncoder.encode(value, "UTF-8"));
                }
            }

            // Thêm các tham số cần thiết cho chữ ký
            sb.append("&vnp_SecureHashType=SHA256");

            // Tạo HMAC SHA256
            String hashString = secureHash(sb.toString(), secretKey);
            sb.append("&vnp_SecureHash=" + hashString);

            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error while hashing fields", e);
        }
    }

    // Hàm tạo chữ ký SHA256 thủ công
    private static String secureHash(String data, String secretKey) throws Exception {
        // Kết hợp dữ liệu và secret key
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update((data + secretKey).getBytes("UTF-8"));
        byte[] hashBytes = digest.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
