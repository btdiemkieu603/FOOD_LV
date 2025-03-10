package com.example.mobile.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile.R;
import com.example.mobile.activities.RetrofitClient;
import com.example.mobile.activities.WebViewActivity;
import com.example.mobile.adapter.itemAdapter;
import com.example.mobile.adapter.paymentAdapter;
import com.example.mobile.api.ApiService;
import com.example.mobile.currentUser;
import com.example.mobile.model.PaymentResponse;
import com.example.mobile.model.VnPayResponse;
import com.example.mobile.model.item;
import com.example.mobile.model.order;
import com.example.mobile.popupController.ChangeAddressDialog;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//import vn.momo.momo_partner.AppMoMoLib;
//import vn.momo.momo_partner.MoMoParameterNameMap;
//import vn.momo.momo_partner.MoMoConstants;
//import org.json.JSONObject;

//import vn.momo.momo_partner.AppMoMoLib;
//import vn.momo.momo_partner.MoMoParameterNameMap;

//import com.momo.momosdk.MoMoSdk;
//import com.momo.momosdk.models.MoMoPaymentResult;
//import com.momo.momosdk.models.MoMoPaymentRequest;
//import com.momo.momosdk.callbacks.MoMoPaymentCallback;
//import java.util.HashMap;

public class PaymentInformation extends AppCompatActivity {

    private Button btnEdit, btnAddNew, btnOrder;
    private TextView tvFullName, tvPhone, tvAddress, tvSum, tvDiscount, tvTotal, tvNumDis;
    private RecyclerView rcvItem;
    private RadioGroup rdoPaymentType;

     private RadioButton rbtnCash,  rbtnAtm;
//    private RadioButton rbtnCash, rbtnMomo, rbtnAtm;
    private ImageView ivExit, ivLocation;
    private List<item> itemList;
    public static Activity paymentInformationActivity;

    private double sum, total, percent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_information);
        paymentInformationActivity = this;
        //AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION
        initView();
        loadUserInfo();
        itemList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvItem.setLayoutManager(linearLayoutManager);
        getListItems();

//        // Kiểm tra xem kết quả thanh toán VNPay có được trả về không
//        Uri data = getIntent().getData();
//        if (data != null && data.toString().startsWith("http://10.0.2.2:8080/api/payment/vnpay_return")) {
//            String vnpResponseCode = data.getQueryParameter("vnp_ResponseCode");
//            if ("00".equals(vnpResponseCode)) {
//                // Thanh toán thành công
//                Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
//                addOrder("3"); // Giả sử 3 là ID thanh toán qua VNPay
//                showPopupSuccess();
//            } else {
//                // Thanh toán thất bại
//                Toast.makeText(this, "Thanh toán thất bại! Mã phản hồi: " + vnpResponseCode, Toast.LENGTH_SHORT).show();
//            }
//        }

        // Xử lý trạng thái thanh toán từ WebViewActivity
        String paymentStatus = getIntent().getStringExtra("paymentStatus");
        if ("success".equals(paymentStatus)) {
            Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
            addOrder("2"); // Giả sử "3" là mã thanh toán VNPay
            showPopupSuccess();
        } else if ("fail".equals(paymentStatus)) {
            String errorCode = getIntent().getStringExtra("errorCode");
            Toast.makeText(this, "Thanh toán thất bại! Mã lỗi: " + errorCode, Toast.LENGTH_SHORT).show();
        }
    }


    private void getListItems() {
        ApiService.apiService.getListItem(currentUser.currentCustomer.getCart().getCartID()).enqueue(new Callback<List<item>>() {
            @Override
            public void onResponse(Call<List<item>> call, Response<List<item>> response) {
                if(response.isSuccessful()) {
                    itemList = response.body();
                    if(itemList != null) {
                        sum=0;
                        for (item item : itemList){
                            sum += Double.parseDouble(item.getPrice());
                        }
                        DecimalFormat df = new DecimalFormat("#,###");
                        String formattedSum = df.format(sum);
                        tvSum.setText(formattedSum + "đ");
                        double decreasedPrice = 0;
                        tvNumDis.setText("0đ");
                        if (currentUser.appliedDiscount != null) {
                            percent = currentUser.appliedDiscount.getPercent();
                            decreasedPrice = (percent)*sum;
                            String formattedDecreasedPrice = df.format(decreasedPrice);
                            tvNumDis.setText("- " + formattedDecreasedPrice + "đ");
                        }
                        total = sum + 10000 - decreasedPrice;
                        String formattedTotal = df.format(total);
                        tvTotal.setText(formattedTotal + "đ");

                        paymentAdapter paymentAdapter = new paymentAdapter(itemList, tvSum, tvNumDis, tvTotal);
                        rcvItem.setAdapter(paymentAdapter);
                    }
                    else {return;}
                }
                else {
                    System.out.println("Error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<item>> call, Throwable t) {
                System.out.println("Error: " + t);
            }
        });
    }

    private void loadUserInfo() {
        tvFullName.setText(currentUser.currentCustomer.getFirstName() + " " + currentUser.currentCustomer.getLastName());
        tvPhone.setText(currentUser.currentCustomer.getPhoneNumber());
        tvAddress.setText(currentUser.currentCustomer.getAddress().getAddressDetail());
    }

    private void showPopupSuccess() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popup = inflater.inflate(R.layout.popup_order_success, null);

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popup, width, height, focusable);
        LinearLayout layout = findViewById(R.id.payment);
        layout.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(layout, Gravity.CENTER,0, 0);
            }
        });
        Button btnBackHomepage = popup.findViewById(R.id.btnBackHomepage);
        btnBackHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                startActivity(new Intent(PaymentInformation.this, HomePage.class));
            }
        });
    }
    private void initView() {
        btnEdit = findViewById(R.id.btnEdit);
        btnAddNew = findViewById(R.id.btnAddNew);
        btnAddNew.setOnClickListener(v -> {
            startActivity(new Intent(PaymentInformation.this, CategoryMenu.class));
        });
        btnOrder = findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(v -> {
            String checkoutID = "";
            if (rbtnCash.isChecked()) {
                checkoutID = "1";
                addOrder(checkoutID);
                showPopupSuccess();
            }
//            if (rbtnMomo.isChecked()) {
//                checkoutID = "2";
//             //   requestPaymentWithMoMo();
//                requestPaymentWithVnPay();
//            }
            if (rbtnAtm.isChecked()) {
                checkoutID = "3";
                requestPaymentWithVnPay();
                //addOrder(checkoutID);
                //showPopupSuccess();
            }

        });
        tvFullName = findViewById(R.id.tvFullName);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);
        tvSum = findViewById(R.id.tvSum);
        tvDiscount = findViewById(R.id.tvDiscount);
        if (currentUser.appliedDiscount != null) {
            tvDiscount.setText(currentUser.appliedDiscount.getCode());
        }
        tvDiscount.setOnClickListener(v -> {
            startActivity(new Intent(PaymentInformation.this, DiscountManagement.class));
        });
        tvTotal = findViewById(R.id.tvTotal);
        tvNumDis = findViewById(R.id.tvNumDis);
        rcvItem = findViewById(R.id.rcvItem);
        rdoPaymentType = findViewById(R.id.rdoPaymentType);
        rbtnCash = findViewById(R.id.rbtnCash);
