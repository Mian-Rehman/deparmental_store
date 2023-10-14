package com.rehman.newtrend;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class Constant
{
    public static RewardedAd rewardedAd;

    public static void loadRewardedAdd(Context context)
    {
        rewardedAd = new RewardedAd(context,"ca-app-pub-9365087916546187/3503184986");
        rewardedAd.loadAd(new AdRequest.Builder().build(),new RewardedAdLoadCallback(){

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError loadAdError) {
                super.onRewardedAdFailedToLoad(loadAdError);
                loadRewardedAdd(context);
            }
        });

    }
}
