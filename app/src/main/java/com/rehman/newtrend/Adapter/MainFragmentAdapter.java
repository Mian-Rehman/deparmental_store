package com.rehman.newtrend.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.rehman.newtrend.MainFragment.HelpFragment;
import com.rehman.newtrend.MainFragment.MyOrderFragment;
import com.rehman.newtrend.MainFragment.ProfileMainFragment;
import com.rehman.newtrend.MainFragment.SafeMainFragment;
import com.rehman.newtrend.MainFragment.StoreFragment;

public class MainFragmentAdapter extends FragmentStateAdapter
{
    String id = "";

    public MainFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public MainFragmentAdapter(@NonNull FragmentActivity fragmentActivity,String id) {
        super(fragmentActivity);
        this.id = id;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;

        switch (position)
        {
            case 0:
                fragment = new MyOrderFragment(id);
                break;
            case 1:
                fragment = new HelpFragment(id);
                break;
            case 2:
                fragment = new StoreFragment(id);
                break;
            case 3:
                fragment = new SafeMainFragment(id);
                break;

            case 4:
                fragment = new ProfileMainFragment(id);
                break;
        }



        return fragment;
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
