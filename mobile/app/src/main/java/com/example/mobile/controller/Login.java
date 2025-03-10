package com.example.mobile.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mobile.R;
import com.example.mobile.api.ApiService;
import com.example.mobile.currentUser;
import com.example.mobile.model.account;
import com.example.mobile.model.customer;
import com.example.mobile.popupController.NotifyLoginAgainDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private TextInputEditText edtPhoneLogin;
    private TextInputEditText edtPasswordLogin;
    private Button btnLogin;
    private TextView tvForgotPass;
    private TextView tvRegister;
    private List<customer> listCustomer;
    private RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Call initView function to init all views
        initView();

        //Init user list
        listCustomer = new ArrayList<>();

        //Call API customers to ready for check login
//        getListUser();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edtPhoneLogin.getText().toString().trim();
                String password = cryptoPassword(edtPasswordLogin.getText().toString().trim());
                //Check if there is no customer, do nothing

                //This means there is one or more customer in system
                boolean isCustomer = false;

                //Check phone number and password
                isCustomer = true;
                //This is correct customer account
                account account = new account(phone, password);
                getListUser(account);

                //Check if there is an account, send this customer to HomePage Activity

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

    private void createPopupWindow() {
        NotifyLoginAgainDialog dialog = new NotifyLoginAgainDialog(Login.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        dialog.setCancelable(false);
        dialog.show();
    }

    public void initView() {
        layout = findViewById(R.id.layout);
        edtPhoneLogin = findViewById(R.id.edtPhoneLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPass = findViewById(R.id.tvForgotPass);
        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ForgetPassword.class));
            }
        });
        tvRegister = findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }

    public void getListUser(account account) {
        System.out.println("Account: " + account.getPhonenumber() + " pass: " + account.getPassword());
        ApiService.apiService.login(account).enqueue(new Callback<customer>() {
            @Override
            public void onResponse(Call<customer> call, Response<customer> response) {
                if (response.isSuccessful()) {
                    currentUser.currentCustomer = response.body();
                    Intent intent = new Intent(Login.this, HomePage.class);
                    startActivity(intent);
                    //Clear everything
                    finish();
                }
                else {
                    createPopupWindow();
                }
            }

            @Override
            public void onFailure(Call<customer> call, Throwable t) {
                createPopupWindow();
            }
        });
    }
}