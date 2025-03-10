package com.example.mobile.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile.R;
import com.example.mobile.adapter.orderDetailAdapter;
import com.example.mobile.api.ApiService;
import com.example.mobile.currentUser;
import com.example.mobile.model.item;
import com.example.mobile.model.receivedOrder;
import com.example.mobile.popupController.ConfirmCancelOrderDialog;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetail extends AppCompatActivity {
    private int RECEIVED_STATUS = 4;
    private int WAIT_FOR_ACCEPT = 1;
    private TextView tvOrderCode, tvOrderTime, tvOrderTotal, tvSum, tvTotal, tvPayment, tvDiscount, tvAddress;
    private RecyclerView rcvItem;
    private Button btnReview;
    private ImageView ivExit;
    private List<item> itemList;
    private receivedOrder selectedOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        String orderID = getIntent().getStringExtra("orderID");
        String orderStatusID = getIntent().getStringExtra("orderStatusID");
        initView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvItem.setLayoutManager(linearLayoutManager);
        getListItem(Integer.parseInt(orderID), Integer.parseInt(orderStatusID));
    }

    private void getListItem(int orderID, int orderStatusID) {
        ApiService.apiService.getListOrder(Integer.parseInt(currentUser.currentCustomer.getId()), orderStatusID).enqueue(new Callback<List<receivedOrder>>() {
            @Override
            public void onResponse(Call<List<receivedOrder>> call, Response<List<receivedOrder>> response) {
                if (response.isSuccessful()) {
                    List<receivedOrder> orderList = response.body();
                    for (receivedOrder order : orderList) {
                        System.out.println("Received order: " + order.getId() + ", Selected order: " + orderID);
                        if (order.getId() == orderID) {
                            selectedOrder = order;
                        }
                    }
                    tvOrderCode.setText("Mã đơn: #" + selectedOrder.getId());
                    tvOrderTime.setText("Ngày mua hàng: " + selectedOrder.getOrderdate());
                    DecimalFormat df = new DecimalFormat("#,###");
                    String formattedTotal = df.format(Double.parseDouble(selectedOrder.getTotalprice()));
                    tvOrderTotal.setText("Tổng hóa đơn: " + formattedTotal + "đ");
                    float priceTotal=0;
                    for(item item : selectedOrder.getItems()) {
                        priceTotal += Float.parseFloat(item.getPrice());
                    }
                    String formattedSum = df.format(priceTotal);
                    tvSum.setText(formattedSum + "đ");
                    double totalBill = Double.parseDouble(selectedOrder.getTotalprice()) + 10000;
                    String formattedTotalBill = df.format(totalBill);
                    tvTotal.setText(formattedTotalBill + "đ");
                    float amountOfDiscount = priceTotal - Float.parseFloat(selectedOrder.getTotalprice());
                    String formattedDis = df.format(amountOfDiscount);
                    tvDiscount.setText(formattedDis + "đ");
                    int paymentID = selectedOrder.getCheckout().getId();
                    switch(paymentID) {
                        case 1:
                            tvPayment.setText("Thanh toán khi nhận hàng");
                            break;  // Thêm break ở đây
                        case 3:
                            tvPayment.setText("Ví Momo");
                            break;  // Thêm break ở đây
                        case 2:
                            tvPayment.setText("Ví ngân hàng");
                            break;  // Thêm break ở đây
                        default:
                            tvPayment.setText("");  // Nếu không có trường hợp nào khớp, sẽ để trống
                            break;  // Thêm break ở đây
                    }

                    tvAddress.setText("Giao đến: " + selectedOrder.getAccount().getAddress().getAddressDetail());
                    orderDetailAdapter orderDetailAdapter = new orderDetailAdapter(selectedOrder.getItems());
                    rcvItem.setAdapter(orderDetailAdapter);
                    currentUser.order = selectedOrder;

                    if (currentUser.order.getOrderStatus().getId() == RECEIVED_STATUS) {
                        btnReview.setVisibility(View.VISIBLE);
                        if (checkReview()) {
                            btnReview.setOnClickListener(v -> {
                                Intent intent = new Intent(OrderDetail.this, ReviewManagement.class);
                                startActivity(intent);
                            });
                        }
                        else {
                            btnReview.setText("Đã đánh giá");
                            btnReview.setEnabled(false);
                        }
                    }
                    if (currentUser.order.getOrderStatus().getId() == WAIT_FOR_ACCEPT) {
                        btnReview.setVisibility(View.VISIBLE);
                        btnReview.setText("Hủy đơn");
                        btnReview.setOnClickListener(v -> {
                            ConfirmCancelOrderDialog dialog = new ConfirmCancelOrderDialog(OrderDetail.this);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                            dialog.setCancelable(false);
                            dialog.show();
                        });
                    }
                }
                else {
                    System.out.print("Error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<receivedOrder>> call, Throwable t) {
                System.out.print("Throwable: " + t);
            }
        });
    }

    private boolean checkReview() {
        //check if there is any review which have not been reviewed
        for (item item : selectedOrder.getItems()) {
            if (!item.isReviewed()) {
                return true;
            }
        }
        return false;
    }

    private void initView() {
        tvOrderCode = findViewById(R.id.tvOrderCode);
        tvOrderTime = findViewById(R.id.tvOrderTime);
        tvOrderTotal = findViewById(R.id.tvOrderTotal);
        tvSum = findViewById(R.id.tvSum);
        tvTotal = findViewById(R.id.tvTotalBill);
        tvDiscount = findViewById(R.id.tvDiscount);
        tvPayment = findViewById(R.id.tvPayment);
        tvAddress = findViewById(R.id.tvAddress);
        btnReview = findViewById(R.id.btnReview);
        btnReview.setVisibility(View.GONE);
        ivExit = findViewById(R.id.ivExit);
        ivExit.setOnClickListener(v -> {
            startActivity(new Intent(OrderDetail.this, OrderManagement.class));
        });
        rcvItem = findViewById(R.id.rcvItem);
    }

}