package com.example.mobile.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.mobile.R;
import com.example.mobile.model.advertisement;
import com.example.mobile.model.product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adsAdapter extends PagerAdapter {

    private Context context;
    private List<advertisement> adsList;

    public adsAdapter(Context context, List<advertisement> adsList) {
        this.context = context;
        this.adsList = adsList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.ads_item, container, false);
        ImageView imgPhoto = view.findViewById(R.id.imAds);

        advertisement ads = adsList.get(position);
        if(ads != null) {
            Glide.with(context).load(ads.getResource()).into(imgPhoto);
        }
        //Add view to view group
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if(adsList != null) {
            return adsList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
