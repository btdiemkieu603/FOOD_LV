package com.example.mobile.model;

public class VnPayResponse {
    private String paymentUrl;  // URL thanh toán VNPAY
    private String responseCode;  // Mã phản hồi từ VNPAY
    private String message;  // Thông điệp từ VNPAY (nếu có)

    // Getter và Setter
    public String geVnPayResponsetPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // ToString method nếu cần để kiểm tra giá trị của đối tượng
    @Override
    public String toString() {
        return "VnPayResponse{" +
                "paymentUrl='" + paymentUrl + '\'' +
                ", responseCode='" + responseCode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}