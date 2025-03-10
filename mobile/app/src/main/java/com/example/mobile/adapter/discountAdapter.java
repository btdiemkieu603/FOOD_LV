package com.example.mobile.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile.R;
import com.example.mobile.api.ApiService;
import com.example.mobile.controller.PaymentInformation;
import com.example.mobile.currentUser;
import com.example.mobile.model.discount;
import com.squareup.picasso.Picasso;

import java.util.List;

public class discountAdapter extends RecyclerView.Adapter<discountAdapter.discountViewHolder> {
    private List<discount> discountList;

    public discountAdapter(List<discount> discountList) {
        this.discountList = discountList;
    }

    @NonNull
    @Override
    public discountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discount_item, parent, false);
        return new discountAdapter.discountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull discountViewHolder holder, int position) {
        discount discount = discountList.get(position);
        holder.tvCode.setText(discount.getCode());
        holder.tvDate.setText(discount.getStartdate() + " - " + discount.getEnddate());
        holder.tvPercent.setText("Giảm " + discount.getPercent()*100 + "% tổng hóa đơn");
    }

    @Override
    public int getItemCount() {
        if (discountList != null) {
            return discountList.size();
        }
        return 0;
    }

    public class discountViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCode;
        private TextView tvDate;
        private TextView tvPercent;
        private Button btnApply;
        public discountViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCode = itemView.findViewById(R.id.tvCode);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvPercent = itemView.findViewById(R.id.tvPercent);
            btnApply = itemView.findViewById(R.id.btnApply);
            btnApply.setOnClickListener(v -> {
                currentUser.appliedDiscount = discountList.get(getAdapterPosition());
                itemView.getContext().startActivity(new Intent(itemView.getContext(), PaymentInformation.class));
            });
        }
    }
}
