package com.rehman.newtrend.Cart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rehman.newtrend.R;

public class ViewDetailsActivity extends AppCompatActivity {

    ImageView cover_Image;
    TextView orderType_text,orderKey_text,firstName_text,email_text,phone_text,address_text;

    String userUID,productKey,productName,productPrice,productDiscountPrice,productDescription,productCoverImage
            ,phoneNumber,orderType,orderKey,firstName,lastName,email,city,state,addressOne,addressTwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        intiViws();
        getIntentValue();
    }

    private void getIntentValue()
    {
        Intent intent = getIntent();

        userUID = intent.getStringExtra("userUID");
        productKey = intent.getStringExtra("productKey");
        productName = intent.getStringExtra("productName");
        productPrice = intent.getStringExtra("productPrice");
        productDiscountPrice = intent.getStringExtra("productDiscountPrice");
        productDescription = intent.getStringExtra("productDescription");
        productCoverImage = intent.getStringExtra("productCoverImage");
        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        email = intent.getStringExtra("email");
        phoneNumber = intent.getStringExtra("phoneNumber");
        addressOne = intent.getStringExtra("addressOne");
        addressTwo = intent.getStringExtra("addressTwo");
        city = intent.getStringExtra("city");
        state = intent.getStringExtra("state");
        orderType = intent.getStringExtra("orderType");
        orderKey = intent.getStringExtra("orderKey");

        Glide.with(this).load(productCoverImage).into(cover_Image);
        orderKey_text.setText("Order Status: "+orderType);
        orderKey_text.setText("Order Key: "+orderKey);
        firstName_text.setText("Name: "+firstName+" "+lastName);
        email_text.setText(email);
        phone_text.setText(phoneNumber);
        address_text.setText(addressOne);

    }

    private void intiViws()
    {
        cover_Image = findViewById(R.id.cover_Image);
        orderType_text = findViewById(R.id.orderType_text);
        orderKey_text = findViewById(R.id.orderKey_text);
        firstName_text = findViewById(R.id.firstName_text);
        email_text = findViewById(R.id.email_text);
        phone_text = findViewById(R.id.phone_text);
        address_text = findViewById(R.id.address_text);
    }
}