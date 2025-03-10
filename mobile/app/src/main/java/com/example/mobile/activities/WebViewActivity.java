//package com.example.mobile.activities;
//import android.net.Uri;
//import android.os.Bundle;
//import android.webkit.WebResourceRequest;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import com.example.mobile.R;
//public class WebViewActivity extends AppCompatActivity {
//    private WebView webView;
//
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_webview); // layout chứa WebView
////
////        webView = findViewById(R.id.webView);
////
////        // Bật JavaScript nếu cần
////        webView.getSettings().setJavaScriptEnabled(true);
////
////        // Lấy URL thanh toán từ Intent
////        String paymentUrl = getIntent().getStringExtra("payment_url");
////
////        // Đảm bảo URL không null trước khi load
////        if (paymentUrl != null) {
////            webView.loadUrl(paymentUrl);
////        }
////
////        // Thiết lập WebViewClient để các liên kết trong trang web không mở ngoài ứng dụng
////        webView.setWebViewClient(new WebViewClient());
////    }
//@Override
//protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_webview);
//
//    webView = findViewById(R.id.webView);
//    webView.getSettings().setJavaScriptEnabled(true);
//
//    String paymentUrl = getIntent().getStringExtra("payment_url");
//
//    if (paymentUrl != null) {
//        webView.loadUrl(paymentUrl);
//    }
//
//    webView.setWebViewClient(new WebViewClient() {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            String url = request.getUrl().toString();
//
//            // Kiểm tra URL callback (ví dụ backend cung cấp URL xác nhận kết quả)
//            if (url.contains("vnpay_return")) {
//                // Xử lý kết quả thanh toán tại đây (thành công, thất bại, hoặc hủy)
//                handlePaymentResult(url);
//                return true;
//            }
//            //view.loadUrl(url);
//            return false;
//        }
//    });
//}
//
//    // Xử lý kết quả thanh toán từ callback URL
//    private void handlePaymentResult(String url) {
//        // Tùy thuộc vào cấu trúc URL callback mà backend trả về
////        if (url.contains("success")) {
////            Toast.makeText(this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
////        } else if (url.contains("cancel")) {
////            Toast.makeText(this, "Hủy giao dịch", Toast.LENGTH_SHORT).show();
////        } else {
////            Toast.makeText(this, "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
////        }
////
////        // Quay về màn hình trước đó
////        finish();
//
//        Uri uri = Uri.parse(url);
//        String responseCode = uri.getQueryParameter("vnp_ResponseCode");
//        String message;
//
//        if ("00".equals(responseCode)) {
//            message = "Thanh toán thành công";
//        } else if ("24".equals(responseCode)) {
//            message = "Hủy giao dịch";
//        } else {
//            message = "Thanh toán thất bại: " + responseCode;
//        }
//
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//
//        // Quay về màn hình trước đó
//        finish();
//    }
//
//}



package com.example.mobile.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile.R;
import com.example.mobile.controller.PaymentInformation;
//
//public class WebViewActivity extends AppCompatActivity {
//    private WebView webView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_webview);
//
//        webView = findViewById(R.id.webView);
//        webView.getSettings().setJavaScriptEnabled(true);
//
//        // Lấy URL thanh toán từ Intent
//        String paymentUrl = getIntent().getStringExtra("payment_url");
//
//        // Kiểm tra URL hợp lệ trước khi load
//        if (paymentUrl != null) {
//            webView.loadUrl(paymentUrl);
//        }
//
//        // Cấu hình WebViewClient để không mở liên kết ngoài WebView
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                String url = request.getUrl().toString();
//
//                // Kiểm tra URL callback (ví dụ như vnpay_return)
//                if (url.contains("vnpay_return")) {
//                    // Xử lý kết quả thanh toán
//                    handlePaymentResult(url);
//                    return true;
//                }
//
//                // Nếu không phải URL callback, mở tiếp trong WebView
//                return false;
//            }
//        });
//    }
//
//    // Xử lý kết quả thanh toán từ callback URL
//    private void handlePaymentResult(String url) {
//        Uri uri = Uri.parse(url);
//        String responseCode = uri.getQueryParameter("vnp_ResponseCode");
//        String message;
//
//        // Kiểm tra mã phản hồi của VNPay
//        if ("00".equals(responseCode)) {
//            message = "Thanh toán thành công";
//        } else if ("24".equals(responseCode)) {
//            message = "Hủy giao dịch";
//        } else {
//            message = "Thanh toán thất bại: " + responseCode;
//        }
//
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//
//        // Quay lại màn hình trước đó
//        finish();
//    }
//}

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        // Lấy URL thanh toán từ Intent
        String paymentUrl = getIntent().getStringExtra("paymentUrl");

        if (paymentUrl != null) {
            // Mở trang web thanh toán trong WebView
            webView.loadUrl(paymentUrl);
            //webView.setWebViewClient(new WebViewClient());
            webView.setWebViewClient(new WebViewClient() {
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                    // Kiểm tra nếu URL chứa đường dẫn callback
//                    if (url.startsWith("http://10.0.2.2:8080/api/payment/vnpay_return")) {
//                        Uri uri = Uri.parse(url);
//                        String responseCode = uri.getQueryParameter("vnp_ResponseCode");
//
//                        if ("00".equals(responseCode)) {
//                            // Thanh toán thành công
//                            Intent intent = new Intent(WebViewActivity.this, PaymentInformation.class);
//                            intent.putExtra("paymentStatus", "success");
//                            startActivity(intent);
//                        } else {
//                            // Thanh toán thất bại
//                            Intent intent = new Intent(WebViewActivity.this, PaymentInformation.class);
//                            intent.putExtra("paymentStatus", "failed");
//                            intent.putExtra("errorCode", responseCode);
//                            startActivity(intent);
//                        }
//                        finish(); // Đóng WebViewActivity
//                        return true; // Dừng xử lý URL trong WebView
//                    }
//                    return false; // Tiếp tục xử lý URL trong WebView
//                }
@Override
public boolean shouldOverrideUrlLoading(WebView view, String url) {
    // Kiểm tra nếu URL phản hồi chứa "vnpay_return"
    if (url.contains("vnpay_return")) {
        Uri uri = Uri.parse(url);
        String responseCode = uri.getQueryParameter("vnp_ResponseCode");

        if ("00".equals(responseCode)) {
            // Thanh toán thành công
            Intent successIntent = new Intent(WebViewActivity.this, PaymentInformation.class);
            successIntent.putExtra("paymentStatus", "success");
            startActivity(successIntent);
            finish(); // Đóng WebViewActivity
        } else {
            // Thanh toán thất bại
            Intent failIntent = new Intent(WebViewActivity.this, PaymentInformation.class);
            failIntent.putExtra("paymentStatus", "fail");
            startActivity(failIntent);
            finish(); // Đóng WebViewActivity
        }
        return true; // Ngăn chặn tải thêm URL
    }
    return false;
}
            });
        }
    }
}
            //        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        // Cho phép người dùng quay lại trang trước trong WebView
//        WebView webView = findViewById(R.id.webView);
//        if (webView.canGoBack()) {
//            webView.goBack();
//        } else {
////            super.onBackPressed();
//            Intent returnIntent = new Intent(WebViewActivity.this, PaymentInformation.class);
//            returnIntent.putExtra("paymentStatus", "cancelled");
//            startActivity(returnIntent);
//            finish(); // Đóng WebViewActivity
//        }
//    }
//}
