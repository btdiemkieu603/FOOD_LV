package com.example.mobile.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mobile.api.ApiService;
import com.example.mobile.controller.PaymentInformation;
import com.example.mobile.currentUser;
import com.example.mobile.model.item;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class paymentAdapter extends itemAdapter{
    private TextView tvNumDis, tvTotal;
    public paymentAdapter(List<item> itemList, TextView tvSum, TextView tvNumDis, TextView tvTotal) {
        super(itemList, tvSum);
        this.tvNumDis = tvNumDis;
        this.tvTotal = tvTotal;
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiService.apiService.deleteItemFromCart(holder.item.getId())
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(view.getContext(), "Delete item success", Toast.LENGTH_SHORT).show();
                                recalculateSum();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(view.getContext(), "Delete item fail", Toast.LENGTH_SHORT).show();
                            }
                        });

                itemList.remove(holder.getAdapterPosition());
                if(itemList.size()==0) {
                    PaymentInformation.paymentInformationActivity.finish();
                }
                // Notify the adapter about the removal
                notifyItemRemoved(holder.getAdapterPosition());
                // Update other items' positions
                notifyItemRangeChanged(holder.getAdapterPosition(), itemList.size());
            }
        });
    }

    @Override
    protected void recalculateSum() {
        ApiService.apiService.getListItem(currentUser.currentCustomer.getCart().getCartID()).enqueue(new Callback<List<item>>() {
            @Override
            public void onResponse(Call<List<item>> call, Response<List<item>> response) {
                List<item> items = response.body();
                float sum = 0;
                for (item item : items) {
                    sum += Double.parseDouble(item.getPrice());
                }
                DecimalFormat df = new DecimalFormat("#,###");
                String formattedSum = df.format(sum);
                tvSum.setText(String.valueOf(formattedSum) + "đ");
                double decreasedPrice = 0;
                tvNumDis.setText("0đ");
                if (currentUser.appliedDiscount != null) {
                    double percent = currentUser.appliedDiscount.getPercent();
                    decreasedPrice = (percent/100)*sum;
                    String formattedDecreasedPrice = df.format(decreasedPrice);
                    tvNumDis.setText("- " + formattedDecreasedPrice + "đ");
                }
                double total = sum + 10000 - decreasedPrice;
                String formattedTotal = df.format(total);
                tvTotal.setText(formattedTotal + "đ");
            }

            @Override
            public void onFailure(Call<List<item>> call, Throwable t) {
                System.out.println("Recalculate sum error: " + t);
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == MoMoConstants.REQUEST_CODE_MOMO && resultCode == RESULT_OK) {
//            if (data != null) {
//                String status = data.getStringExtra("status");
//                String message = data.getStringExtra("message");
//                if ("0".equals(status)) {
//                    // Thanh toán thành công
//                    addOrder("2");
//                    showPopupSuccess();
//                } else {
//                    // Thanh toán thất bại
//                    Toast.makeText(this, "Thanh toán thất bại: " + message, Toast.LENGTH_LONG).show();
//                }
//            } else {
//                Toast.makeText(this, "Không nhận được kết quả thanh toán từ MoMo", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}
