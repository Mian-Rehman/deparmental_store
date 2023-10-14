package com.rehman.newtrend.MainFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rehman.newtrend.Adapter.OrdersTabsAdapter;
import com.rehman.newtrend.R;


public class MyOrderFragment extends Fragment {

    ViewPager2 booking_viewpager;
    LinearLayout booking_pending_layout,booking_cancel_layout,booking_success_layout;
    TextView booking_pending_text,booking_cancel_text,booking_success_text;
    View booking_pending_view,booking_cancel_view,booking_success_view;

    OrdersTabsAdapter adapter;
    Context context;


    public MyOrderFragment(String id) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);

        initViewField(view);



        adapter = new OrdersTabsAdapter(getActivity());
        booking_viewpager.setUserInputEnabled(true);
        booking_viewpager.setAdapter(adapter);

        booking_viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                onChangeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        booking_pending_text.setTextColor(ContextCompat.getColor(getActivity(),R.color.selected_font_color));
        booking_pending_view.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.selected_font_color,getActivity().getTheme()));

        booking_cancel_text.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray));
        booking_cancel_view.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.gray,getActivity().getTheme()));

        booking_success_text.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray));
        booking_success_view.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.gray,getActivity().getTheme()));

        booking_pending_layout.setOnClickListener(v -> {
            booking_viewpager.setCurrentItem(0,true);
        });

        booking_cancel_layout.setOnClickListener(v -> {
            booking_viewpager.setCurrentItem(1,true);
        });

        booking_success_layout.setOnClickListener(v -> {
            booking_viewpager.setCurrentItem(2,true);
        });

        return view;
    }

    private void onChangeTab(int position)
    {
        if (position == 0)
        {
            booking_pending_text.setTextColor(ContextCompat.getColor(getActivity(),R.color.selected_font_color));
            booking_pending_view.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.selected_font_color,getActivity().getTheme()));

            booking_cancel_text.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray));
            booking_cancel_view.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.gray,getActivity().getTheme()));

            booking_success_text.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray));
            booking_success_view.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.gray,getActivity().getTheme()));
        }

        if (position == 1)
        {
            booking_pending_text.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray));
            booking_pending_view.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.gray,getActivity().getTheme()));

            booking_cancel_text.setTextColor(ContextCompat.getColor(getActivity(),R.color.selected_font_color));
            booking_cancel_view.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.selected_font_color,getActivity().getTheme()));

            booking_success_text.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray));
            booking_success_view.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.gray,getActivity().getTheme()));
        }

        if (position == 2)
        {
            booking_pending_text.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray));
            booking_pending_view.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.gray,getActivity().getTheme()));

            booking_cancel_text.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray));
            booking_cancel_view.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.gray,getActivity().getTheme()));

            booking_success_text.setTextColor(ContextCompat.getColor(getActivity(),R.color.selected_font_color));
            booking_success_view.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.selected_font_color,getActivity().getTheme()));
        }
    }

    private void initViewField(View view)
    {
        booking_viewpager = view.findViewById(R.id.booking_viewpager);
        booking_pending_layout = view.findViewById(R.id.booking_pending_layout);
        booking_cancel_layout = view.findViewById(R.id.booking_cancel_layout);
        booking_success_layout = view.findViewById(R.id.booking_success_layout);
        booking_cancel_text = view.findViewById(R.id.booking_cancel_text);
        booking_pending_text = view.findViewById(R.id.booking_pending_text);
        booking_success_text = view.findViewById(R.id.booking_success_text);
        booking_pending_view = view.findViewById(R.id.booking_pending_view);
        booking_cancel_view = view.findViewById(R.id.booking_cancel_view);
        booking_success_view = view.findViewById(R.id.booking_success_view);
    }
}