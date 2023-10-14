package com.rehman.newtrend.Product;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rehman.newtrend.Adapter.ImageAdapter;
import com.rehman.newtrend.Adapter.MainBannerAdapter;
import com.rehman.newtrend.AdapterProduct.MenAdapter;
import com.rehman.newtrend.Cart.BuyActivity;
import com.rehman.newtrend.Cart.CartActivity;
import com.rehman.newtrend.Cart.CartModel;
import com.rehman.newtrend.Chat.ChatActivity;
import com.rehman.newtrend.Constant;
import com.rehman.newtrend.Model.ImageModel;
import com.rehman.newtrend.Model.ProductDetailsModel;
import com.rehman.newtrend.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ProductDetailsActivity extends AppCompatActivity {


    RecyclerView imageSlider_recycleView,related_recycleView;
    ArrayList<ImageModel> mDataList;
    ArrayList<ProductDetailsModel> mMenList;
    MenAdapter menAdapter;
    LinearLayoutManager menLinearLayout;
    String userUID;
    Button btn_whatsApp,btn_call,btn_cart,btn_viewCart;

    ImageAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    TextView topCate_text,price_text,offerPrice_text,color_text,des_text,title_text;

    ImageView back_image;
    String productKey,productCategory,productColor,productCoverImage,productDescription
            ,productDiscountPrice,productPrice,productTitle;

    FirebaseAuth auth;
    LinearLayout chat_layout;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        progressDialog = ProgressDialog.show(ProductDetailsActivity.this, "", "Processing", true);

        initViews();
        getIntentValue();
        checkUserPermission();

        auth = FirebaseAuth.getInstance();
        userUID = auth.getCurrentUser().getUid();


        if (Constant.rewardedAd.isLoaded()){
            Constant.rewardedAd.show(ProductDetailsActivity.this, new RewardedAdCallback() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                    Toast.makeText(ProductDetailsActivity.this, rewardItem.getAmount()+" Coins is your reward", Toast.LENGTH_SHORT).show();

                    Constant.loadRewardedAdd(ProductDetailsActivity.this);
                }
            });
        }
        else
        {
            Toast.makeText(this, "Ad not available", Toast.LENGTH_SHORT).show();
        }

        back_image.setOnClickListener(v -> {
            onBackPressed();
        });

        btn_call.setOnClickListener(v -> {
            String phoneNumber = "923104003404";
            callToPhone(phoneNumber);
        });

       btn_whatsApp.setOnClickListener(v -> {
           String WhatsNumber = "923104003404";
           sendToWhatsapp(WhatsNumber);
       });

        chat_layout.setOnClickListener(v -> {
            startActivity(new Intent(ProductDetailsActivity.this, ChatActivity.class));
        });

        btn_cart.setOnClickListener(v -> {

            Intent intent = new Intent(ProductDetailsActivity.this, BuyActivity.class);
            intent.putExtra("userUID",userUID);
            intent.putExtra("productTitle",productTitle);
            intent.putExtra("productPrice",productPrice);
            intent.putExtra("productDiscountPrice",productDiscountPrice);
            intent.putExtra("productDescription",productDescription);
            intent.putExtra("productCoverImage",productCoverImage);
            intent.putExtra("productKey",productKey);
            startActivity(intent);


//            progressDialog = ProgressDialog.show(ProductDetailsActivity.this, "", "Processing", true);
//            btn_cart.setVisibility(View.INVISIBLE);
//            btn_viewCart.setVisibility(View.VISIBLE);
//
//            DatabaseReference reference = FirebaseDatabase.getInstance()
//                    .getReference("CustomerInfoDetails").child(userUID);
//
//            reference.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (snapshot.exists())
//                    {
//                        String key = reference.push().getKey();
//                        CartModel model = new CartModel(userUID,productKey,productTitle,productPrice,productDiscountPrice
//                        ,productDescription,productCoverImage,key);
//
//                        assert key != null;
//                        reference.child("myCart").child(key).setValue(model);
//                        progressDialog.dismiss();
//                    }
//                    else
//                    {
//                        Toast.makeText(ProductDetailsActivity.this, "Login Required", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
        });

       btn_viewCart.setOnClickListener(v -> {
           startActivity(new Intent(ProductDetailsActivity.this, CartActivity.class));
       });


    }

    private void getImage(String productCategory, String productKey)
    {
        ImageModel model  = new ImageModel();
        model.setProductImages(productCoverImage);
        mDataList = new ArrayList<>();
        mDataList.add(model);

        adapter = new ImageAdapter(this,mDataList);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        imageSlider_recycleView.setLayoutManager(linearLayoutManager);
        imageSlider_recycleView.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products")
                .child(productCategory).child(productKey).child("image");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists())
                {
                    ImageModel model = snapshot.getValue(ImageModel.class);
                    mDataList.add(model);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(ProductDetailsActivity.this, "No image", Toast.LENGTH_SHORT).show();
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

    @SuppressLint("SetTextI18n")
    private void getIntentValue()
    {
        Intent intent = getIntent();
        productKey = intent.getStringExtra("productKey");
        productCategory = intent.getStringExtra("productCategory");
        productColor = intent.getStringExtra("productColor");
        productCoverImage = intent.getStringExtra("productCoverImage");
        productDescription = intent.getStringExtra("productDescription");
        productDiscountPrice = intent.getStringExtra("productDiscountPrice");
        productPrice = intent.getStringExtra("productPrice");
        productTitle = intent.getStringExtra("productTitle");

        topCate_text.setText(productCategory);
        price_text.setText("Rs: "+productPrice);
        offerPrice_text.setText("Discount Price: Rs "+productDiscountPrice);
        color_text.setText(productColor);
        des_text.setText(productDescription);
        title_text.setText(productTitle);

        getImage(productCategory,productKey);
        relatedProducts(productCategory);

    }

    private void relatedProducts(String productCategory)
    {
        mMenList = new ArrayList<>();
        menLinearLayout = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        menAdapter = new MenAdapter(this,mMenList);
        related_recycleView.setLayoutManager(menLinearLayout);
        related_recycleView.setAdapter(menAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products")
                .child(productCategory);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists())
                {
                    ProductDetailsModel model = snapshot.getValue(ProductDetailsModel.class);
                    mMenList.add(model);
                    menAdapter.notifyDataSetChanged();
                }
                else
                {
                    related_recycleView.setVisibility(View.GONE);
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
        imageSlider_recycleView = findViewById(R.id.imageSlider_recycleView);
        topCate_text = findViewById(R.id.topCate_text);
        back_image = findViewById(R.id.back_image);
        price_text = findViewById(R.id.price_text);
        offerPrice_text = findViewById(R.id.offerPrice_text);
        color_text = findViewById(R.id.color_text);
        des_text = findViewById(R.id.des_text);
        title_text = findViewById(R.id.title_text);
        related_recycleView = findViewById(R.id.related_recycleView);
        btn_whatsApp = findViewById(R.id.btn_whatsApp);
        btn_call = findViewById(R.id.btn_call);
        btn_cart = findViewById(R.id.btn_cart);
        chat_layout = findViewById(R.id.chat_layout);
        btn_viewCart = findViewById(R.id.btn_viewCart);
    }

    private void callToPhone(String phoneNumber)
    {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+ phoneNumber));//change the number
        startActivity(callIntent);
    }

    private void sendToWhatsapp(String whatsNumber)
    {
        try {
            String text = productCategory+"\n"+productTitle+"\n"+productKey+"\n"+"userID: "+userUID;// Replace with your message.
//           String toNumber = "xxxxxxxxxx"; // Replace with mobile phone number without +Sign or leading zeros, but with country code
            //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+whatsNumber +"&text="+text));
            startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void checkUserPermission()
    {
        if (ContextCompat.checkSelfPermission(ProductDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ProductDetailsActivity.this, new String[]{
                    Manifest.permission.CALL_PHONE


            }, 100);
        }
    }


}