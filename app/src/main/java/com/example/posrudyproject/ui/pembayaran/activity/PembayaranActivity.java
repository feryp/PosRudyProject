package com.example.posrudyproject.ui.pembayaran.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
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

import java.text.DecimalFormat;

public class PembayaranActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    TabLayout tabLayout;
    private ViewPagerPembayaranAdapter adapter;
    ViewPager2 viewPager;
    AppCompatTextView tvTotalHargaPembayaran,tvKembalianPembayaran;
    public static final String INTENT_TUNAI_PAS = "INTENT_TUNAI_PAS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        //INIT VIEW
        initComponent();

        initToolbar();
        DecimalFormat decim = new DecimalFormat("#,###.##");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tvTotalHargaPembayaran.setText(("Rp").concat(decim.format(Double.valueOf(bundle.getString("total_harga","0.00").replace(",","")))));
        }

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
        tvTotalHargaPembayaran = findViewById(R.id.tv_total_harga_pembayaran);
        tvKembalianPembayaran = findViewById(R.id.tv_kembalian_pembayaran);
    }

    public String getTotal() {
        return tvTotalHargaPembayaran.getText().toString();
    }

    private BroadcastReceiver someBroadcastReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO extract extras from intent
            DecimalFormat decim = new DecimalFormat("#,###.##");
            if (intent.getStringExtra("uang_diterima").isEmpty()) {
                tvKembalianPembayaran.setText(decim.format(0.00));
            } else {
                tvKembalianPembayaran.setText(decim.format(Double.valueOf((intent.getStringExtra("uang_diterima").replace(",","")).replace("Rp","")) - Double.valueOf((tvTotalHargaPembayaran.getText().toString().replace("Rp","")).replace(",",""))));
            }
        }
    };
    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(someBroadcastReceiver,
                new IntentFilter(INTENT_TUNAI_PAS));
    }
    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(someBroadcastReceiver);
        super.onPause();
    }
}