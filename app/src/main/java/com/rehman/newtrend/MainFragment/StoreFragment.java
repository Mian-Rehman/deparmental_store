package com.rehman.newtrend.MainFragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rehman.newtrend.Adapter.MainBannerAdapter;
import com.rehman.newtrend.Adapter.SmartWatchAdapter;
import com.rehman.newtrend.Adapter.TagsAdapter;
import com.rehman.newtrend.AdapterProduct.CableAdapter;
import com.rehman.newtrend.AdapterProduct.GadgetAdapter;
import com.rehman.newtrend.AdapterProduct.HandfreeAdapter;
import com.rehman.newtrend.AdapterProduct.MenAdapter;
import com.rehman.newtrend.AdapterProduct.MobileAdapter;
import com.rehman.newtrend.AdapterProduct.RingsAdapter;
import com.rehman.newtrend.AdapterProduct.WatchAdapter;
import com.rehman.newtrend.AdapterProduct.WomenFashionAdapter;
import com.rehman.newtrend.AdapterProduct.WoodClockAdapter;
import com.rehman.newtrend.Cart.CartActivity;
import com.rehman.newtrend.Model.ProductDetailsModel;
import com.rehman.newtrend.Model.Tags;
import com.rehman.newtrend.Product.ViewAllProductActivity;
import com.rehman.newtrend.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class StoreFragment extends Fragment implements View.OnClickListener{

    private InterstitialAd mInterstitialAd,mInterstitialAd2,mInterstitialAd3,mInterstitialAd4,mInterstitialAd5
            ,mInterstitialAd6,mInterstitialAd7,mInterstitialAd8,mInterstitialAd9,mInterstitialAd10;


    SwipeRefreshLayout refreshLayout;
    TextView search_tags;
    ArrayList<Tags> tagsArrayList;
    TagsAdapter tagsAdapter;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ImageView middle_banner_image;

    RecyclerView rcvBanner;
    ProgressDialog progressDialog;
    ScrollView home_scrollView;
    Timer timer;
    List<String> urls;
    List<String> names;
    TimerTask timerTask;
    int position;
    LinearLayoutManager layoutManager;
    String banner1,banner2,banner3,banner4;
    LinearLayout cart_layout;

    LinearLayoutManager linearLayoutManager,watchLinearLayout,menLinearLayout,handFreeLinearLayout
            ,gadgetLinearLayout,womanFashionLinear,woodLinearLayout,ringsLinearLayout
            ,cableLinearLayout,mobileLinearLayout;
    RecyclerView smartWatch_recycleView,watch_recycleView,menTrouser_recycleView
            ,handfree_recycleView,gadgets_recycleView,womenFashion_recycleView
            ,woodClock_recycleView,rings_recycleView,cables_recycleView
            ,mobile_recycleView;

    TextView makeUp_viewAll,watch_viewAll,makeupHead_text,watchHead_text,menHead_text,men_viewAll
            ,handfreeHead_text,handfree_viewAll,gadgetsHead_text,gadgets_viewAll,womenFashion_viewAll
            ,womenFashionHead_text,woodClock_viewAll,woodClockHead_text,ringsHead_text,rings_viewAll
            ,cables_viewAll,cablesHead_text,mobile_viewAll,mobileHead_text;
    String viewText,offerBannerUrl;

    MenAdapter menAdapter;
    SmartWatchAdapter adapter;
    WatchAdapter watchAdapter;
    HandfreeAdapter handfreeAdapter;
    GadgetAdapter gadgetAdapter;
    WomenFashionAdapter fashionAdapter;
    WoodClockAdapter clockAdapter;
    RingsAdapter ringsAdapter;
    CableAdapter cableAdapter;
    MobileAdapter mobileAdapter;
    ArrayList<ProductDetailsModel> mSmartWatchList;
    ArrayList<ProductDetailsModel> mWatchList;
    ArrayList<ProductDetailsModel> mMenList;
    ArrayList<ProductDetailsModel> mHandfreeList;
    ArrayList<ProductDetailsModel> mGadgetList;
    ArrayList<ProductDetailsModel> mFashionList;
    ArrayList<ProductDetailsModel> mClockList;
    ArrayList<ProductDetailsModel> mRingsList;
    ArrayList<ProductDetailsModel> mCablesList;
    ArrayList<ProductDetailsModel> mMobileList;

    @Override
    public void onResume() {
        super.onResume();
        if (progressDialog!=null)
        {
            progressDialog.dismiss();
        }
    }

    public StoreFragment(String id) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        progressDialog = ProgressDialog.show(getActivity(), "", "Processing", true);
        initViews(view);


        smartWatchData();
        watchGetValue();
        menTrouserValue();
        handFreeValue();
        gadgetValue();
        womenFasionValue();
        clockValue();
        ringsValue();
        CableValue();
        mobileValue();

        getBannerUrls();

        cart_layout.setVisibility(View.GONE);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("categoryTags");

        initializeRefreshListener();

        //tags Code
        search_tags.setOnClickListener(v -> {
            Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.tag_dialog);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCancelable(false);
            dialog.show();

            tagsArrayList = new ArrayList<>();
            ImageView close = dialog.findViewById(R.id.close);
            RecyclerView recyclerView = dialog.findViewById(R.id.tag_recycler_view);
            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity());
            layoutManager.setFlexDirection(FlexDirection.ROW);
            layoutManager.setJustifyContent(JustifyContent.FLEX_START);
            recyclerView.setLayoutManager(layoutManager);
            tagsAdapter = new TagsAdapter(getActivity(),tagsArrayList);
            recyclerView.setAdapter(tagsAdapter);

            myRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Tags tags = snapshot.getValue(Tags.class);
                    tagsArrayList.add(tags);
                    tagsAdapter.notifyDataSetChanged();
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

            close.setOnClickListener(view1 -> dialog.dismiss());

            EditText search = dialog.findViewById(R.id.search);
            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable != null && !editable.equals("")) {
                        ArrayList<Tags> newTagsList = new ArrayList<>();
                        for (Tags tags : tagsArrayList) {
                            if (tags.getName().toLowerCase().contains(editable)) {
                                newTagsList.add(tags);
                            }
                        }
                        if (newTagsList.size() == 0) {
                            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity());
                            layoutManager.setFlexDirection(FlexDirection.ROW);
                            layoutManager.setJustifyContent(JustifyContent.FLEX_START);
                            tagsAdapter = new TagsAdapter(getActivity(), tagsArrayList);
                        } else {
                            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity());
                            layoutManager.setFlexDirection(FlexDirection.ROW);
                            layoutManager.setJustifyContent(JustifyContent.FLEX_START);
                            tagsAdapter = new TagsAdapter(getActivity(), newTagsList);
                        }

                    } else {
                    }
                    recyclerView.setAdapter(tagsAdapter);
                    tagsAdapter.notifyDataSetChanged();
                }
            });
        });

        makeUp_viewAll.setOnClickListener(this::onClick);
        watch_viewAll.setOnClickListener(this::onClick);
        men_viewAll.setOnClickListener(this::onClick);
        handfree_viewAll.setOnClickListener(this::onClick);
        gadgets_viewAll.setOnClickListener(this::onClick);
        womenFashion_viewAll.setOnClickListener(this::onClick);
        woodClock_viewAll.setOnClickListener(this::onClick);
        rings_viewAll.setOnClickListener(this::onClick);
        cables_viewAll.setOnClickListener(this::onClick);
        mobile_viewAll.setOnClickListener(this::onClick);
        cart_layout.setOnClickListener(this::onClick);


