package com.rehman.newtrend.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rehman.newtrend.Cart.AddressModel;
import com.rehman.newtrend.Cart.ViewDetailsActivity;
import com.rehman.newtrend.R;

import java.util.List;

public class SuccessOrderAdapter extends RecyclerView.Adapter<SuccessOrderAdapter.MyViewHolder>{

    private Context context;
    private List<AddressModel> mDataList;

    public SuccessOrderAdapter(Context context, List<AddressModel> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview = LayoutInflater.from(context).inflate(R.layout.pending_booking_list_layout,parent,false);

        return new MyViewHolder(myview);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        AddressModel model = mDataList.get(position);

        if (model.getOrderType().equals("success"))
        {
            holder.card_layout.setVisibility(View.VISIBLE);
            holder.orderStatus_text.setText("Order Status: "+model.getOrderType());
            holder.orderKey.setText("Order Key: "+model.getOrderKey());
        }
        else
        {
            holder.card_layout.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewDetailsActivity.class);
            intent.putExtra("userUID",model.getUserUID());
            intent.putExtra("productKey",model.getProductKey());
            intent.putExtra("productName",model.getProductName());
            intent.putExtra("productPrice",model.getProductPrice());
            intent.putExtra("productDiscountPrice",model.getProductDiscountPrice());
            intent.putExtra("productDescription",model.getProductDescription());
            intent.putExtra("productCoverImage",model.getProductCoverImage());
            intent.putExtra("firstName",model.getFirstName());
            intent.putExtra("lastName",model.getLastName());
            intent.putExtra("email",model.getEmail());
            intent.putExtra("phoneNumber",model.getPhoneNumber());
            intent.putExtra("addressOne",model.getAddressOne());
            intent.putExtra("addressTwo",model.getAddressTwo());
            intent.putExtra("city",model.getCity());
            intent.putExtra("state",model.getState());
            intent.putExtra("orderType",model.getOrderType());
            intent.putExtra("orderKey",model.getOrderKey());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView orderStatus_text,view_details,orderKey;
        CardView card_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            card_layout = itemView.findViewById(R.id.card_layout);
            view_details = itemView.findViewById(R.id.view_details);
            orderStatus_text = itemView.findViewById(R.id.orderStatus_text);
            orderKey = itemView.findViewById(R.id.orderKey);

            card_layout.setVisibility(View.GONE);
        }
    }
}
