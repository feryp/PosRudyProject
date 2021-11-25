package com.example.posrudyproject.ui.pembayaran.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerPembayaranAdapter extends FragmentStateAdapter {

    public final List<Fragment> mFragment = new ArrayList<>();
    public final List<String> mFragmetnTitle = new ArrayList<>();

    public ViewPagerPembayaranAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public void addFragment(Fragment fragment, String title){
        mFragment.add(fragment);
        mFragmetnTitle.add(title);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragment.size();
    }
}
