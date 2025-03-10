//package com.kt.backend.dto;
//
//
//import lombok.Builder;
//
//public abstract class PaymentDTO {
//    @Builder
//    public static class VNPayResponse {
//        public String code;
//        public String message;
//        public String paymentUrl;
//		public String getCode() {
//			return code;
//		}
//		public void setCode(String code) {
//			this.code = code;
//		}
//		public String getMessage() {
//			return message;
//		}
//		public void setMessage(String message) {
//			this.message = message;
//		}
//		public String getPaymentUrl() {
//			return paymentUrl;
//		}
//		public void setPaymentUrl(String paymentUrl) {
//			this.paymentUrl = paymentUrl;
//		}
//        
//        
//    }
//}


package com.kt.backend.dto;

public class PaymentDTO {

    public static class VNPayResponse {
        private String code;
        private String message;
        private String additionalInfo;

        public VNPayResponse(String code, String message, String additionalInfo) {
            this.code = code;
            this.message = message;
            this.additionalInfo = additionalInfo;
        }

        // Getters and Setters
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getAdditionalInfo() {
            return additionalInfo;
        }

        public void setAdditionalInfo(String additionalInfo) {
            this.additionalInfo = additionalInfo;
        }
    }

    public static class PaymentRequest {
        private String orderId;
        private String amount;

        // Constructor, Getters and Setters
        public PaymentRequest(String orderId, String amount) {
            this.orderId = orderId;
            this.amount = amount;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
}
