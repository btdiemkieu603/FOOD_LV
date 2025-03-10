package com.example.mobile.adapter;

import android.content.ClipData;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile.R;
import com.example.mobile.controller.OrderDetail;
import com.example.mobile.model.item;
import com.example.mobile.model.order;
import com.example.mobile.model.receivedOrder;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class orderAdapter extends RecyclerView.Adapter<orderAdapter.orderViewHolder> {
    private List<receivedOrder> orderList;

    public orderAdapter(List<receivedOrder> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public orderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
        return new orderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull orderViewHolder holder, int position) {
        receivedOrder order = orderList.get(position);
        holder.tvOrderCode.setText("Mã đơn: #" + order.getId());
        holder.tvOrderTime.setText(order.getOrderdate());
        DecimalFormat df = new DecimalFormat("#,###");
        float price = Float.parseFloat(order.getTotalprice());
        String formattedPrice = df.format(price + 10000);
        holder.tvOrderTotal.setText("Tổng hóa đơn: " + formattedPrice + "đ");
        orderItemAdapter orderItemAdapter = new orderItemAdapter(order.getItems());
        holder.rcvItem.setAdapter(orderItemAdapter);
    }

    @Override
    public int getItemCount() {
        if (orderList != null) {
            return orderList.size();
        }
        return 0;
    }

    public class orderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOrderCode, tvOrderTime, tvOrderTotal, tvOrderDetail;
        private RecyclerView rcvItem;
        public orderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderCode = itemView.findViewById(R.id.tvOrderCode);
            tvOrderTime = itemView.findViewById(R.id.tvOrderTime);
            tvOrderTotal = itemView.findViewById(R.id.tvOrderTotal);
            tvOrderDetail = itemView.findViewById(R.id.tvOrderDetail);
            tvOrderDetail.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), OrderDetail.class);
                String orderID = String.valueOf(orderList.get(getAdapterPosition()).getId());
                String orderStatusID = String.valueOf(orderList.get(getAdapterPosition()).getOrderStatus().getId());
                System.out.print("orderID selected: " + orderID);
                intent.putExtra("orderID", orderID);
                intent.putExtra("orderStatusID", orderStatusID);
                itemView.getContext().startActivity(intent);
            });
            rcvItem = itemView.findViewById(R.id.rcvItem);
        }
    }
}
