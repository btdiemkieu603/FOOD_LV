package com.example.mobile.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile.R;
import com.example.mobile.controller.ProductMenu;
import com.example.mobile.model.category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.categoryViewHolder> {
    private final List<category> categoryList;

    public categoryAdapter(List<category> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public categoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new categoryViewHolder(view, categoryList);
    }

    @Override
    public void onBindViewHolder(@NonNull categoryViewHolder holder, int position) {
        category category = categoryList.get(position);
        if (category == null) {
            return ;
        }
        Picasso.get().load(category.getAvatarLink()).placeholder(R.drawable.loading).into(holder.ivCate);
        holder.tvCate.setText(category.getCategory());
    }

    @Override
    public int getItemCount() {
        if (categoryList != null) {
            return categoryList.size();
        }
        return 0;
    }

    public static class categoryViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivCate;
        private final TextView tvCate;
        private List<category> categoryList;
        public categoryViewHolder(@NonNull View itemView, List<category> categoryList) {
            super(itemView);
            this.categoryList = categoryList;
            ivCate = itemView.findViewById(R.id.ivCate);
            tvCate = itemView.findViewById(R.id.tvCate);
            ivCate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String categoryId = getCategoryFromView(getAdapterPosition());
                    Intent intent = new Intent(itemView.getContext(), ProductMenu.class);
                    intent.putExtra("category_id", categoryId);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        private String getCategoryFromView(int adapterPosition) {
            return categoryList.get(adapterPosition).getCategoryID();
        }
    }
}
