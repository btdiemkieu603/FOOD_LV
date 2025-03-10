package com.example.mobile.popupController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.mobile.R;
import com.example.mobile.api.ApiService;
import com.example.mobile.currentUser;
import com.example.mobile.model.account;
import com.example.mobile.model.address;
import com.example.mobile.model.customer;
import com.example.mobile.model.district;
import com.example.mobile.model.province;
import com.example.mobile.model.updatedAddress;
import com.example.mobile.model.ward;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class ChangeAddressDialog extends Dialog {

    private Retrofit retrofit;
    private AutoCompleteTextView tvProvince;
    private AutoCompleteTextView tvDistrict;
    private AutoCompleteTextView tvWard;
    private Button btnConfirm, btnClose;
    private TextInputEditText tvDetail;
    private ArrayAdapter<String> adapterProvinceItems, adapterDistrictItems, adapterWardItems;

    public ChangeAddressDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_address_dialog);
        initView();
        currentUser.provinceList = new ArrayList<>();
        currentUser.districtList = new ArrayList<>();
        currentUser.wardList = new ArrayList<>();
        setUpUserAddress();
        callApiProvince();
    }

    private void setUpUserAddress() {
        if(currentUser.currentCustomer.getAddress() != null) {
            address address = currentUser.currentCustomer.getAddress();
            tvProvince.setText(address.getProvince());
            tvDistrict.setText(address.getDistrict());
            tvWard.setText(address.getWard());
            tvDetail.setText(address.getStreet());
        }
        else {
            tvProvince.setText("Vui lòng chọn tỉnh");
            tvDistrict.setText("Vui lòng chọn huyện");
            tvWard.setText("Vui lòng chọn xã");
        }

    }

    private void callApiDistrict(province province) {
        System.out.println("Selected province: " + province.getName() + " id: " + province.getIdProvince());
        ApiService.apiAddress.getDistrict(province.getIdProvince()).enqueue(new Callback<List<district>>() {
            @Override
            public void onResponse(Call<List<district>> call, Response<List<district>> response) {
                if(response.isSuccessful()) {
                    currentUser.districtList = response.body();
                    List<String> districtName = new ArrayList<>();
                    //districtName.add("Vui lòng chọn huyện");
                    for(district district : currentUser.districtList) {
                        districtName.add(district.getName());
                    }
                    adapterDistrictItems = new ArrayAdapter<String>(getContext(), R.layout.dropdown_list_item, districtName);
                    tvDistrict.setAdapter(adapterDistrictItems);
                    tvDistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String district = adapterView.getItemAtPosition(i).toString();
                            System.out.println("Selected district: " + district);
                            for(district districtItem : currentUser.districtList) {
                                if(districtItem.getName().compareTo(district)==0) {
                                    callApiWard(districtItem);
                                }
                            }
                            tvWard.setText("Vui lòng chọn xã");
                        }
                    });
                }
                else {
                    System.out.println("callApiDistrict,error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<district>> call, Throwable t) {
                System.out.println("callApiDistrict, throwable: " + t);
            }
        });
    }

    private void callApiWard(district district) {
        ApiService.apiAddress.getWards(district.getIdDistrict()).enqueue(new Callback<List<ward>>() {
            @Override
            public void onResponse(Call<List<ward>> call, Response<List<ward>> response) {
                if(response.isSuccessful()){
                    currentUser.wardList = response.body();
                    System.out.println("ward size:" + currentUser.wardList.size());
                    List<String> wardName = new ArrayList<>();
                    //wardName.add("Vui lòng chọn phường");
                    for(ward wardItem : currentUser.wardList) {
                        wardName.add(wardItem.getName());
                    }
                    adapterWardItems = new ArrayAdapter<String>(getContext(), R.layout.dropdown_list_item, wardName);
                    tvWard.setAdapter(adapterWardItems);
                    tvWard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String ward = adapterView.getItemAtPosition(i).toString();
                            System.out.println("Selected ward: " + ward);
                        }
                    });
                }
                else {
                    System.out.println("CallApiWard, error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ward>> call, Throwable t) {
                System.out.println("CallApiWard, throwable:" + t);
            }
        });
    }

    private void callApiProvince() {
        ApiService.apiAddress.getProvinces().enqueue(new Callback<List<province>>() {
            @Override
            public void onResponse(Call<List<province>> call, Response<List<province>> response) {
                if (response.isSuccessful()) {
                    currentUser.provinceList = response.body();
                    System.out.println("Province size:" + currentUser.provinceList.size());
                    List<String> provinceName = new ArrayList<>();
                    //provinceName.add("Vui lòng chọn tỉnh");
                    for(province province : currentUser.provinceList) {
                        provinceName.add(province.getName());
                    }
                    adapterProvinceItems = new ArrayAdapter<String>(getContext(), R.layout.dropdown_list_item, provinceName);
                    tvProvince.setAdapter(adapterProvinceItems);
                    tvProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String province = adapterView.getItemAtPosition(i).toString();
                            System.out.println("Selected province: " + province);
                            for(province provinceItem : currentUser.provinceList) {
                                if(provinceItem.getName().compareTo(province)==0) {
                                    callApiDistrict(provinceItem);
                                }
                            }
                            tvDistrict.setText("Vui lòng chọn huyện");
                            tvWard.setText("Vui lòng chọn xã");
                        }
                    });
                }
                else {
                    System.out.println("Error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<province>> call, Throwable t) {
                System.out.println("callApiProvince, throwable: " + t);
            }
        });
    }
    private void initView() {
        tvProvince = findViewById(R.id.tvProvince);
        tvDistrict = findViewById(R.id.tvDistrict);
        tvWard = findViewById(R.id.tvWard);
        tvDetail = findViewById(R.id.tvDetail);
        btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(v -> {
            dismiss();
        });
        btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(v -> {
            String province = tvProvince.getText().toString();
            String district = tvDistrict.getText().toString();
            String ward = tvWard.getText().toString();
            String detail = tvDetail.getText().toString();
            updatedAddress address = new updatedAddress(detail, ward, district, province);
            ApiService.apiService.updateAddress(currentUser.currentCustomer.getId(), address).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()) {
                        Toast.makeText(getContext(), "Update address successful", Toast.LENGTH_SHORT).show();
                        ApiService.apiService.login(new account(currentUser.currentCustomer.getPhoneNumber(), currentUser.currentCustomer.getPassword())).enqueue(new Callback<customer>() {
                            @Override
                            public void onResponse(Call<customer> call, Response<customer> response) {
                                currentUser.currentCustomer = response.body();
                            }

                            @Override
                            public void onFailure(Call<customer> call, Throwable t) {

                            }
                        });
                    }
                    else {
                        System.out.println("ChangeAddressDialog error code: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    System.out.print("ChangAddressDialog throwable: " + t);
                }
            });
            dismiss();
        });
    }

}