package com.example.mobile.popupController;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.mobile.R;

public class RequestAddAddress extends Dialog {
    private Button btnClose, btnConfirm;
    public RequestAddAddress(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_add_address);
        btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(v -> {dismiss();});
        btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(v -> {
            dismiss();
            ChangeAddressDialog dialog = new ChangeAddressDialog(getContext());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(android.R.color.transparent)));
            dialog.setCancelable(false);
            dialog.show();
        });
    }
}
