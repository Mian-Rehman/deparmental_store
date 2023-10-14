package com.rehman.newtrend.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rehman.newtrend.Model.Tags;
import com.rehman.newtrend.Product.ViewAllProductActivity;
import com.rehman.newtrend.R;

import java.util.ArrayList;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.ViewHolder>
{
    Context context;
    ArrayList<Tags> tagsArrayList;

    public TagsAdapter(Context context, ArrayList<Tags> tagsArrayList) {
        this.context = context;
        this.tagsArrayList = tagsArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tag_card , parent , false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Tags tags = tagsArrayList.get(position);
        holder.tag_btn.setText(tags.getName());

        holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ViewAllProductActivity.class);
                intent.putExtra("productCategory",tags.getName());
                context.startActivity(intent);
        });

        holder.tag_btn.setOnClickListener(v -> {

            Intent intent = new Intent(context, ViewAllProductActivity.class);
            intent.putExtra("productCategory",tags.getName());
            context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return tagsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button tag_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tag_btn = itemView.findViewById(R.id.tag_btn);

        }
    }
}
