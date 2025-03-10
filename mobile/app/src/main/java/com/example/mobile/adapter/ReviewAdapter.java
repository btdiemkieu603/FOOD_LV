package com.example.mobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile.R;
import com.example.mobile.model.customer;
import com.example.mobile.model.review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private final List<review> reviewList;

    public ReviewAdapter(List<review> reviewList) {
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewAdapter.ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        review review = reviewList.get(position);
        if (review == null) {
            return ;
        }

        holder.tvName.setText(review.getCustomer().getFirstName() + " " + review.getCustomer().getLastName());
        holder.tvDateReview.setText(review.getDateReview());
        holder.ratingBar.setRating(review.getNumOfStar());
        holder.tvContent.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        if (reviewList != null) {
            return reviewList.size();
        }
        return 0;
    }
    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvDateReview;
        public RatingBar ratingBar;
        public TextView tvContent;
        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDateReview = itemView.findViewById(R.id.tvDateReview);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            tvContent = itemView.findViewById(R.id.tvContent);
        }
    }
}
