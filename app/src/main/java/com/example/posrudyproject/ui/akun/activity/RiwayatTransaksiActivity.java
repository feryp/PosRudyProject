package com.example.posrudyproject.ui.akun.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.akun.adapter.PagerRiwayatTransaksiAdapter;
import com.example.posrudyproject.ui.akun.fragment.RiwayatProsesFragment;
import com.example.posrudyproject.ui.akun.fragment.RiwayatSelesaiFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

public class RiwayatTransaksiActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerRiwayatTransaksiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_transaksi);

        //INIT VIEW
        initComponent();

        initToolbar();

        //initialize adapter
        adapter = new PagerRiwayatTransaksiAdapter(getSupportFragmentManager());
        //add fragment
        adapter.AddFragment(new RiwayatProsesFragment(), "Proses");
        adapter.AddFragment(new RiwayatSelesaiFragment(), "Selesai");
        //set adapter
        viewPager.setAdapter(adapter);
        //connect tab layout with view pager
        tabLayout.setupWithViewPager(viewPager);

    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_riwayata_transaksi);
        tabLayout = findViewById(R.id.tab_riwayat_transaksi);
        viewPager = findViewById(R.id.vp_riwayat_transaksi);
    }
}