package com.rehman.newtrend.Islam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.style.ImageSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rehman.newtrend.R;

public class CounterActivity extends AppCompatActivity implements View.OnTouchListener {

    ImageView back_image;
    RelativeLayout counterTouchLayout;
    TextView counter_text,step_text,counterTotal_text,name_text;
    Button btn_reset;

    int count = 0;
    int runCount;
    int stepCount = 0;
    String steps,name,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        runCount = 100;


        back_image = findViewById(R.id.back_image);
        counterTouchLayout = findViewById(R.id.counterTouchLayout);
        counter_text = findViewById(R.id.counter_text);
        btn_reset = findViewById(R.id.btn_reset);
        step_text = findViewById(R.id.step_text);
        counterTotal_text = findViewById(R.id.counterTotal_text);
        name_text = findViewById(R.id.name_text);

        getIntentValue();

        back_image.setOnClickListener(v -> {
            onBackPressed();
        });

        btn_reset.setOnClickListener(v -> {
            count = 0;
            counter_text.setText(String.valueOf(count));
        });

    counterTouchLayout.setOnTouchListener(this::onTouch);

    }

    @SuppressLint("SetTextI18n")
    private void getIntentValue()
    {
        Intent intent = getIntent();
       name = intent.getStringExtra("name");
       steps = intent.getStringExtra("steps");
       name_text.setText(name);
       counterTotal_text.setText("Out of "+steps);
       stepCount = Integer.parseInt(steps);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        count = count+1;
        counter_text.setText(String.valueOf(count));

        if (count == runCount)
        {
            stepCount = stepCount + 1;
            count = 0;
            counter_text.setText(String.valueOf(count));
            step_text.setText("Step "+stepCount);
        }

        return false;
    }
}