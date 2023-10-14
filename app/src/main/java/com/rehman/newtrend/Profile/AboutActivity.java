package com.rehman.newtrend.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rehman.newtrend.R;

public class AboutActivity extends AppCompatActivity {

    ImageView back_image;
    RelativeLayout email_layout,whatsapp_layout,instagram_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initViews();

        back_image.setOnClickListener(v -> {
            onBackPressed();
        });

    }

    private void initViews()
    {
        back_image = findViewById(R.id.back_image);
        email_layout = findViewById(R.id.email_layout);
        whatsapp_layout = findViewById(R.id.whatsapp_layout);
        instagram_layout = findViewById(R.id.instagram_layout);
    }
}