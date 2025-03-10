package com.example.mobile.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile.R;
import com.example.mobile.adapter.adsAdapter;
import com.example.mobile.adapter.productAdapter;
import com.example.mobile.api.ApiService;
import com.example.mobile.api.ApiService500;
import com.example.mobile.currentUser;
import com.example.mobile.model.advertisement;
import com.example.mobile.model.itemUpdate;
import com.example.mobile.model.offlineProduct;
import com.example.mobile.model.product;
import com.example.mobile.offlineUser;
import com.example.mobile.popupController.ChangeAddressDialog;
import com.example.mobile.popupController.RequestLoginDialog;
import com.example.mobile.popupController.ThanksForRatingDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePage extends AppCompatActivity {
    private TextView tvUsername, tvSuggestedList;;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private RecyclerView rcvProduct;
    private List<product> productList;
//    private TextView
    private RecyclerView rcvSuggestedProducts;
    private List<product> suggestedProductList;
    private adsAdapter adapter;
    private CardView menuCard, orderCard, storeCard, discountCard;
    private ImageView cart, location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initView();
        //Receive username from login activity
        tvUsername.setText("Đăng nhập");
        tvUsername.setOnClickListener(v -> {
            startActivity(new Intent(HomePage.this, Login.class));
        });
        tvSuggestedList = findViewById(R.id.tvSuggestedList);
        // Khởi tạo RecyclerView cho sản phẩm gợi ý
        rcvSuggestedProducts = findViewById(R.id.rcvSuggestedProducts);
        LinearLayoutManager suggestedLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvSuggestedProducts.setLayoutManager(suggestedLayoutManager);

        if (currentUser.currentCustomer != null) {
            checkOfflineData();

            tvUsername.setText(currentUser.currentCustomer.getFirstName().toString() + " " + currentUser.currentCustomer.getLastName().toString());
            int userId = Integer.parseInt(currentUser.currentCustomer.getId());
            tvSuggestedList.setVisibility(View.VISIBLE);
            Log.d("UserID", "Current User ID: " + userId);
            tvUsername.setOnClickListener(v -> {
                startActivity(new Intent(HomePage.this, ProfileManagement.class));
            });
//             Gọi API gợi ý và hiển thị
//            getSuggestedProducts(currentUser.currentCustomer.getId());
            getSuggestedProducts(userId);
        }
        else {
            tvSuggestedList.setVisibility(View.GONE);

        }

        adapter = new adsAdapter(this, getListAds());
        viewPager.setAdapter(adapter);
        circleIndicator.setViewPager(viewPager);

        // Set up a TimerTask to automatically scroll every 3 seconds
        Timer timer = new Timer();
        final long DELAY_MS = 3000; // 3 seconds
        final long PERIOD_MS = 3000; // 3 seconds
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    int currentPage = viewPager.getCurrentItem();
                    int nextPage = (currentPage + 1) % 3;
                    viewPager.setCurrentItem(nextPage);
                });
            }
        }, DELAY_MS, PERIOD_MS);

        adapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        productList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvProduct.setLayoutManager(linearLayoutManager);
        getBestSellers();

