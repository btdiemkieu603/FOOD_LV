package com.example.mobile.popupController;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.request.RequestCoordinator;
import com.example.mobile.R;
import com.example.mobile.controller.Login;

public class RequestLoginDialog extends Dialog {
    private Button btnLogin;
    private ImageView ivCancel;
    public RequestLoginDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_login_dialog);
        initView();
    }

    private void initView() {
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            dismiss();
            getContext().startActivity(new Intent(getContext(), Login.class));
        });
        ivCancel = findViewById(R.id.ivCancel);
        ivCancel.setOnClickListener(v -> {
            dismiss();
        });
    }
}
