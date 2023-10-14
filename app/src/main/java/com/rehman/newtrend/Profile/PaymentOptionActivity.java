package com.rehman.newtrend.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.rehman.newtrend.Constant;
import com.rehman.newtrend.Product.ProductDetailsActivity;
import com.rehman.newtrend.R;
import com.rehman.newtrend.Utility.ConstantPayment;

import org.w3c.dom.Text;

public class PaymentOptionActivity extends AppCompatActivity {

    ImageView back_image;

    TextView tv_coins,watch_Video;




    int count = 0;
    int newCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_option);
        initViews();

        back_image.setOnClickListener(v -> {
            onBackPressed();
        });

        if (ConstantPayment.rewardedAd.isLoaded()){
            ConstantPayment.rewardedAd.show(PaymentOptionActivity.this, new RewardedAdCallback() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                    Toast.makeText(PaymentOptionActivity.this, rewardItem.getAmount()+" Coins is your reward", Toast.LENGTH_SHORT).show();
                    newCount = rewardItem.getAmount();
                    count = count+newCount;
                    tv_coins.setText(String.valueOf("Coins: "+count));
                    ConstantPayment.loadRewardedAdd(PaymentOptionActivity.this);
                }
            });
        }
        else
        {
            Toast.makeText(this, "Ad not available", Toast.LENGTH_SHORT).show();
        }


        watch_Video.setOnClickListener(v -> {
            if (ConstantPayment.rewardedAd.isLoaded()){
                ConstantPayment.rewardedAd.show(PaymentOptionActivity.this, new RewardedAdCallback() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                        Toast.makeText(PaymentOptionActivity.this, rewardItem.getAmount()+" Coins is your reward", Toast.LENGTH_SHORT).show();
                        newCount = rewardItem.getAmount();
                        count = count+newCount;
                        tv_coins.setText(String.valueOf("Coins: "+count));
                        ConstantPayment.loadRewardedAdd(PaymentOptionActivity.this);
                    }
                });
            }
            else
            {
                Toast.makeText(this, "Ad not available", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initViews()
    {
        back_image = findViewById(R.id.back_image);
        tv_coins = findViewById(R.id.tv_coins);
        watch_Video = findViewById(R.id.watch_Video);
    }
}