//        // Setup RecyclerView for Suggested Products
//        LinearLayoutManager suggestedLayoutManager = new LinearLayoutManager(this);
//        suggestedLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        rcvSuggestedProducts.setLayoutManager(suggestedLayoutManager);
////        getSuggestedProducts();

        //Set event for menuCard button
        menuCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, CategoryMenu.class));
            }
        });
    }

    private void checkOfflineData() {

        if(offlineUser.selectedProductList != null) {

            for(offlineProduct product : offlineUser.selectedProductList) {
                ApiService.apiService.addItem(currentUser.currentCustomer.getCart().getCartID(), product.getProduct().getProductID(), new itemUpdate(product.getQuantity()))
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful()) {
                                    System.out.println("Added product " + product.getProduct().getProductName() + " from offline data to cart");
                                }
                                else {
                                    System.out.println("Homepage, error code: " + response.code());
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                System.out.println("Homepage, throwable: " + t);
                            }
                        });
            }
        }
    }

    private void getBestSellers() {
        ApiService.apiService.getBestSellers().enqueue(new Callback<List<product>>() {
            @Override
            public void onResponse(Call<List<product>> call, Response<List<product>> response) {
                if(response.isSuccessful()) {
                    productList = response.body();
                    Log.d("productList", "Product ID: " + productList);
                    productAdapter productAdapter = new productAdapter(productList);
                    rcvProduct.setAdapter(productAdapter);
                }
                else {
                    System.out.print("Error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<product>> call, Throwable t) {

            }
        });
    }
    // Hàm gọi API gợi ý sản phẩm

    private void getSuggestedProducts(int userId) {
        ApiService500.apiService.getRecommendedProducts(userId).enqueue(new Callback<List<product>>() {
            @Override
            public void onResponse(Call<List<product>> call, Response<List<product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    suggestedProductList = response.body();
                    if (suggestedProductList.isEmpty()) {
                        tvSuggestedList.setVisibility(View.GONE);
                    }
                    Log.d("SuggestedProduct", "Product ID: " + suggestedProductList);
                    productAdapter suggestedAdapter = new productAdapter(suggestedProductList);
                    rcvSuggestedProducts.setAdapter(suggestedAdapter); // Hiển thị sản phẩm gợi ý
                } else {
                    tvSuggestedList.setVisibility(View.GONE);
                    Log.e("HomePage", "Error fetching suggested products: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<product>> call, Throwable t) {
                tvSuggestedList.setVisibility(View.GONE);
                Log.e("HomePage", "Failed to fetch suggested products: " + t.getMessage());
            }
        });
    }

//    private void getSuggestedProducts() {
//        if (currentUser.currentCustomer != null) { // Kiểm tra xem người dùng hiện tại có đăng nhập không
//            String userId = currentUser.currentCustomer.getId(); // Lấy ID người dùng hiện tại
//
//            // Chuyển đổi userId từ Integer sang String nếu API yêu cầu String
//            String userIdString = userId.toString();
//
//            ApiService.apiService.getSuggestedProducts(userId).enqueue(new Callback<List<product>>() {
//                @Override
//                public void onResponse(Call<List<product>> call, Response<List<product>> response) {
//                    if (response.isSuccessful()) {
//                        suggestedProductList = response.body();
//                        productAdapter suggestedAdapter = new productAdapter(suggestedProductList);
//                        rcvSuggestedProducts.setAdapter(suggestedAdapter);
//                    } else {
//                        System.out.print("Error code: " + response.code());
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<product>> call, Throwable t) {
//                    System.out.print("Failed to fetch suggested products: " + t.getMessage());
//                }
//            });
//        } else {
//            System.out.print("User not logged in. Cannot fetch suggested products.");
//        }
//    }

    private List<advertisement> getListAds() {
        List<advertisement> list = new ArrayList<>();
        list.add(new advertisement(R.drawable.ad01));
        list.add(new advertisement(R.drawable.ad02));
        list.add(new advertisement(R.drawable.ad03));
        return list;
    }

    public void initView() {
        tvUsername = findViewById(R.id.tvUsername);
        viewPager = findViewById(R.id.viewPager);
        circleIndicator = findViewById(R.id.circle_indicator);
        menuCard = findViewById(R.id.menuCard);
        orderCard = findViewById(R.id.orderCard);
        orderCard.setOnClickListener(v -> {
            if(currentUser.currentCustomer != null){
                startActivity(new Intent(HomePage.this, OrderManagement.class));
            }
            else{
                RequestLoginDialog dialog = new RequestLoginDialog(HomePage.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                dialog.setCancelable(false);
                dialog.show();
            }
        });
        storeCard = findViewById(R.id.storeCard);
        storeCard.setOnClickListener(v -> {
            if(currentUser.currentCustomer != null){

            }
            else{
                RequestLoginDialog dialog = new RequestLoginDialog(HomePage.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                dialog.setCancelable(false);
                dialog.show();
            }
        });
        discountCard = findViewById(R.id.discountCard);
        discountCard.setOnClickListener(v -> {
            if(currentUser.currentCustomer != null) {
                startActivity(new Intent(HomePage.this, DiscountManagement.class));
            }
            else {
                RequestLoginDialog dialog = new RequestLoginDialog(HomePage.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                dialog.setCancelable(false);
                dialog.show();
            }
        });
        rcvProduct = findViewById(R.id.rcvProduct);
        cart = findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, CartManagement.class);
                startActivity(intent);
            }
        });
        location = findViewById(R.id.location);
        location.setOnClickListener(v -> {
            ChangeAddressDialog dialog = new ChangeAddressDialog(HomePage.this);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
            dialog.setCancelable(false);
            dialog.show();
        });
    }
}