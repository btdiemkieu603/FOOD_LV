package com.example.mobile.adapter;

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

import java.util.List;

public class orderItemAdapter extends RecyclerView.Adapter<orderItemAdapter.orderItemViewHolder> {
    private List<item> itemList;

    public orderItemAdapter(List<item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public orderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordered_product_item, parent, false);
        return new orderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull orderItemViewHolder holder, int position) {
        item item = itemList.get(position);
        Picasso.get().load(item.getProduct().getImageLink()).placeholder(R.drawable.loading).into(holder.ivItem);
        holder.tvItem.setText(item.getProduct().getProductName());
    }

    @Override
    public int getItemCount() {
        if (itemList != null) {
            return itemList.size();
        }
        return 0;
    }

    public class orderItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivItem;
        private TextView tvItem;
        public orderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItem = itemView.findViewById(R.id.ivItem);
            tvItem = itemView.findViewById(R.id.tvItem);
        }
    }
}
