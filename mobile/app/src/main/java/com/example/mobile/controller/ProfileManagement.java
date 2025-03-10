package com.example.mobile.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mobile.R;
import com.example.mobile.api.ApiService;
import com.example.mobile.currentUser;
import com.example.mobile.model.customer;
import com.example.mobile.popupController.SuccessDialog;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileManagement extends AppCompatActivity {

    private TextInputEditText tvFirstName, tvLastName, tvPhoneNumber, tvEmail, tvPass;
    private ImageView ivExit;
    private Button btnUpdate, btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);
        initView();
    }

    private void initView() {
        tvFirstName = findViewById(R.id.tvFirstName);
        tvFirstName.setText(currentUser.currentCustomer.getFirstName());
        tvLastName = findViewById(R.id.tvLastName);
        tvLastName.setText(currentUser.currentCustomer.getLastName());
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvPhoneNumber.setText(currentUser.currentCustomer.getPhoneNumber());
        tvEmail = findViewById(R.id.tvEmail);
        tvEmail.setText(currentUser.currentCustomer.getEmail());
        tvPass = findViewById(R.id.tvPass);
        tvPass.setText(currentUser.currentCustomer.getPassword());
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            currentUser.currentCustomer = null;
            startActivity(new Intent(ProfileManagement.this, Login.class));
        });
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(v -> {
            updateProfile();
        });
        ivExit = findViewById(R.id.ivExit);
        ivExit.setOnClickListener(v -> {
            finish();
        });
    }

    private void updateProfile() {
        System.out.println("Call updateProfile method");
        String firstName = tvFirstName.getText().toString();
        String lastName = tvLastName.getText().toString();
        String phoneNumber = tvPhoneNumber.getText().toString();
        String email = tvEmail.getText().toString();
        String pass = tvPass.getText().toString();
        customer customer = currentUser.currentCustomer;
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setPhoneNumber(phoneNumber);
        customer.setEmail(email);
        customer.setPassword(pass);
        ApiService.apiService.updateProfile(currentUser.currentCustomer.getId(), customer)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()) {
                            SuccessDialog dialog = new SuccessDialog(ProfileManagement.this, "Cập nhật thành công");
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                            dialog.setCancelable(false);
                            dialog.show();
                            new Handler().postDelayed(() -> {
                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                            }, 1000);
                        }
                        else {
                            System.out.println("Update profile fail, Error code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.print("Update profile fail, Throwable: " + t);
                    }
                });
    }
}