//        rbtnMomo = findViewById(R.id.rbtnMomo);
        rbtnAtm = findViewById(R.id.rbtnATM);
        ivExit = findViewById(R.id.ivExit);
        ivExit.setOnClickListener(v -> {
            startActivity(new Intent(PaymentInformation.this, CartManagement.class));
        });
        ivLocation = findViewById(R.id.ivLocation);
        ivLocation.setOnClickListener(v -> {
            ChangeAddressDialog dialog = new ChangeAddressDialog(PaymentInformation.this);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
            dialog.setCancelable(false);
            dialog.show();
        });
    }

//    private void requestPaymentWithVnPay() {
//        // Chuyển tổng số tiền thành String
//        String amount = String.valueOf((long) total);
//
//        // Tạo đối tượng ApiService từ Retrofit client
//        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
//
//        // Gọi API để tạo URL thanh toán
//        apiService.createVnPayPayment(Long.parseLong(amount)).enqueue(new Callback<PaymentResponse>() {
//            @Override
//            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    // Lấy URL thanh toán từ phản hồi JSON
//                    String paymentUrl = response.body().getPaymentUrl();
//
//                    // Kiểm tra xem URL thanh toán có hợp lệ không
//                    if (paymentUrl != null && !paymentUrl.isEmpty()) {
//                        // Mở URL trong trình duyệt web mặc định
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(paymentUrl));
//                        startActivity(browserIntent);
//                    } else {
//                        Toast.makeText(PaymentInformation.this, "URL thanh toán không hợp lệ", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    // Nếu phản hồi không thành công
//                    Log.e("VNPayResponse", "Lỗi tạo URL thanh toán: " + response.message());
//                    Toast.makeText(PaymentInformation.this, "Lỗi tạo URL thanh toán", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PaymentResponse> call, Throwable t) {
//                // Xử lý khi yêu cầu API thất bại
//                Log.e("VNPayError", "Yêu cầu thất bại: ", t);
//                Toast.makeText(PaymentInformation.this, "Yêu cầu thanh toán thất bại", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void requestPaymentWithVnPay() {
        // Chuyển tổng số tiền thành String
        String amount = String.valueOf((long) total);

        // Tạo đối tượng ApiService từ Retrofit client
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // Gọi API để tạo URL thanh toán
        apiService.createVnPayPayment(Long.parseLong(amount)).enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Lấy URL thanh toán từ phản hồi JSON
                    String paymentUrl = response.body().getPaymentUrl();

                    // Kiểm tra xem URL thanh toán có hợp lệ không
                    if (paymentUrl != null && !paymentUrl.isEmpty()) {
                        // Mở URL trong trình duyệt web mặc định
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(paymentUrl));
//                        browserIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                        startActivity(browserIntent);
                        Intent intent = new Intent(PaymentInformation.this, WebViewActivity.class);
                        intent.putExtra("paymentUrl", paymentUrl);
                        startActivity(intent);
                    } else {
                        Toast.makeText(PaymentInformation.this, "URL thanh toán không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("VNPayResponse", "Lỗi tạo URL thanh toán: " + response.message());
                    Toast.makeText(PaymentInformation.this, "Lỗi tạo URL thanh toán", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                Log.e("VNPayError", "Yêu cầu thất bại: ", t);
                Toast.makeText(PaymentInformation.this, "Yêu cầu thanh toán thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void addOrder(String checkoutID) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date date = new Date();
        String orderDate = formatter.format(date);
        order newOrder;
        if (currentUser.appliedDiscount != null) {
            newOrder = new order(orderDate, currentUser.appliedDiscount.getCode());
        }
        else {
            newOrder = new order(orderDate, "");
        }
        ApiService.apiService.addOrder(currentUser.currentCustomer.getId(), checkoutID, newOrder)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(PaymentInformation.this, "Add order success", Toast.LENGTH_SHORT).show();
                            // Reset mã giảm giá và cập nhật giao diện
                            currentUser.appliedDiscount = null;
//                            tvDiscount.setText("");
//                            tvNumDis.setText("0đ");

                        }
                        else {
                            System.out.print("Fail, error code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.print("Fail, throwable: " + t);
                    }
                });
    }

}