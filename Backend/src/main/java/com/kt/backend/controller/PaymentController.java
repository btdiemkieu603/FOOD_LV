package com.kt.backend.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kt.backend.Payment.Config;


import lombok.RequiredArgsConstructor;



@RestController
//@CrossOrigin(origins = "${allowedOrigins}")
@CrossOrigin(origins = "*")
@RequestMapping("/api/payment")
@RequiredArgsConstructor 
public class PaymentController {

	@GetMapping("/pay")
	public Map<String, String> getPay( @RequestParam long amount
//	public String getPay( @RequestParam long amount    
            ) throws UnsupportedEncodingException{
		
		String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount2 = amount*100;
        String bankCode = "NCB";
        
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";

        String vnp_TmnCode = Config.vnp_TmnCode;
        
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount2));
        vnp_Params.put("vnp_CurrCode", "VND");
        
        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
		
		//return paymentUrl;
        Map<String, String> response = new HashMap<>();
        response.put("paymentUrl", paymentUrl);
        return response;
	}

	@GetMapping("/vnpay_return")
//	public String vnpayReturn(@RequestParam Map<String, String> vnpParams) {
	public ResponseEntity<String> vnpayReturn(@RequestParam Map<String, String> vnpParams) {
		 
	try {
	        // Lấy giá trị SecureHash trả về từ VNPay
	        String vnp_SecureHash = vnpParams.get("vnp_SecureHash");
	        vnpParams.remove("vnp_SecureHash");  // Loại bỏ tham số SecureHash khỏi map tham số

	        List fieldNames = new ArrayList(vnpParams.keySet());
	        Collections.sort(fieldNames);
	        StringBuilder hashData = new StringBuilder();
	        StringBuilder query = new StringBuilder();
	        Iterator itr = fieldNames.iterator();
	        while (itr.hasNext()) {
	            String fieldName = (String) itr.next();
	            String fieldValue = (String) vnpParams.get(fieldName);
	            if ((fieldValue != null) && (fieldValue.length() > 0)) {
	                //Build hash data
	                hashData.append(fieldName);
	                hashData.append('=');
	                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
	                //Build query
	                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
	                query.append('=');
	                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
	                if (itr.hasNext()) {
	                    query.append('&');
	                    hashData.append('&');
	                }
	            }
	        }
	    
	        // Tính toán lại SecureHash
	        String calculatedSecureHash = Config.hmacSHA512(Config.secretKey,  hashData.toString());

	        // Kiểm tra nếu chữ ký hợp lệ
//	        if (vnp_SecureHash.equals(calculatedSecureHash)) {
//	            // Kiểm tra mã phản hồi từ VNPay
//	            String responseCode = vnpParams.get("vnp_ResponseCode");
//
//	            if ("00".equals(responseCode)) {
//	                // Thanh toán thành công
//	                return "Payment Successful";
//	            } else {
//	                // Thất bại
//	                return "Payment Failed with response code: " + responseCode;
//	            }
//	        } else {
//	            // Chữ ký không hợp lệ
//	            return "Invalid secure hash!";
//	        }
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return "Error while processing payment response";
//	    }
//	}   
	        if (vnp_SecureHash.equals(calculatedSecureHash)) {
	            String responseCode = vnpParams.get("vnp_ResponseCode");
	            if ("00".equals(responseCode)) {
	                return ResponseEntity.ok("Payment Successful");
	            } else {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment Failed with response code: " + responseCode);
	            }
	        } else {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid secure hash!");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while processing payment response");
	    }
	}  
	
	}
