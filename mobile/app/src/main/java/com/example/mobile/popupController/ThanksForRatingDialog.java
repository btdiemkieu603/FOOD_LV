package com.example.mobile.popupController;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.example.mobile.R;
import com.example.mobile.controller.OrderDetail;
import com.example.mobile.controller.ReviewManagement;
import com.example.mobile.currentUser;

public class ThanksForRatingDialog extends Dialog {
    public ThanksForRatingDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thank_for_rating_dialog);

        final AppCompatButton btnClose;
        btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(v -> {
            dismiss();
            String orderID = String.valueOf(currentUser.order.getId());
            String orderStatusID = String.valueOf(currentUser.order.getOrderStatus().getId());
            Intent intent = new Intent(getContext(), OrderDetail.class);
            intent.putExtra("orderID", orderID);
            intent.putExtra("orderStatusID", orderStatusID);
            getContext().startActivity(intent);
        });
    }
}
