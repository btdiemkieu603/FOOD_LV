package com.example.mobile.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mobile.R;
import com.google.android.material.textfield.TextInputEditText;

public class ForgetPassword extends AppCompatActivity {
    private ImageView ivBackToLogin;
    private TextInputEditText edtPhone;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    public void initView() {
        ivBackToLogin = findViewById(R.id.ivBackToLogin);
        ivBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgetPassword.this, Login.class));
            }
        });
        edtPhone = findViewById(R.id.edtPhone);
        btnSend = findViewById(R.id.btnSend);
    }
}