//        AdRequest adRequest = new AdRequest.Builder().build();
//        InterstitialAd.load(getActivity(), "ca-app-pub-3940256099942544/1033173712", adRequest,
//                new InterstitialAdLoadCallback() {
//                    @Override
//                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                        // The mInterstitialAd reference will be null until
//                        // an ad is loaded.
//                        mInterstitialAd = interstitialAd;
//                        Log.i("TAG", "onAdLoaded");
//                    }
//
//                    @Override
//                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                        // Handle the error
//                        Log.i("TAG", loadAdError.getMessage());
//                        super.onAdFailedToLoad(loadAdError);
//                        mInterstitialAd = null;
//                    }
//                });

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        switch (id)
        {
            case R.id.cart_layout:
                startActivity(new Intent(getActivity(), CartActivity.class));
                break;

            case R.id.makeUp_viewAll:
                makeUpAds();
                viewText  = makeupHead_text.getText().toString().trim();


                break;

            case R.id.watch_viewAll:
                watchAds();
                viewText  = watchHead_text.getText().toString().trim();

                break;

            case R.id.men_viewAll:
                menAds();
                viewText  = menHead_text.getText().toString().trim();

                break;

            case R.id.handfree_viewAll:
                handfreeAds();
                viewText  = handfreeHead_text.getText().toString().trim();
                break;

            case R.id.gadgets_viewAll:
                gadetsAds();
                viewText  = gadgetsHead_text.getText().toString().trim();
                break;

            case R.id.womenFashion_viewAll:
                womenFashionAds();
                viewText  = womenFashionHead_text.getText().toString().trim();
                break;

            case R.id.woodClock_viewAll:
                cloclAds();
                viewText  = woodClockHead_text.getText().toString().trim();
                break;

            case R.id.rings_viewAll:
                ringsAds();
                viewText  = ringsHead_text.getText().toString().trim();
                break;

            case R.id.cables_viewAll:
                cableAds();
                viewText  = cablesHead_text.getText().toString().trim();
                break;

            case R.id.mobile_viewAll:
                mobileAds();
                viewText  = mobileHead_text.getText().toString().trim();
                break;


        }

    }

    private void mobileAds()
    {
        mInterstitialAd10 = new InterstitialAd(getActivity());
        mInterstitialAd10.setAdUnitId("ca-app-pub-9365087916546187/4077900059");
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        AdRequest adRequest10 = new AdRequest.Builder().build();
        mInterstitialAd10.loadAd(adRequest10);
        mInterstitialAd10.setAdListener(new AdListener(){

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Intent mobileIntent = new Intent(getActivity(), ViewAllProductActivity.class);
                mobileIntent.putExtra("productCategory",viewText);
                startActivity(mobileIntent);
                if (mInterstitialAd10 != null) {
                    mInterstitialAd10.show();
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mInterstitialAd10.loadAd(adRequest10);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });
    }

    private void cableAds()
    {
        mInterstitialAd9 = new InterstitialAd(getActivity());
        mInterstitialAd9.setAdUnitId("ca-app-pub-9365087916546187/4077900059");
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        AdRequest adRequest9 = new AdRequest.Builder().build();
        mInterstitialAd9.loadAd(adRequest9);
        mInterstitialAd9.setAdListener(new AdListener(){

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Intent cableIntent = new Intent(getActivity(), ViewAllProductActivity.class);
                cableIntent.putExtra("productCategory",viewText);
                startActivity(cableIntent);
                if (mInterstitialAd9 != null) {
                    mInterstitialAd9.show();
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mInterstitialAd9.loadAd(adRequest9);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });
    }

    private void ringsAds()
    {
        mInterstitialAd8 = new InterstitialAd(getActivity());
        mInterstitialAd8.setAdUnitId("ca-app-pub-9365087916546187/1643308400");
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        AdRequest adRequest8 = new AdRequest.Builder().build();
        mInterstitialAd8.loadAd(adRequest8);
        mInterstitialAd8.setAdListener(new AdListener(){

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Intent ringsIntent = new Intent(getActivity(), ViewAllProductActivity.class);
                ringsIntent.putExtra("productCategory",viewText);
                startActivity(ringsIntent);
                if (mInterstitialAd8 != null) {
                    mInterstitialAd8.show();
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mInterstitialAd8.loadAd(adRequest8);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });
    }

    private void cloclAds()
    {
        mInterstitialAd7 = new InterstitialAd(getActivity());
        mInterstitialAd7.setAdUnitId("ca-app-pub-9365087916546187/8208716759");
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        AdRequest adRequest7 = new AdRequest.Builder().build();
        mInterstitialAd7.loadAd(adRequest7);
        mInterstitialAd7.setAdListener(new AdListener(){

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Intent woodIntent = new Intent(getActivity(), ViewAllProductActivity.class);
                woodIntent.putExtra("productCategory",viewText);
                startActivity(woodIntent);
                if (mInterstitialAd7 != null) {
                    mInterstitialAd7.show();
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mInterstitialAd7.loadAd(adRequest7);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });
    }

    private void womenFashionAds()
    {
        mInterstitialAd6 = new InterstitialAd(getActivity());
        mInterstitialAd6.setAdUnitId("ca-app-pub-9365087916546187/2958854533");
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        AdRequest adRequest6 = new AdRequest.Builder().build();
        mInterstitialAd6.loadAd(adRequest6);
        mInterstitialAd6.setAdListener(new AdListener(){

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Intent woIntent = new Intent(getActivity(), ViewAllProductActivity.class);
                woIntent.putExtra("productCategory",viewText);
                startActivity(woIntent);

                if (mInterstitialAd6 != null) {
                    mInterstitialAd6.show();
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mInterstitialAd6.loadAd(adRequest6);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });
    }

    private void gadetsAds()
    {
        mInterstitialAd5 = new InterstitialAd(getActivity());
        mInterstitialAd5.setAdUnitId("ca-app-pub-9365087916546187/9904941809");
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        AdRequest adRequest5 = new AdRequest.Builder().build();
        mInterstitialAd5.loadAd(adRequest5);

        mInterstitialAd5.setAdListener(new AdListener(){

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Intent gadIntent = new Intent(getActivity(), ViewAllProductActivity.class);
                gadIntent.putExtra("productCategory",viewText);
                startActivity(gadIntent);

                if (mInterstitialAd5 != null) {
                    mInterstitialAd5.show();
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mInterstitialAd5.loadAd(adRequest5);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }


        });
    }

    private void handfreeAds()
    {
        mInterstitialAd4 = new InterstitialAd(getActivity());
        mInterstitialAd4.setAdUnitId("ca-app-pub-9365087916546187/5035758508");
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        AdRequest adRequest4 = new AdRequest.Builder().build();
        mInterstitialAd4.loadAd(adRequest4);

        mInterstitialAd4.setAdListener(new AdListener(){

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Intent handFreeIntent = new Intent(getActivity(), ViewAllProductActivity.class);
                handFreeIntent.putExtra("productCategory",viewText);
                startActivity(handFreeIntent);

                if (mInterstitialAd4 != null) {
                    mInterstitialAd4.show();
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mInterstitialAd4.loadAd(adRequest4);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }


        });
    }

    private void menAds()
    {
        mInterstitialAd3 = new InterstitialAd(getActivity());
        mInterstitialAd3.setAdUnitId("ca-app-pub-9365087916546187/8211181211");
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        AdRequest adRequest3 = new AdRequest.Builder().build();
        mInterstitialAd3.loadAd(adRequest3);

        mInterstitialAd3.setAdListener(new AdListener(){

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Intent menIntent = new Intent(getActivity(), ViewAllProductActivity.class);
                menIntent.putExtra("productCategory",viewText);
                startActivity(menIntent);

                if (mInterstitialAd3 != null) {
                    mInterstitialAd3.show();
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mInterstitialAd3.loadAd(adRequest3);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }


        });
    }

    private void makeUpAds()
    {
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-9365087916546187/5802045264");
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Intent intent = new Intent(getActivity(), ViewAllProductActivity.class);
                intent.putExtra("productCategory",viewText);
                startActivity(intent);

                if (mInterstitialAd != null) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mInterstitialAd.loadAd(adRequest);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }


        });
    }

    private void watchAds()
    {
        mInterstitialAd2 = new InterstitialAd(getActivity());
        mInterstitialAd2.setAdUnitId("ca-app-pub-9365087916546187/4105820216");
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        AdRequest adRequest2 = new AdRequest.Builder().build();
        mInterstitialAd2.loadAd(adRequest2);

        mInterstitialAd2.setAdListener(new AdListener(){

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Intent watchIntent = new Intent(getActivity(), ViewAllProductActivity.class);
                watchIntent.putExtra("productCategory",viewText);
                startActivity(watchIntent);
                if (mInterstitialAd2 != null) {
                    mInterstitialAd2.show();
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mInterstitialAd2.loadAd(adRequest2);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }


        });
    }

    private void mobileValue()
    {
        mMobileList = new ArrayList<>();
        mobileLinearLayout = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        mobileAdapter = new MobileAdapter(getActivity(),mMobileList);
        mobile_recycleView.setLayoutManager(mobileLinearLayout);
        mobile_recycleView.setAdapter(mobileAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products")
                .child("Data cable");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists())
                {
                    ProductDetailsModel model = snapshot.getValue(ProductDetailsModel.class);
                    mMobileList.add(model);
                    mobileAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
                else
                {
                    mobile_recycleView.setVisibility(View.GONE);
                    progressDialog.dismiss();
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

    private void CableValue()
    {
        mCablesList = new ArrayList<>();
        cableLinearLayout = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        cableAdapter = new CableAdapter(getActivity(),mCablesList);
        cables_recycleView.setLayoutManager(cableLinearLayout);
        cables_recycleView.setAdapter(cableAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products")
                .child("Data Cable");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists())
                {
                    ProductDetailsModel model = snapshot.getValue(ProductDetailsModel.class);
                    mCablesList.add(model);
                    cableAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
                else
                {
                    cables_recycleView.setVisibility(View.GONE);
                    progressDialog.dismiss();
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

    private void ringsValue()
    {
        mRingsList = new ArrayList<>();
        ringsLinearLayout = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        ringsAdapter = new RingsAdapter(getActivity(),mRingsList);
        rings_recycleView.setLayoutManager(ringsLinearLayout);
        rings_recycleView.setAdapter(ringsAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products")
                .child("Rings");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists())
                {
                    ProductDetailsModel model = snapshot.getValue(ProductDetailsModel.class);
                    mRingsList.add(model);
                    ringsAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
                else
                {
                    rings_recycleView.setVisibility(View.GONE);
                    progressDialog.dismiss();
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

    private void clockValue()
    {
        mClockList = new ArrayList<>();
        woodLinearLayout = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        clockAdapter = new WoodClockAdapter(getActivity(),mClockList);
        woodClock_recycleView.setLayoutManager(woodLinearLayout);
        woodClock_recycleView.setAdapter(clockAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products")
                .child("Wooden clock");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists())
                {
                    ProductDetailsModel model = snapshot.getValue(ProductDetailsModel.class);
                    mClockList.add(model);
                    clockAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
                else
                {
                    woodClock_recycleView.setVisibility(View.GONE);
                    progressDialog.dismiss();
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


    private void womenFasionValue()
    {
        mFashionList = new ArrayList<>();
        womanFashionLinear = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        fashionAdapter = new WomenFashionAdapter(getActivity(),mFashionList);
        womenFashion_recycleView.setLayoutManager(womanFashionLinear);
        womenFashion_recycleView.setAdapter(fashionAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products")
                .child("Women Fashion");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists())
                {
                    ProductDetailsModel model = snapshot.getValue(ProductDetailsModel.class);
                    mFashionList.add(model);
                    fashionAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
                else
                {
                    womenFashion_recycleView.setVisibility(View.GONE);
                    progressDialog.dismiss();
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

    private void gadgetValue()
    {
        mGadgetList = new ArrayList<>();
        gadgetLinearLayout = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        gadgetAdapter = new GadgetAdapter(getActivity(),mGadgetList);
        gadgets_recycleView.setLayoutManager(gadgetLinearLayout);
        gadgets_recycleView.setAdapter(gadgetAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products")
                .child("Gadgets");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists())
                {
                    ProductDetailsModel model = snapshot.getValue(ProductDetailsModel.class);
                    mGadgetList.add(model);
                    gadgetAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
                else
                {
                    gadgets_recycleView.setVisibility(View.GONE);
                    progressDialog.dismiss();
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

    private void handFreeValue()
    {
        mHandfreeList = new ArrayList<>();
        handFreeLinearLayout = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        handfreeAdapter = new HandfreeAdapter(getActivity(),mHandfreeList);
        handfree_recycleView.setLayoutManager(handFreeLinearLayout);
        handfree_recycleView.setAdapter(handfreeAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products")
                .child("Handfree");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists())
                {
                    ProductDetailsModel model = snapshot.getValue(ProductDetailsModel.class);
                    mHandfreeList.add(model);
                    handfreeAdapter.notifyDataSetChanged();
                }
                else
                {
                    handfree_recycleView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists())
                {
                    ProductDetailsModel model = snapshot.getValue(ProductDetailsModel.class);
                    mHandfreeList.add(model);
                    handfreeAdapter.notifyDataSetChanged();
                }
                else
                {
                    handfree_recycleView.setVisibility(View.GONE);
                }
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

    private void menTrouserValue()
    {
        mMenList = new ArrayList<>();
        menLinearLayout = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        menAdapter = new MenAdapter(getActivity(),mMenList);
        menTrouser_recycleView.setLayoutManager(menLinearLayout);
        menTrouser_recycleView.setAdapter(menAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products")
                .child("Men Trouser");

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
                    menTrouser_recycleView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists())
                {
                    ProductDetailsModel model = snapshot.getValue(ProductDetailsModel.class);
                    mMenList.add(model);
                    menAdapter.notifyDataSetChanged();
                }
                else
                {
                    menTrouser_recycleView.setVisibility(View.GONE);
                }
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

    private void watchGetValue()
    {
        mWatchList = new ArrayList<>();
        watchLinearLayout = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        watchAdapter = new WatchAdapter(getActivity(),mWatchList);
        watch_recycleView.setLayoutManager(watchLinearLayout);
        watch_recycleView.setAdapter(watchAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products")
                .child("Smart Watches");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists())
                {
                    ProductDetailsModel model = snapshot.getValue(ProductDetailsModel.class);
                    mWatchList.add(model);
                    watchAdapter.notifyDataSetChanged();
                }
                else
                {
                    watch_recycleView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists())
                {
                    ProductDetailsModel model = snapshot.getValue(ProductDetailsModel.class);
                    mWatchList.add(model);
                    watchAdapter.notifyDataSetChanged();
                }
                else
                {
                    watch_recycleView.setVisibility(View.GONE);
                }
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

    private void smartWatchData()
    {

        mSmartWatchList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        adapter = new SmartWatchAdapter(getActivity(),mSmartWatchList);
        smartWatch_recycleView.setLayoutManager(linearLayoutManager);
        smartWatch_recycleView.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products")
                .child("Makeup");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists())
                {
                    ProductDetailsModel model = snapshot.getValue(ProductDetailsModel.class);
                    mSmartWatchList.add(model);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
                else
                {
                    smartWatch_recycleView.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists())
                {
                    ProductDetailsModel model = snapshot.getValue(ProductDetailsModel.class);
                    mSmartWatchList.add(model);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
                else
                {
                    smartWatch_recycleView.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
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


    private void initializeRefreshListener()
    {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh() {
                // This method gets called when user pull for refresh,
                // You can make your API call here,
                // We are using adding a delay for the moment
                if (refreshLayout.isRefreshing())
                {
                    refreshLayout.setRefreshing(false);
                }

            }
        });
    }

    private void MainSlider()
    {
        Glide.with(getActivity()).load(offerBannerUrl).into(middle_banner_image);
        getListurl();

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rcvBanner.setLayoutManager(layoutManager);

        MainBannerAdapter bannerAdapter = new MainBannerAdapter(getActivity(), urls,names);
        rcvBanner.setAdapter(bannerAdapter);

        if (urls != null) {
            position = Integer.MAX_VALUE / 2;
            rcvBanner.scrollToPosition(position);
        }

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rcvBanner);
        rcvBanner.smoothScrollBy(5, 0);

        rcvBanner.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == 1) {
                    stopAutoScrollBanner();
                } else if (newState == 0)
                {
                    position = layoutManager.findFirstCompletelyVisibleItemPosition();
                    runAutoScrollBanner();
                }
            }
        });
    }

    private void initViews(View view)
    {
        refreshLayout = view.findViewById(R.id.refreshLayout);
        home_scrollView = view.findViewById(R.id.home_scrollView);
        search_tags = view.findViewById(R.id.search_tags);
        rcvBanner = view.findViewById(R.id.rcvBanner);

        smartWatch_recycleView = view.findViewById(R.id.smartWatch_recycleView);
        watch_recycleView = view.findViewById(R.id.watch_recycleView);
        menTrouser_recycleView = view.findViewById(R.id.menTrouser_recycleView);
        makeUp_viewAll = view.findViewById(R.id.makeUp_viewAll);
        watch_viewAll = view.findViewById(R.id.watch_viewAll);
        makeupHead_text = view.findViewById(R.id.makeupHead_text);
        watchHead_text = view.findViewById(R.id.watchHead_text);
        men_viewAll = view.findViewById(R.id.men_viewAll);
        menHead_text = view.findViewById(R.id.menHead_text);
        handfreeHead_text = view.findViewById(R.id.handfreeHead_text);
        handfree_viewAll = view.findViewById(R.id.handfree_viewAll);

        handfree_recycleView = view.findViewById(R.id.handfree_recycleView);
        gadgets_recycleView = view.findViewById(R.id.gadgets_recycleView);
        gadgetsHead_text = view.findViewById(R.id.gadgetsHead_text);
        gadgets_viewAll = view.findViewById(R.id.gadgets_viewAll);
        womenFashion_recycleView = view.findViewById(R.id.womenFashion_recycleView);
        womenFashion_viewAll = view.findViewById(R.id.womenFashion_viewAll);
        womenFashionHead_text = view.findViewById(R.id.womenFashionHead_text);
        woodClock_recycleView = view.findViewById(R.id.woodClock_recycleView);
        woodClock_viewAll = view.findViewById(R.id.woodClock_viewAll);
        woodClockHead_text = view.findViewById(R.id.woodClockHead_text);
        ringsHead_text = view.findViewById(R.id.ringsHead_text);
        rings_viewAll = view.findViewById(R.id.rings_viewAll);
        rings_recycleView = view.findViewById(R.id.rings_recycleView);
        cables_recycleView = view.findViewById(R.id.cables_recycleView);
        cables_viewAll = view.findViewById(R.id.cables_viewAll);
        cablesHead_text = view.findViewById(R.id.cablesHead_text);
        middle_banner_image = view.findViewById(R.id.middle_banner_image);
        mobile_recycleView = view.findViewById(R.id.mobile_recycleView);
        mobile_viewAll = view.findViewById(R.id.mobile_viewAll);
        mobileHead_text = view.findViewById(R.id.mobileHead_text);
        cart_layout = view.findViewById(R.id.cart_layout);


    }

    private void stopAutoScrollBanner() {
        if (timer != null && timerTask != null) {
            timerTask.cancel();
            timer.cancel();
            timer = null;
            timerTask = null;
            position = layoutManager.findFirstCompletelyVisibleItemPosition();
        }
    }

    private void runAutoScrollBanner() {
        if (timer == null && timerTask == null) {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {

                    if (position == Integer.MAX_VALUE) {
                        position = Integer.MAX_VALUE / 2;
                        rcvBanner.scrollToPosition(position);

                        rcvBanner.smoothScrollBy(5, 0);
                    } else {
                        position++;
                        rcvBanner.smoothScrollToPosition(position);
                    }
                }
            };
            timer.schedule(timerTask, 4000, 4000);
        }
    }

    private void getBannerUrls()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("MainBannerUrls");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                    banner1 = snapshot.child("banner1").getValue(String.class);
                    banner2 = snapshot.child("banner2").getValue(String.class);
                    banner3 = snapshot.child("banner3").getValue(String.class);
                    banner4 = snapshot.child("banner4").getValue(String.class);
                    offerBannerUrl = snapshot.child("offerBanner").getValue(String.class);
                    MainSlider();


                }
                else
                {
                    banner1 = "https://thumbs.dreamstime.com/b/online-shopping-banner-business-concept-sale-e-commerce-smartphone-tiny-people-character-template-web-landing-147192696.jpg";
                    banner2 = "https://thumbs.dreamstime.com/b/online-shopping-banner-business-concept-sale-e-commerce-smartphone-tiny-people-character-template-web-landing-147192696.jpg";
                    banner3 = "https://thumbs.dreamstime.com/b/online-shopping-banner-business-concept-sale-e-commerce-smartphone-tiny-people-character-template-web-landing-147192696.jpg";
                    banner4 = "https://thumbs.dreamstime.com/b/online-shopping-banner-business-concept-sale-e-commerce-smartphone-tiny-people-character-template-web-landing-147192696.jpg";
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getListurl()
    {
        urls = new ArrayList<>();
        urls.add(banner1);
        urls.add(banner2);
        urls.add(banner3);
        urls.add(banner4);


        names = new ArrayList<>();
        names.add("Massage Parlor");
        names.add("Beauty Parlor");
        names.add("Old Age Care Center");
        names.add("Tutor");
    }


}