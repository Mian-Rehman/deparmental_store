package com.rehman.newtrend.Product;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rehman.newtrend.AdapterProduct.AllProductAdapter;
import com.rehman.newtrend.Constant;
import com.rehman.newtrend.MainActivity;
import com.rehman.newtrend.Model.ProductDetailsModel;
import com.rehman.newtrend.R;

import java.util.ArrayList;

public class ViewAllProductActivity extends AppCompatActivity {

    RecyclerView product_recycleView;
    AllProductAdapter adapter;
    ArrayList<ProductDetailsModel> mDataList;
    TextView topCate_text;
    ImageView back_image;
    String productCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_product);

        initViews();
        getIntentValue();

        back_image.setOnClickListener(v -> {
            startActivity(new Intent(ViewAllProductActivity.this, MainActivity.class));
            finish();
        });
    }

    private void getIntentValue()
    {
        Intent intent = getIntent();
        productCategory = intent.getStringExtra("productCategory");

        topCate_text.setText(productCategory);
        showRecycleValue(productCategory);
    }

    private void showRecycleValue(String productCategory)
    {

        mDataList = new ArrayList<>();
        adapter = new AllProductAdapter(this,mDataList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        product_recycleView.setLayoutManager(gridLayoutManager);
//        product_recycleView.setLayoutManager(new LinearLayoutManager(this));
        product_recycleView.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products")
                .child(productCategory);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if (snapshot.exists())
                {
                    ProductDetailsModel model = snapshot.getValue(ProductDetailsModel.class);
                    mDataList.add(model);
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(ViewAllProductActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void initViews()
    {
        product_recycleView = findViewById(R.id.product_recycleView);
        topCate_text = findViewById(R.id.topCate_text);
        back_image = findViewById(R.id.back_image);
    }
}