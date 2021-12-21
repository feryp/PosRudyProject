package com.example.posrudyproject.ui.penyimpanan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penyimpanan.adapter.ViewPagerMenuAdapter;
import com.example.posrudyproject.ui.penyimpanan.fragment.BarangKeluarFragment;
import com.example.posrudyproject.ui.penyimpanan.fragment.BarangMasukFragment;
import com.example.posrudyproject.ui.penyimpanan.fragment.BarangPindahFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

public class RiwayatPenyimpananActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerMenuAdapter adapter;

    int betweenSpace = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_penyimpanan);

        //INIT VIEW
        initComponent();

        initToolbar();

        //initialize adapter
        adapter = new ViewPagerMenuAdapter(getSupportFragmentManager());
        //add fragment
        adapter.AddFragment(new BarangMasukFragment(), "Barang Masuk");
        adapter.AddFragment(new BarangKeluarFragment(), "Barang Keluar");
        adapter.AddFragment(new BarangPindahFragment(), "Barang Pindah");
        //set adapter
        viewPager.setAdapter(adapter);
        //connect tab layout with view pager
        tabLayout.setupWithViewPager(viewPager);

        //Setting margin tab layout
        ViewGroup slidingTabStrip = (ViewGroup) tabLayout.getChildAt(0);
        for (int i=0; i<slidingTabStrip.getChildCount()-1; i++){
            View v = slidingTabStrip.getChildAt(i);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            params.rightMargin = betweenSpace;
        }


    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_riwayat_barang);
        tabLayout = findViewById(R.id.tab_menu_kategori_barang);
        viewPager = findViewById(R.id.vp_riwayat_barang);
    }

}