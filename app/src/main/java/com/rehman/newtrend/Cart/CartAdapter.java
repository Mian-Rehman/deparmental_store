package com.rehman.newtrend.Cart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;
import com.rehman.newtrend.Chat.ChatAdapter;
import com.rehman.newtrend.Chat.ChatModel;
import com.rehman.newtrend.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<CartModel> mDataList;

    public CartAdapter(Context context, ArrayList<CartModel> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View myView = LayoutInflater.from(context).inflate(R.layout.cart_list,parent,false);

        return new MyViewHolder(myView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CartModel model  = mDataList.get(position);
        holder.productKey_text.setText("Product ID: "+model.getCartKey());
        Glide.with(context).load(model.getProductCoverImage()).into(holder.cover_image);
        holder.title_text.setText(model.getProductName());
        holder.price_text.setText("RS: "+model.getProductPrice());
        holder.discount_text.setText("Discount Price: "+model.getProductDiscountPrice());


        holder.delete_image.setOnClickListener(v -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(holder.productKey_text.getContext());
            builder.setTitle("Remove Cart product");
            builder.setMessage("Press Yes or No?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    FirebaseDatabase.getInstance().getReference("CustomerInfoDetails")
                            .child(model.getUserUID()).child("myCart").child(model.getCartKey()).removeValue();
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            builder.show();
        });

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

       TextView productKey_text,title_text,price_text,discount_text;
       ImageView cover_image,delete_image;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productKey_text = itemView.findViewById(R.id.productKey_text);
            cover_image = itemView.findViewById(R.id.cover_image);
            title_text = itemView.findViewById(R.id.title_text);
            price_text = itemView.findViewById(R.id.price_text);
            discount_text = itemView.findViewById(R.id.discount_text);
            delete_image = itemView.findViewById(R.id.delete_image);
        }
    }


}
