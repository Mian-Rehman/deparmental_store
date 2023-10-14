package com.rehman.newtrend.AdapterProduct;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rehman.newtrend.Adapter.SmartWatchAdapter;
import com.rehman.newtrend.Model.ProductDetailsModel;
import com.rehman.newtrend.Product.ProductDetailsActivity;
import com.rehman.newtrend.R;

import java.util.ArrayList;

public class WatchAdapter extends RecyclerView.Adapter<WatchAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<ProductDetailsModel> mDataList;

    public WatchAdapter(Context context, ArrayList<ProductDetailsModel> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View myView = LayoutInflater.from(context).inflate(R.layout.smart_watch_item_list,parent,false);

        return new MyViewHolder(myView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ProductDetailsModel model = mDataList.get(position);

        String imageUrl = model.getProductCoverImage();

        Glide.with(context).load(imageUrl).into(holder.product_image);

        holder.productPrice_text.setText("Price: Rs "+model.getProductPrice());
        holder.title_text.setText(model.getProductTitle());

        if (model.getProductDiscountPrice().equals(""))
        {
            holder.offer_image.setVisibility(View.GONE);
            holder.productDiscountPrice_text.setVisibility(View.GONE);
        }
        else
        {
            holder.offer_image.setVisibility(View.VISIBLE);
            holder.productDiscountPrice_text.setVisibility(View.VISIBLE);
            holder.productDiscountPrice_text.setText("Offer Price: Rs "+model.getProductDiscountPrice());
        }


        holder.details_text.setOnClickListener(v -> {

            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("productKey",model.getProductKey());
            intent.putExtra("productCategory",model.getProductCategory());
            intent.putExtra("productColor",model.getProductColor());
            intent.putExtra("productCoverImage",model.getProductCoverImage());
            intent.putExtra("productDescription",model.getProductDescription());
            intent.putExtra("productDiscountPrice",model.getProductDiscountPrice());
            intent.putExtra("productPrice",model.getProductPrice());
            intent.putExtra("productTitle",model.getProductTitle());
            context.startActivity(intent);

        });

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("productKey",model.getProductKey());
            intent.putExtra("productCategory",model.getProductCategory());
            intent.putExtra("productColor",model.getProductColor());
            intent.putExtra("productCoverImage",model.getProductCoverImage());
            intent.putExtra("productDescription",model.getProductDescription());
            intent.putExtra("productDiscountPrice",model.getProductDiscountPrice());
            intent.putExtra("productPrice",model.getProductPrice());
            intent.putExtra("productTitle",model.getProductTitle());
            context.startActivity(intent);

        });



    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView offer_image,product_image;
        TextView productPrice_text,productDiscountPrice_text,details_text,title_text;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            offer_image = itemView.findViewById(R.id.offer_image);
            product_image = itemView.findViewById(R.id.product_image);
            productPrice_text = itemView.findViewById(R.id.productPrice_text);
            productDiscountPrice_text = itemView.findViewById(R.id.productDiscountPrice_text);
            details_text = itemView.findViewById(R.id.details_text);
            title_text = itemView.findViewById(R.id.title_text);
        }
    }


}
