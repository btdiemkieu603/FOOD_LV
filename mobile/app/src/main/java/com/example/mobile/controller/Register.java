package com.example.mobile.controller;

import static com.example.mobile.api.ApiService.apiService;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.mobile.R;
import com.example.mobile.api.ApiService;
import com.example.mobile.model.customer;
import com.example.mobile.popupController.WelcomeUserDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    private TextInputEditText edtPhone;
    private TextInputEditText edtFirstName;
    private TextInputEditText edtLastName;
    private TextInputEditText edtEmail;
    private TextInputEditText edtPass;
    private TextInputEditText edtConfirmPass;
    private Button btnRegister;
    private CheckBox checkBox;
    private TextView tvLinkToLogin;
    private LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInput()) {
                    createObject();
                }
            }
        });
    }

    private boolean checkInput() {
        String phone = edtPhone.getText().toString().trim();
        String firstName = edtFirstName.getText().toString().trim();
        String lastName = edtLastName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPass.getText().toString().trim();
        boolean isCorrect = true;

        return isCorrect;
    }

    private void createObject() {
        String phone = edtPhone.getText().toString().trim();
        String firstName = edtFirstName.getText().toString().trim();
        String lastName = edtLastName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = cryptoPassword(edtPass.getText().toString().trim());

        customer customer = new customer(phone, password, firstName, lastName, email);
        ApiService.apiService.createNewAccount(customer).enqueue(new Callback<com.example.mobile.model.customer>() {
            @Override
            public void onResponse(Call<com.example.mobile.model.customer> call, Response<com.example.mobile.model.customer> response) {
                customer newCustomer = response.body();
                if(newCustomer != null) {
                   showPopupWelcome(newCustomer.getFirstName().toString() +" "+newCustomer.getLastName().toString());
                }
            }

            @Override
            public void onFailure(Call<com.example.mobile.model.customer> call, Throwable t) {

            }
        });
    }

    public String cryptoPassword(String pass) {
        try {
            // Create a MessageDigest instance with MD5 algorithm
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Update the digest with the byte array of the string
            md.update(pass.getBytes());
            // Convert the byte array to a BigInteger
            BigInteger bi = new BigInteger(1, md.digest());
            // Convert the BigInteger to a hexadecimal string
            String hex = bi.toString(16);
            // Return the hash
            return hex;
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception
            e.printStackTrace();
            return null;
        }
    }

    private void showPopupWelcome(String username) {
        WelcomeUserDialog dialog = new WelcomeUserDialog(Register.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    public void initView() {
        layout = findViewById(R.id.registerLayout);
        edtPhone = findViewById(R.id.edtPhone);
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPassword);
        edtConfirmPass = findViewById(R.id.edtConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        checkBox = findViewById(R.id.checkBox);
        tvLinkToLogin = findViewById(R.id.tvLinkToLogin);
        tvLinkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }
}