package com.example.mobile.adapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile.R;
import com.example.mobile.model.item;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class orderDetailAdapter extends RecyclerView.Adapter<orderDetailAdapter.orderDetailViewHolder> {
    private List<item> itemList;

    public orderDetailAdapter(List<item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public orderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_item, parent, false);
        return new orderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull orderDetailViewHolder holder, int position) {
        item item = itemList.get(position);
        holder.tvQuantity.setText(item.getQuantity());
        holder.tvProductName.setText(item.getProduct().getProductName());
        DecimalFormat df = new DecimalFormat("#,###");
        String formattedSum = df.format(Double.parseDouble(item.getPrice()));
        holder.tvPrice.setText(formattedSum + "đ");
        Picasso.get().load(item.getProduct().getImageLink()).placeholder(R.drawable.loading).into(holder.ivProduct);
    }

    @Override
    public int getItemCount() {
        if (itemList != null) {
            return itemList.size();
        }
        return 0;
    }

    public class orderDetailViewHolder extends RecyclerView.ViewHolder {
        private TextView tvQuantity, tvProductName, tvPrice;
        private ImageView ivProduct;
        public orderDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivProduct = itemView.findViewById(R.id.ivProduct);
        }
    }
}
