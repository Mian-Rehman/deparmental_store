package com.rehman.newtrend.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.rehman.newtrend.AdapterProduct.CableAdapter;
import com.rehman.newtrend.Islam.CounterActivity;
import com.rehman.newtrend.Model.ProductDetailsModel;
import com.rehman.newtrend.Model.TasbeehModel;
import com.rehman.newtrend.Product.ProductDetailsActivity;
import com.rehman.newtrend.R;

import java.util.ArrayList;

public class TasbeehAdapter extends RecyclerView.Adapter<TasbeehAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<TasbeehModel> mDataList;

    public TasbeehAdapter(Context context, ArrayList<TasbeehModel> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View myView = LayoutInflater.from(context).inflate(R.layout.tasbeeh_list,parent,false);

        return new MyViewHolder(myView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TasbeehModel model = mDataList.get(position);

        holder.text_name.setText(model.getTasbeehName());
        holder.text_steps.setText(model.getTasbeehSteps());

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(context, CounterActivity.class);
            intent.putExtra("name",model.getTasbeehName());
            intent.putExtra("steps",model.getTasbeehSteps());
            context.startActivity(intent);

        });


    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

    TextView text_name,text_steps;
    ImageView delete_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            text_name = itemView.findViewById(R.id.text_name);
            delete_image = itemView.findViewById(R.id.delete_image);
            text_steps = itemView.findViewById(R.id.text_steps);

        }
    }


}
