package com.example.mobile.popupController;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mobile.R;

public class SuccessDialog extends Dialog {
    private TextView textContainer;
    private String content;
    public SuccessDialog(@NonNull Context context, String content) {
        super(context);
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_dialog);
        textContainer = findViewById(R.id.textContainer);
        textContainer.setText(content);
    }
}
