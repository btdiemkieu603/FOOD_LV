package com.example.mobile.api;

import com.example.mobile.model.product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService500 {
 // Base URL cho cổng 500
 public static final String BASE_URL = "http://10.0.2.2:5001/api/";
 public static final String ADDRESS_URL = "https://vietnam-administrative-division-json-server-swart.vercel.app/";
 // Instance của Retrofit dành cho API cổng 500
 ApiService500 apiService = new Retrofit.Builder()
         .baseUrl(BASE_URL)
         .addConverterFactory(GsonConverterFactory.create())
         .build()
         .create(ApiService500.class);

 // Định nghĩa các API dùng cổng 500
 @GET("recommendation/{userId}")
 Call<List<product>> getRecommendedProducts(@Path("userId") int userId);
}
