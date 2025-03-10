package com.example.mobile.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile.R;
import com.example.mobile.adapter.orderAdapter;
import com.example.mobile.api.ApiService;
import com.example.mobile.currentUser;
import com.example.mobile.model.order;
import com.example.mobile.model.receivedOrder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderManagement extends AppCompatActivity {

    private TextView tvWaitForAccept, tvWaitForDelivery, tvDelivering, tvReceived, tvCanceled;
    private ImageView ivExit;
    private RecyclerView rcvOrder;
    private List<receivedOrder> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);

        initView();
        orderList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvOrder.setLayoutManager(linearLayoutManager);
        getListOrder(1);
    }

    private void getListOrder(int orderStatusId) {
        ApiService.apiService.getListOrder(Integer.parseInt(currentUser.currentCustomer.getId()), orderStatusId).enqueue(new Callback<List<receivedOrder>>() {
            @Override
            public void onResponse(Call<List<receivedOrder>> call, Response<List<receivedOrder>> response) {
                if (response.isSuccessful()) {
                    orderList = response.body();
                    orderAdapter orderAdapter = new orderAdapter(null);
                    if(orderList != null) {
                        orderAdapter = new orderAdapter(orderList);
                    }
                    rcvOrder.setAdapter(orderAdapter);
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
    private void initView(){
        tvWaitForAccept = findViewById(R.id.tvWaitForAccept);
        tvWaitForAccept.setOnClickListener(v -> {
            getListOrder(1);
            tvWaitForAccept.setTextColor(ContextCompat.getColor(this, R.color.red));
            tvWaitForDelivery.setTextColor(ContextCompat.getColor(this, R.color.black));
            tvDelivering.setTextColor(ContextCompat.getColor(this, R.color.black));
            tvReceived.setTextColor(ContextCompat.getColor(this, R.color.black));
            tvCanceled.setTextColor(ContextCompat.getColor(this, R.color.black));
        });
        tvWaitForDelivery = findViewById(R.id.tvWaitForDelivery);
        tvWaitForDelivery.setOnClickListener(v -> {
            getListOrder(2);
            tvWaitForAccept.setTextColor(ContextCompat.getColor(this, R.color.black));
            tvWaitForDelivery.setTextColor(ContextCompat.getColor(this, R.color.red));
            tvDelivering.setTextColor(ContextCompat.getColor(this, R.color.black));
            tvReceived.setTextColor(ContextCompat.getColor(this, R.color.black));
            tvCanceled.setTextColor(ContextCompat.getColor(this, R.color.black));
        });
        tvDelivering = findViewById(R.id.tvDelivering);
        tvDelivering.setOnClickListener(v -> {
            getListOrder(3);
            tvWaitForAccept.setTextColor(ContextCompat.getColor(this, R.color.black));
            tvWaitForDelivery.setTextColor(ContextCompat.getColor(this, R.color.black));
            tvDelivering.setTextColor(ContextCompat.getColor(this, R.color.red));
            tvReceived.setTextColor(ContextCompat.getColor(this, R.color.black));
            tvCanceled.setTextColor(ContextCompat.getColor(this, R.color.black));
        });
        tvReceived = findViewById(R.id.tvReceived);
        tvReceived.setOnClickListener(v -> {
            getListOrder(4);
            tvWaitForAccept.setTextColor(ContextCompat.getColor(this, R.color.black));
            tvWaitForDelivery.setTextColor(ContextCompat.getColor(this, R.color.black));
            tvDelivering.setTextColor(ContextCompat.getColor(this, R.color.black));
            tvReceived.setTextColor(ContextCompat.getColor(this, R.color.red));
            tvCanceled.setTextColor(ContextCompat.getColor(this, R.color.black));
        });
        tvCanceled = findViewById(R.id.tvCanceled);
        tvCanceled.setOnClickListener(v -> {
            getListOrder(5);
            tvWaitForAccept.setTextColor(ContextCompat.getColor(this, R.color.black));
            tvWaitForDelivery.setTextColor(ContextCompat.getColor(this, R.color.black));
            tvDelivering.setTextColor(ContextCompat.getColor(this, R.color.black));
            tvReceived.setTextColor(ContextCompat.getColor(this, R.color.black));
            tvCanceled.setTextColor(ContextCompat.getColor(this, R.color.red));
        });
        ivExit = findViewById(R.id.ivExit);
        ivExit.setOnClickListener(v -> {
            startActivity(new Intent(OrderManagement.this, HomePage.class));
        });
        rcvOrder = findViewById(R.id.rcvOrder);
    }
}