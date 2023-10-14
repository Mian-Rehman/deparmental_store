package com.rehman.newtrend.Help;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rehman.newtrend.R;

public class AccountInfoActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout layout_less,layout_more,paymentLayout_more,paymentLayout_less;
    ImageView show_more,show_less,like_image,dislike_image,paymentShow_less,paymentShow_more,dislike_image2
            ,like_image2,back_image;
    Button email_button,payment_button;
    TextView feed_text,feed_text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        initViewField();

        show_less.setOnClickListener(this::onClick);
        show_more.setOnClickListener(this::onClick);
        email_button.setOnClickListener(this::onClick);
        like_image.setOnClickListener(this::onClick);
        dislike_image.setOnClickListener(this::onClick);

        paymentShow_less.setOnClickListener(this::onClick);
        paymentShow_more.setOnClickListener(this::onClick);
        payment_button.setOnClickListener(this::onClick);
        like_image2.setOnClickListener(this::onClick);
        dislike_image2.setOnClickListener(this::onClick);
        back_image.setOnClickListener(this::onClick);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        switch (id)
        {
            case R.id.back_image:
                onBackPressed();
                break;

            case R.id.show_less:
                layout_less.setVisibility(View.VISIBLE);
                layout_more.setVisibility(View.GONE);
                break;

            case R.id.show_more:
                layout_less.setVisibility(View.GONE);
                layout_more.setVisibility(View.VISIBLE);
                break;

            case R.id.email_button:
//                startActivity(new Intent(AccountInfoActivity.this,EditProfileInfoActivity.class));
                break;

            case R.id.like_image:
            case R.id.dislike_image:
                feed_text.setText("Thank you for the feedback");
                like_image.setVisibility(View.GONE);
                dislike_image.setVisibility(View.GONE);
                break;

            case R.id.paymentShow_less:
                paymentLayout_less.setVisibility(View.VISIBLE);
                paymentLayout_more.setVisibility(View.GONE);
                break;

            case R.id.paymentShow_more:
                paymentLayout_less.setVisibility(View.GONE);
                paymentLayout_more.setVisibility(View.VISIBLE);
                break;

            case R.id.payment_button:
                //code here
                break;


            case R.id.like_image2:
            case R.id.dislike_image2:
                feed_text2.setText("Thank you for the feedback");
                like_image2.setVisibility(View.GONE);
                dislike_image2.setVisibility(View.GONE);
                break;
        }

    }

    private void initViewField()
    {
        layout_less = findViewById(R.id.layout_less);
        layout_more = findViewById(R.id.layout_more);
        show_more = findViewById(R.id.show_more);
        show_less = findViewById(R.id.show_less);
        like_image = findViewById(R.id.like_image);
        dislike_image = findViewById(R.id.dislike_image);
        email_button = findViewById(R.id.email_button);
        feed_text = findViewById(R.id.feed_text);
        paymentLayout_more = findViewById(R.id.paymentLayout_more);
        paymentShow_less = findViewById(R.id.paymentShow_less);
        paymentLayout_less = findViewById(R.id.paymentLayout_less);
        paymentShow_more = findViewById(R.id.paymentShow_more);
        payment_button = findViewById(R.id.payment_button);
        feed_text2 = findViewById(R.id.feed_text2);
        dislike_image2 = findViewById(R.id.dislike_image2);
        like_image2 = findViewById(R.id.like_image2);
        back_image = findViewById(R.id.back_image);
    }

}