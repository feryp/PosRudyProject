package com.example.posrudyproject.ui.akun.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.akun.adapter.PagerRiwayatTransaksiAdapter;
import com.example.posrudyproject.ui.akun.fragment.RiwayatProsesFragment;
import com.example.posrudyproject.ui.akun.fragment.RiwayatSelesaiFragment;
import com.example.posrudyproject.ui.main.MainActivity;
import com.example.posrudyproject.ui.penjualan.activity.KategoriActivity;
import com.example.posrudyproject.ui.penjualan.activity.PenjualanActivity;
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
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RiwayatTransaksiActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_riwayata_transaksi);
        tabLayout = findViewById(R.id.tab_riwayat_transaksi);
        viewPager = findViewById(R.id.vp_riwayat_transaksi);
    }
}