package com.rehman.newtrend.Cart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rehman.newtrend.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    RecyclerView cart_recycle;
    ImageView back_image;
    CartAdapter adapter;
    Button btn_buy;
    ArrayList<CartModel> mDataList;
    FirebaseAuth mAuth;
    String userUID;
    ArrayList<String> headArrayList = new ArrayList<>();
    Map<String,String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initViews();
        mAuth = FirebaseAuth.getInstance();
        userUID = mAuth.getCurrentUser().getUid();

        getCartValue();

        back_image.setOnClickListener(v -> {
            onBackPressed();
        });

        btn_buy.setOnClickListener(v -> {


            Intent intent = new Intent(CartActivity.this,BuyActivity.class);
            intent.putExtra("customerUID",userUID);
            intent.putStringArrayListExtra("HeadList",headArrayList);
            startActivity(intent);

        });
    }



    private void getCartValue()
    {
        mDataList = new ArrayList<>();
        adapter = new CartAdapter(this,mDataList);
        cart_recycle.setLayoutManager(new LinearLayoutManager(this));
        cart_recycle.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("CustomerInfoDetails").child(userUID).child("myCart");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               if (snapshot.exists())
               {
                   CartModel model = snapshot.getValue(CartModel.class);
                   mDataList.add(model);
                   adapter.notifyDataSetChanged();
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
        cart_recycle = findViewById(R.id.cart_recycle);
        back_image = findViewById(R.id.back_image);
        btn_buy = findViewById(R.id.btn_buy);
    }
}