package com.rehman.newtrend.Start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.Lottie;
import com.airbnb.lottie.LottieAnimationView;
import com.rehman.newtrend.MainActivity;
import com.rehman.newtrend.R;

public class SplashScreen extends AppCompatActivity {

   LottieAnimationView start_splash;
    TextView start_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        start_splash = findViewById(R.id.start_splash);
        start_text  = findViewById(R.id.start_text);

        Animation myanim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        Animation topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        start_splash.startAnimation(topAnim);
        start_text.startAnimation(myanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                finish();
            }
        },3000);


    }
}