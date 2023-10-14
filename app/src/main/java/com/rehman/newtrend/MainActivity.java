package com.rehman.newtrend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import com.rehman.newtrend.Adapter.MainFragmentAdapter;
import com.rehman.newtrend.Utility.NetworkChangeListner;

public class MainActivity extends AppCompatActivity {



    ViewPager2 viewPager;
    MainFragmentAdapter adapter;

    MeowBottomNavigation bottomNavigation;
    private AdView mAdView;

    NetworkChangeListner networkChangeListner  = new NetworkChangeListner();

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListner,filter);

//        GPSLocationServiceCheck();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeListner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);

        adapter = new MainFragmentAdapter(this);
        viewPager.setUserInputEnabled(false);
        viewPager.setAdapter(adapter);

        Constant.loadRewardedAdd(this);


        bottomNavigation  = findViewById(R.id.bottom_tab_layout);
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_order));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_help));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_store));
        bottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.ic_sheild));
        bottomNavigation.add(new MeowBottomNavigation.Model(5,R.drawable.ic_profile));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

                switch (item.getId())
                {
                    case 1:
                        viewPager.setCurrentItem(0, true);
                        break;

                    case 2:
                        viewPager.setCurrentItem(1, true);
                        break;

                    case 3:
                        viewPager.setCurrentItem(2, true);
                        break;

                    case 4:
                        viewPager.setCurrentItem(3, true);
                        break;

                    case 5:
                        viewPager.setCurrentItem(4, true);
                        break;

                }
            }
        });
        bottomNavigation.show(3,true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
//                Toast.makeText(MainActivity.this, "You Clicked"+
//                        item.getId(), Toast.LENGTH_SHORT).show();

            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
//                Toast.makeText(MainActivity.this, "You reselect"+
//                        item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                super.onAdLoaded();
                Toast.makeText(MainActivity.this, "AD Loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError);
                mAdView.loadAd(adRequest);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                super.onAdOpened();

            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                super.onAdClicked();
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

    }
}