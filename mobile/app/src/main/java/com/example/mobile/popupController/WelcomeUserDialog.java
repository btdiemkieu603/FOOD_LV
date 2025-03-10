package com.example.mobile.popupController;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.mobile.R;
import com.example.mobile.controller.Login;

public class WelcomeUserDialog extends Dialog {
    private Button btnLoginNow;
    public WelcomeUserDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_user_dialog);

        initView();
    }

    private void initView() {
        btnLoginNow = findViewById(R.id.btnLoginNow);
        btnLoginNow.setOnClickListener(v -> {
            getContext().startActivity(new Intent(getContext(), Login.class));
        });
    }
}
