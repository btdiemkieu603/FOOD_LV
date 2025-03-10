package com.example.mobile.api;

import com.example.mobile.model.PaymentResponse;
import com.example.mobile.model.VnPayResponse;
import com.example.mobile.model.account;
import com.example.mobile.model.cart;
import com.example.mobile.model.category;
import com.example.mobile.model.customer;
import com.example.mobile.model.discount;
import com.example.mobile.model.district;
import com.example.mobile.model.item;
import com.example.mobile.model.itemUpdate;
import com.example.mobile.model.order;
import com.example.mobile.model.postReview;
import com.example.mobile.model.product;
import com.example.mobile.model.province;
import com.example.mobile.model.receivedOrder;
import com.example.mobile.model.review;
import com.example.mobile.model.updatedAddress;
import com.example.mobile.model.ward;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {
  // public static final String BASE_URL = "http://192.168.56.1:8080/api/";
 public static final String BASE_URL = "http://10.0.2.2:8080/api/";
   //public static final String BASE_URL = "https://b315-113-164-236-59.ngrok-free.app/api/";
//   public static final String BASE_URL_500 = "http://10.0.2.2:500/api/";
 public static final String ADDRESS_URL = "https://vietnam-administrative-division-json-server-swart.vercel.app/";
    ApiService apiService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);
// ApiService apiService500 = new Retrofit.Builder()
//         .baseUrl(BASE_URL_500)
//         .addConverterFactory(GsonConverterFactory.create())
//         .build()
//         .create(ApiService.class);
    ApiService apiAddress = new Retrofit.Builder()
            .baseUrl(ADDRESS_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);
//    @FormUrlEncoded
// @GET("payment/pay")
// Call<String> createVnPayPayment(
//
//         @Query("amount") long amount
//        );

//   @GET
//   Call<Map<String, String>> createVnPayPayment(@Url String url);


      @GET("api/payment/pay")
      Call<PaymentResponse> createVnPayPayment(@Query("amount") long amount);


   @GET("product/category/{idCate}")
    Call<List<product>> getListProduct(@Path("idCate") int idCate);
    @GET("category/")
    Call<List<category>> getListCategory();
    @GET("review/product/{idProduct}")
    Call<List<review>> getListReview(@Path("idProduct") int idProduct);
    @GET("product/{id}")
    Call<product> getProductById(@Path("id") int id);
    @GET("cart/current/{idCart}")
    Call<List<item>> getListItem(@Path("idCart") String cartID);
    @GET("order/toporder")
    Call<List<product>> getBestSellers();
    @GET("discount/current")
    Call<List<discount>> getListDiscount();
    @GET("order/{accountID}/{orderStatusId}?sort=desc")
    Call<List<receivedOrder>> getListOrder(@Path("accountID") int accountID, @Path("orderStatusId") int orderStatusId);
    @GET("order/{orderID}")
    Call<List<receivedOrder>> getOrder(@Path("orderID") int orderID);
    @GET("province")
    Call<List<province>> getProvinces();
    @GET("district")
    Call<List<district>> getDistrict(@Query("idProvince") String provinceCode);
    @GET("commune")
    Call<List<ward>> getWards(@Query("idDistrict") String districtCode);

    @POST("login/")
    Call<customer> login(@Body account account);
    @POST("account/addCustomer/1")
    Call<customer> createNewAccount(@Body customer customer);
    @POST("item/add/{cartID}/{productID}")
    Call<Void> addItem(@Path("cartID") String cardID, @Path("productID") String productID, @Body itemUpdate item);
    @POST("order/addDiscount/{accountID}/{checkoutID}")
    Call<Void> addOrder(@Path("accountID") String accountID, @Path("checkoutID") String checkoutID, @Body order order);
    @POST("review/add/{accountId}/{itemId}")
    Call<Void> addReview(@Path("accountId") String accountId, @Path("itemId") String itemId, @Body postReview review);

    @PUT("item/{id}")
    Call<Void> updateCartItemQuantity(@Path("id") String id, @Body itemUpdate item);
    @PUT("account/{id}")
    Call<Void> updateProfile(@Path("id") String id, @Body customer customer);
    @PUT("account/address/{accountID}")
    Call<Void> updateAddress(@Path("accountID") String accountID, @Body updatedAddress address);
    @PUT("order/{orderID}/{orderStatusID}")
    Call<Void> updateOrderStatus(@Path("orderID") int orderID, @Path("orderStatusID") int orderStatusID);
    @DELETE("item/{id}")
    Call<Void> deleteItemFromCart(@Path("id") String id);

//    @GET("recommendation/{userId}")
//    Call<List<product>> getRecommendedProducts(@Path("userId") int userId);
}
