package com.example.mobile.popupController;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mobile.R;
import com.example.mobile.api.ApiService;
import com.example.mobile.controller.OrderManagement;
import com.example.mobile.currentUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmCancelOrderDialog extends Dialog {

    private Button btnClose, btnConfirm;
    public ConfirmCancelOrderDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_cancel_order_dialog);
        initView();
    }

    private void initView() {
        btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(v -> {
            dismiss();
        });
        btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(v -> {
            ApiService.apiService.updateOrderStatus(currentUser.order.getId(), 5).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()) {
                        dismiss();
                        SuccessDialog dialog = new SuccessDialog(getContext(), "Hủy đơn hàng thành công");
                        dialog.setContentView(R.layout.success_dialog);

                        Window window = dialog.getWindow();
                        if (window != null) {
                            WindowManager.LayoutParams layoutParams = window.getAttributes();
                            layoutParams.gravity = Gravity.CENTER; // Set the gravity to TOP
                            layoutParams.y = 100; // Adjust the vertical position (optional)
                            window.setAttributes(layoutParams);
                        }
                        dialog.show();

                        new Handler().postDelayed(() -> {
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                                getContext().startActivity(new Intent(getContext(), OrderManagement.class));
                            }
                        }, 1000);
                    }
                    else {
                        System.out.println("Errorcode: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getContext(), "Hủy đơn thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }


}
