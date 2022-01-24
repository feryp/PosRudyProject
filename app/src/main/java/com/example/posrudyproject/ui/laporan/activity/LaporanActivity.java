package com.example.posrudyproject.ui.laporan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.posrudyproject.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class LaporanActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialToolbar mToolbar;
    MaterialCardView laporanRangkuman, laporanPenjualanProduk, laporanPenjualanKategori , laporanPenjualanTipe, laporanPenjualanArtikel, laporanPelanggan, laporanPenjual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);

        //INIT VIEW
        initComponent();

        initToolbar();

        //SET LISTENER
        laporanRangkuman.setOnClickListener(this);
        laporanPenjualanProduk.setOnClickListener(this);
        laporanPenjualanKategori.setOnClickListener(this);
        laporanPenjualanTipe.setOnClickListener(this);
        laporanPenjualanArtikel.setOnClickListener(this);
        laporanPelanggan.setOnClickListener(this);
        laporanPenjual.setOnClickListener(this);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_laporan);
        laporanRangkuman = findViewById(R.id.menu_rangkuman_laporan);
        laporanPenjualanProduk = findViewById(R.id.menu_penjualan_produk_laporan);
        laporanPenjualanKategori = findViewById(R.id.menu_penjualan_kategori_laporan);
        laporanPenjualanTipe = findViewById(R.id.menu_penjualan_tipe_laporan);
        laporanPenjualanArtikel = findViewById(R.id.menu_penjualan_artikel_laporan);
        laporanPelanggan = findViewById(R.id.menu_pelanggan_laporan);
        laporanPenjual = findViewById(R.id.menu_penjual_laporan);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.menu_rangkuman_laporan:
                Intent rangkuman = new Intent(this, RangkumanLaporanActivity.class);
                startActivity(rangkuman);
                break;
            case R.id.menu_penjualan_produk_laporan:
                Intent penjualanProduk = new Intent(this, PenjualanPerProdukActivity.class);
                startActivity(penjualanProduk);
                break;
            case R.id.menu_penjualan_kategori_laporan:
                Intent penjualanKategori = new Intent(this, PenjualanPerKategoriActivity.class);
                startActivity(penjualanKategori);
                break;
        }
    }

}