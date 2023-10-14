package com.rehman.newtrend.Adapter;

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
import com.rehman.newtrend.R;

import java.util.List;

public class MainBannerAdapter extends RecyclerView.Adapter<MainBannerAdapter.BannerHolder>
{

    private Context context;
    private List<String> urls;
    private List<String> names;


    public MainBannerAdapter(Context context, List<String> urls,List<String> names) {
        this.context = context;
        this.urls = urls;
        this.names = names;

    }

    @NonNull
    @Override
    public BannerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.mian_banner_layout,parent,false);
        return new BannerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerHolder holder, int position) {

        String url=urls.get(position%urls.size());
        String name = names.get(position%urls.size());
        holder.text_names.setText(name);

        Glide.with(context).load(url).centerCrop().into(holder.imgBanner);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    @Override
    public int getItemCount() {
        return urls==null?0:Integer.MAX_VALUE;
    }

    public class BannerHolder extends RecyclerView.ViewHolder{
        ImageView imgBanner;
        TextView text_names;

        public BannerHolder(@NonNull View itemView) {
            super(itemView);
            imgBanner=itemView.findViewById(R.id.imgBanner);
            text_names=itemView.findViewById(R.id.text_names);

        }

    }
}
