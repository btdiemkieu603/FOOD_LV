package com.example.mobile.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.mobile.R;
import com.example.mobile.adapter.productAdapter;
import com.example.mobile.api.ApiService;
import com.example.mobile.currentUser;
import com.example.mobile.model.product;
import com.example.mobile.popupController.ChangeAddressDialog;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductMenu extends AppCompatActivity {
    private ImageView ivExit, ivLocation, ivCart;
    private RecyclerView rcvProduct;
    private String selectedCategoryID;
    private List<product> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_menu);

        selectedCategoryID = getIntent().getStringExtra("category_id");
        if (selectedCategoryID == null) {
            Toast.makeText(this, "get Cate string fail", Toast.LENGTH_SHORT).show();
        }
        initView();

        productList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvProduct.setLayoutManager(linearLayoutManager);
        getListProduct();
    }
    public void initView() {
        ivExit = findViewById(R.id.ivExit);
        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductMenu.this, CategoryMenu.class));
            }
        });
        ivLocation = findViewById(R.id.ivLocation);
        ivLocation.setOnClickListener(v -> {
            ChangeAddressDialog dialog = new ChangeAddressDialog(ProductMenu.this);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
            dialog.setCancelable(false);
            dialog.show();
        });
        ivCart = findViewById(R.id.ivCart);
        ivCart.setOnClickListener(v -> {
            startActivity(new Intent(ProductMenu.this, CartManagement.class));
        });
        rcvProduct = findViewById(R.id.rcvCategory);
    }

    private void getListProduct() {
        ApiService.apiService.getListProduct(Integer.parseInt(selectedCategoryID)).enqueue(new Callback<List<product>>() {
            @Override
            public void onResponse(Call<List<product>> call, Response<List<product>> response) {
                productList = response.body();
                Toast.makeText(ProductMenu.this, "Size:"+productList.size(), Toast.LENGTH_SHORT).show();
                productAdapter productAdapter = new productAdapter(productList);
                rcvProduct.setAdapter(productAdapter);
            }

            @Override
            public void onFailure(Call<List<product>> call, Throwable t) {
                Toast.makeText(ProductMenu.this, "Call API Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}