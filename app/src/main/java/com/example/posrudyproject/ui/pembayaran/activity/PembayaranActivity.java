package com.example.posrudyproject.ui.pembayaran.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.keranjang.activity.KeranjangActivity;
import com.example.posrudyproject.ui.pembayaran.adapter.ViewPagerPembayaranAdapter;
import com.example.posrudyproject.ui.pembayaran.fragment.NonTunaiFragment;
import com.example.posrudyproject.ui.pembayaran.fragment.TunaiFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class PembayaranActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    TabLayout tabLayout;
    private ViewPagerPembayaranAdapter adapter;
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        //INIT VIEW
        initComponent();

        initToolbar();

        //set tablayout
        tabLayout.addTab(tabLayout.newTab().setText("Tunai"));
        tabLayout.addTab(tabLayout.newTab().setText("Non Tunai"));

        setupViewPager(viewPager);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {tab.setText(adapter.mFragmetnTitle.get(position));
        }).attach();
    }

    private void setupViewPager(ViewPager2 viewPager) {
        adapter = new ViewPagerPembayaranAdapter(this.getSupportFragmentManager(),
                this.getLifecycle());
        adapter.addFragment(new TunaiFragment(), "Tunai");
        adapter.addFragment(new NonTunaiFragment(), "Non Tunai");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_delete) {
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Konfirmasi Hapus")
                        .setMessage("Pesanan akan dihapus, Lanjutkan ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //function delete
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
                return true;
            }
            return false;
        });
    }

    private void initComponent() {
        //init
        mToolbar = findViewById(R.id.toolbar_pembayaran);
        tabLayout = findViewById(R.id.tab_pembayaran);
        viewPager = findViewById(R.id.vp_pembayaran);
    }
}