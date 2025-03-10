package com.example.mobile.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile.R;
import com.example.mobile.adapter.ReviewAdapter;
import com.example.mobile.adapter.itemAdapter;
import com.example.mobile.api.ApiService;
import com.example.mobile.currentUser;
import com.example.mobile.model.cart;
import com.example.mobile.model.item;
import com.example.mobile.model.offlineProduct;
import com.example.mobile.offlineUser;
import com.example.mobile.popupController.RequestAddAddress;
import com.example.mobile.popupController.RequestLoginDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartManagement extends AppCompatActivity {
    private RecyclerView rcvItem;
    private Button btnAddProduct;
    private Button btnPayment;
    private TextView tvSum;
    private ImageView ivExit;
    private List<item> itemList;
    private cart cart;
    private itemAdapter itemAdapter;
    private float sum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_cart);
        initView();
        //If the user signed in customerPhone will contain the string value of phone number
        if(currentUser.currentCustomer != null) {
            itemList = new ArrayList<>();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rcvItem.setLayoutManager(linearLayoutManager);
            getListItems();
        }
        //else there is no string value and set an empty itemAdapter for rcvItem
        else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rcvItem.setLayoutManager(linearLayoutManager);
            List<item> offlineDataList = new ArrayList<>();
            for(offlineProduct product : offlineUser.selectedProductList) {
                String quantity = String.valueOf(product.getQuantity());
                String price = String.valueOf(product.getProduct().getProductPrice());
                item item = new item("", quantity, price, product.getProduct(), false);
                offlineDataList.add(item);
            }
            itemAdapter itemAdapter = new itemAdapter(offlineDataList, tvSum);
            rcvItem.setAdapter(itemAdapter);
        }
    }

    private void getListItems() {
        ApiService.apiService.getListItem(currentUser.currentCustomer.getCart().getCartID()).enqueue(new Callback<List<item>>() {
            @Override
            public void onResponse(Call<List<item>> call, Response<List<item>> response) {
                if(response.isSuccessful()) {
                    itemList = response.body();
                    System.out.println("itemList size: " + itemList.size());
                    if(itemList != null) {
                        sum=0;
                        for (item item : itemList){
                            sum += Double.parseDouble(item.getPrice());
                        }
                        DecimalFormat df = new DecimalFormat("#,###");
                        String formattedSum = df.format(sum);
                        tvSum.setText(String.valueOf(formattedSum) + " đ");
                        itemAdapter = new itemAdapter(itemList, tvSum);
                        rcvItem.setAdapter(itemAdapter);
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
                Toast.makeText(CartManagement.this, "Connection fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initView() {
        rcvItem = findViewById(R.id.rcvItem);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartManagement.this, CategoryMenu.class);
                startActivity(intent);
            }
        });
        btnPayment = findViewById(R.id.btnPayment);
        btnPayment.setOnClickListener(v -> {
            if(currentUser.currentCustomer != null) {
                if(itemAdapter.getItemCount()==0) {
                    Toast.makeText(this, "Chưa thêm sản phẩm nào", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(currentUser.currentCustomer.getAddress() == null) {
                        RequestAddAddress dialog = new RequestAddAddress(CartManagement.this);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                        dialog.setCancelable(false);
                        dialog.show();
                    }
                    else {
                        startActivity(new Intent(CartManagement.this, PaymentInformation.class));
                    }
                }
            }
            else {
                RequestLoginDialog dialog = new RequestLoginDialog(CartManagement.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                dialog.setCancelable(false);
                dialog.show();
            }
        });
        tvSum = findViewById(R.id.tvSum);
        ivExit = findViewById(R.id.ivExit);
        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartManagement.this, HomePage.class));
            }
        });
    }
}