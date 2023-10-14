package com.rehman.newtrend.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.rehman.newtrend.R;

public class SafeImageSliderAdapter extends PagerAdapter
{
    LayoutInflater inflater;
    Context context;

    public int imgs[] =
            {

                    R.drawable.safe_banner_one,
                    R.drawable.safe_banner_two,
                    R.drawable.safe_banner_three,
                    R.drawable.safe_banner_four,

            };

    public SafeImageSliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return  view == object;
    }

    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =  inflater.inflate(R.layout.safe_slider_item,container,false);
        ImageView imageView = view.findViewById(R.id.image_slider);
        imageView.setImageResource(imgs[position]);

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
