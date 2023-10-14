package com.rehman.newtrend.MainFragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rehman.newtrend.Adapter.SafeImageSliderAdapter;
import com.rehman.newtrend.R;

import java.util.Timer;
import java.util.TimerTask;


public class SafeMainFragment extends Fragment {

    ViewPager mviewPager;
    SafeImageSliderAdapter adapter;
    TextView[] dots;
    LinearLayout makeup_layout,massage_layout,salon_layout,careCenter_layout,grocery_layout,tutor_layout;
    RelativeLayout slider_layout1,slider_layout2,slider_layout3,slider_layout4;
    String slider1Color = "#C2F0F6";
    String slider2Color = "#ECCBCB";
    String slider3Color = "#A5EDA8";
    String slider4Color = "#B1CEE4";
    ProgressDialog progressDialog;

    public SafeMainFragment(String id) {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (progressDialog!=null)
        {
            progressDialog.dismiss();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_safe_main, container, false);

        initViewField(view);
        sliderCode();

        return view;
    }

    private void sliderCode() {
        adapter = new SafeImageSliderAdapter(getActivity());
        mviewPager.setAdapter(adapter);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 2000);
    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            getActivity().runOnUiThread(new Runnable()
            {
                @Override
                public void run() {

                    if(mviewPager.getCurrentItem() == 0)
                    {
                        mviewPager.setCurrentItem(1);
                        slider_layout1.setBackground(new ColorDrawable(Color.parseColor(slider2Color)));
                        slider_layout2.setBackground(new ColorDrawable(Color.parseColor(slider3Color)));
                        slider_layout3.setBackground(new ColorDrawable(Color.parseColor(slider4Color)));
                        slider_layout4.setBackground(new ColorDrawable(Color.parseColor(slider1Color)));
                    }
                    else if(mviewPager.getCurrentItem() == 1)
                    {
                        mviewPager.setCurrentItem(2);
                        slider_layout1.setBackground(new ColorDrawable(Color.parseColor(slider3Color)));
                        slider_layout2.setBackground(new ColorDrawable(Color.parseColor(slider4Color)));
                        slider_layout3.setBackground(new ColorDrawable(Color.parseColor(slider1Color)));
                        slider_layout4.setBackground(new ColorDrawable(Color.parseColor(slider2Color)));
                    }

                    else if (mviewPager.getCurrentItem() == 2)
                    {
                        mviewPager.setCurrentItem(3);
                        slider_layout1.setBackground(new ColorDrawable(Color.parseColor(slider4Color)));
                        slider_layout2.setBackground(new ColorDrawable(Color.parseColor(slider1Color)));
                        slider_layout3.setBackground(new ColorDrawable(Color.parseColor(slider2Color)));
                        slider_layout4.setBackground(new ColorDrawable(Color.parseColor(slider3Color)));
                    }

                    else {
                        mviewPager.setCurrentItem(0);
                        slider_layout1.setBackground(new ColorDrawable(Color.parseColor(slider1Color)));
                        slider_layout2.setBackground(new ColorDrawable(Color.parseColor(slider2Color)));
                        slider_layout3.setBackground(new ColorDrawable(Color.parseColor(slider3Color)));
                        slider_layout4.setBackground(new ColorDrawable(Color.parseColor(slider4Color)));
                    }
                }
            });

        }
    }

    private void initViewField(View view) {
        mviewPager = view.findViewById(R.id.slideViewPager);
        slider_layout1 = view.findViewById(R.id.slider_layout1);
        slider_layout2 = view.findViewById(R.id.slider_layout2);
        slider_layout3 = view.findViewById(R.id.slider_layout3);
        slider_layout4 = view.findViewById(R.id.slider_layout4);
    }
}