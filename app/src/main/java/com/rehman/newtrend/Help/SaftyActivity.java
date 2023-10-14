package com.rehman.newtrend.Help;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rehman.newtrend.R;

public class SaftyActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout layout_less,layout_less2, layout_more,layout_more2;

    ImageView back_image,
            show_more,show_more2,show_more3,show_more4,
            show_less,show_less2,show_less3,show_less4,
            dislike_image,dislike_image2,dislike_image3,dislike_image4,
            like_image,like_image2,like_image3,like_image4;
    TextView feed_text,feed_text2,feed_text3,feed_text4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safty);

        intiView();

        back_image.setOnClickListener(this::onClick);

        show_more.setOnClickListener(this::onClick);
        show_less.setOnClickListener(this::onClick);
        dislike_image.setOnClickListener(this::onClick);
        like_image.setOnClickListener(this::onClick);

        show_more2.setOnClickListener(this::onClick);
        show_less2.setOnClickListener(this::onClick);
        dislike_image2.setOnClickListener(this::onClick);
        like_image2.setOnClickListener(this::onClick);
    }
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

            case R.id.like_image:
            case R.id.dislike_image:
                feed_text.setText("Thank you for the feedback");
                like_image.setVisibility(View.GONE);
                dislike_image.setVisibility(View.GONE);
                break;

            //start second

            case R.id.show_less2:
                layout_less2.setVisibility(View.VISIBLE);
                layout_more2.setVisibility(View.GONE);
                break;

            case R.id.show_more2:
                layout_less2.setVisibility(View.GONE);
                layout_more2.setVisibility(View.VISIBLE);
                break;

            case R.id.like_image2:
            case R.id.dislike_image2:
                feed_text2.setText("Thank you for the feedback");
                like_image2.setVisibility(View.GONE);
                dislike_image2.setVisibility(View.GONE);
                break;
        }

    }

    private void intiView()
    {
        back_image = findViewById(R.id.back_image);

        layout_less = findViewById(R.id.layout_less);
        show_more = findViewById(R.id.show_more);
        layout_more = findViewById(R.id.layout_more);
        show_less = findViewById(R.id.show_less);
        feed_text = findViewById(R.id.feed_text);
        dislike_image = findViewById(R.id.dislike_image);
        like_image = findViewById(R.id.like_image);

        layout_less2 = findViewById(R.id.layout_less2);
        show_more2 = findViewById(R.id.show_more2);
        layout_more2 = findViewById(R.id.layout_more2);
        show_less2 = findViewById(R.id.show_less2);
        feed_text2 = findViewById(R.id.feed_text2);
        dislike_image2 = findViewById(R.id.dislike_image2);
        like_image2 = findViewById(R.id.like_image2);
    }
}