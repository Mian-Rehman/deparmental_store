package com.rehman.newtrend.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.rehman.newtrend.OrderFiles.CancelOrderFrag;
import com.rehman.newtrend.OrderFiles.PendingOrderFrag;
import com.rehman.newtrend.OrderFiles.SuccessOrderFrag;

public class OrdersTabsAdapter extends FragmentStateAdapter
{


    public OrdersTabsAdapter(@NonNull FragmentActivity fragmentActivity)
    {
        super(fragmentActivity);
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Fragment fragment = null;
        switch (position)
        {
            case 0:
                fragment = new PendingOrderFrag();
                break;

            case 1:
                fragment = new CancelOrderFrag();
                break;

            case 2:
                fragment = new SuccessOrderFrag();
                break;
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
