package com.example.posrudyproject.ui.diskon.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.diskon.adapter.PagerDiskonAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class BotSheetDiskonFragment extends BottomSheetDialogFragment {

    ImageButton btnClose;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    PagerDiskonAdapter adapter;
    private static BotSheetDiskonFragment instance = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bot_sheet_diskon, container, false);
        instance = this;
        //INIT VIEW
        btnClose = v.findViewById(R.id.btn_close_botsheet);
        tabLayout = v.findViewById(R.id.tablayout_diskon);
        viewPager = v.findViewById(R.id.vp_diskon);

        setupViewPager(viewPager);

        new TabLayoutMediator(tabLayout, viewPager,(tab, position) -> {tab.setText(adapter.mFragmentTitleList.get(position));
        }).attach();

        for (int i = 0; i < tabLayout.getTabCount(); i++){
            @SuppressLint("InflateParams")
            AppCompatTextView tv = (AppCompatTextView) LayoutInflater.from(getActivity()).inflate(R.layout.tab_custom, null);
            tabLayout.getTabAt(i).setCustomView(tv);
        }

        return v;
    }

    private void setupViewPager(ViewPager2 viewPager) {
        adapter = new PagerDiskonAdapter(getActivity().getSupportFragmentManager(),
                getActivity().getLifecycle());
        adapter.addFragment(new PercentFragment(), "%");
        adapter.addFragment(new RupiahFragment(), "Rp");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    public static BotSheetDiskonFragment getInstance() {
        return instance;
    }

    public void DismissParent () {
        dismiss();
    }
}