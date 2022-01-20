package com.example.posrudyproject.ui.laporan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.adapter.ProdukTerlarisAdapter;
import com.example.posrudyproject.ui.laporan.model.ProdukTerlarisItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.ArrayList;
import java.util.List;

public class RangkumanLaporanActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    SwipeRefreshLayout mSwipeRefresh;
    AppCompatTextView produkTerlarisEmpty, kategoriTerlarisEmpty, tipeTerlarisEmpty, transaksiEmpty;
    RecyclerView rvProdukTerlaris, rvKategoriTerlaris, rvTipeTerlaris, rvTransaksiTerakhir;
    MaterialButton btnDetailProduk, btnDetailKategori, btnDetailTipe, btnDetailTotalTransaksi, btnDetailTransaksiTerakhir;
    private ConstraintLayout btnPilihPeriode;
    private AppCompatTextView mSelectedPeriod;

    List<ProdukTerlarisItem> produkTerlarisItems;
    ProdukTerlarisAdapter produkTerlarisAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rangkuman_laporan);

        //INIT VIEW
        initComponent();

        initToolbar();

        //SWIPE REFRESH LAYOUT
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefresh.setRefreshing(false);
            }
        });

        //Material Date Picker
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Pilih Periode");
        final MaterialDatePicker materialDatePicker = builder.build();

        btnPilihPeriode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                mSelectedPeriod.setText(materialDatePicker.getHeaderText()); //SET PICKER
            }
        });

        //DATA PRODUK TERLARIS LIST
        produkTerlarisItems = new ArrayList<>();
        produkTerlarisItems.add(new ProdukTerlarisItem("1. Mandarin Fade/Coral Matte - RP Optics Multilaser Red"));
        produkTerlarisItems.add(new ProdukTerlarisItem("2. Black Matte - ImpactX Photochromic 2 Laser Purple"));
        produkTerlarisItems.add(new ProdukTerlarisItem("3. Fire Red Matte - RP Optics Smoke Black"));
        produkTerlarisItems.add(new ProdukTerlarisItem("4. Pacific Blue (Matte) - RP Optics Multilaser Ice"));
        produkTerlarisItems.add(new ProdukTerlarisItem("5. Bronze Fade / Black Matte - RP Optics Smoke Black"));

        //SET ADAPTER PRODUK TERLARIS
        produkTerlarisAdapter = new ProdukTerlarisAdapter(produkTerlarisItems, this);
        rvProdukTerlaris.setLayoutManager(new LinearLayoutManager(this));
        rvProdukTerlaris.setAdapter(produkTerlarisAdapter);
        rvProdukTerlaris.setHasFixedSize(true);

        //JIKA ADA PRODUK TERLARIS, TEXT EMPTY HILANG
        if (produkTerlarisAdapter.getItemCount() > 0){
            produkTerlarisEmpty.setVisibility(View.GONE);
        }
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_rangkuman_laporan);
        mSwipeRefresh = findViewById(R.id.swipe_refresh_rangkuman);
        btnPilihPeriode = findViewById(R.id.btn_pilih_periode);
        mSelectedPeriod = findViewById(R.id.tv_selected_period);
        produkTerlarisEmpty = findViewById(R.id.tv_no_data_produk);
        kategoriTerlarisEmpty = findViewById(R.id.tv_no_data_kategori);
        tipeTerlarisEmpty = findViewById(R.id.tv_no_data_tipe);
        transaksiEmpty = findViewById(R.id.tv_no_data_transaksi);
        rvProdukTerlaris = findViewById(R.id.rv_produk_terlaris_rangkuman);
        rvKategoriTerlaris = findViewById(R.id.rv_kategori_terlaris_rangkuman);
        rvTipeTerlaris = findViewById(R.id.rv_tipe_terlaris_rangkuman);
        rvTransaksiTerakhir = findViewById(R.id.rv_transaksi_terakhir_rangkuman);
        btnDetailProduk = findViewById(R.id.btn_lihat_detail_produk);
        btnDetailKategori = findViewById(R.id.btn_lihat_detail_kategori);
        btnDetailTipe = findViewById(R.id.btn_lihat_detail_tipe);
        btnDetailTotalTransaksi = findViewById(R.id.btn_lihat_detail_total_transaksi);
        btnDetailTransaksiTerakhir = findViewById(R.id.btn_lihat_detail_transaksi_terakhir);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

}