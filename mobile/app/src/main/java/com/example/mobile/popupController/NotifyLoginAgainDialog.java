package com.example.mobile.popupController;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.mobile.R;

public class NotifyLoginAgainDialog extends Dialog {
    private ImageView ivSkip;

    public NotifyLoginAgainDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify_login_again_dialog);
        ivSkip = findViewById(R.id.ivSkip);
        ivSkip.setOnClickListener(v -> {dismiss();});
    }
}
