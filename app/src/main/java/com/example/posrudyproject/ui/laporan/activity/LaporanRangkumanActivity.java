package com.example.posrudyproject.ui.laporan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.adapter.KategoriTerlarisAdapter;
import com.example.posrudyproject.ui.laporan.adapter.ProdukTerlarisAdapter;
import com.example.posrudyproject.ui.laporan.adapter.TipeTerlarisAdapter;
import com.example.posrudyproject.ui.laporan.adapter.TransaksiTerakhirAdapter;
import com.example.posrudyproject.ui.laporan.model.KategoriTerlarisItem;
import com.example.posrudyproject.ui.laporan.model.ProdukTerlarisItem;
import com.example.posrudyproject.ui.laporan.model.TipeTerlarisItem;
import com.example.posrudyproject.ui.laporan.model.TransaksiTerakhirItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.ArrayList;
import java.util.List;

public class LaporanRangkumanActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialToolbar mToolbar;
    AppCompatTextView produkTerlarisEmpty, kategoriTerlarisEmpty, tipeTerlarisEmpty, transaksiEmpty;
    RecyclerView rvProdukTerlaris, rvKategoriTerlaris, rvTipeTerlaris, rvTransaksiTerakhir;
    MaterialButton btnDetailProduk, btnDetailKategori, btnDetailTipe, btnDetailTotalTransaksi, btnDetailTransaksiTerakhir;
    private ConstraintLayout btnPilihPeriode;
    private AppCompatTextView mSelectedPeriod;

    //PRODUK TERLARIS
    List<ProdukTerlarisItem> produkTerlarisItems;
    ProdukTerlarisAdapter produkTerlarisAdapter;

    //KATEGORI TERLARIS
    List<KategoriTerlarisItem> kategoriTerlarisItems;
    KategoriTerlarisAdapter kategoriTerlarisAdapter;

    //TIPE TERLARIS
    List<TipeTerlarisItem> tipeTerlarisItems;
    TipeTerlarisAdapter tipeTerlarisAdapter;

    //TRANSAKSI TERAKHIR
    List<TransaksiTerakhirItem> transaksiTerakhirItems;
    TransaksiTerakhirAdapter transaksiTerakhirAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_rangkuman);

        //INIT VIEW
        initComponent();

        initToolbar();

        //SET LISTENER
        btnDetailProduk.setOnClickListener(this);
        btnDetailKategori.setOnClickListener(this);
        btnDetailTipe.setOnClickListener(this);
        btnDetailTotalTransaksi.setOnClickListener(this);
        btnDetailTransaksiTerakhir.setOnClickListener(this);

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

        //DATA KATEGORI TERLARIS LIST
        kategoriTerlarisItems = new ArrayList<>();
        kategoriTerlarisItems.add(new KategoriTerlarisItem("1. EYEWEAR"));
        kategoriTerlarisItems.add(new KategoriTerlarisItem("2. HEALMETS"));
        kategoriTerlarisItems.add(new KategoriTerlarisItem("3. SPAREPART"));

        //SET ADAPTER KATEGORI TERLARIS
        kategoriTerlarisAdapter = new KategoriTerlarisAdapter(kategoriTerlarisItems, this);
        rvKategoriTerlaris.setLayoutManager(new LinearLayoutManager(this));
        rvKategoriTerlaris.setAdapter(kategoriTerlarisAdapter);
        rvKategoriTerlaris.setHasFixedSize(true);

        //JIKA ADA KATEGORI TERLARIS, TEXT EMPTY HILANG
        if (kategoriTerlarisAdapter.getItemCount() > 0){
            kategoriTerlarisEmpty.setVisibility(View.GONE);
        }

        //DATA TIPE TERLARIS LIST
        tipeTerlarisItems = new ArrayList<>();
        tipeTerlarisItems.add(new TipeTerlarisItem("1. CUTLINE"));
        tipeTerlarisItems.add(new TipeTerlarisItem("2. RYDON"));
        tipeTerlarisItems.add(new TipeTerlarisItem("3. DEFENDER"));

        //SET ADAPTER TIPE TERLARIS
        tipeTerlarisAdapter = new TipeTerlarisAdapter(tipeTerlarisItems, this);
        rvTipeTerlaris.setLayoutManager(new LinearLayoutManager(this));
        rvTipeTerlaris.setAdapter(tipeTerlarisAdapter);
        rvTipeTerlaris.setHasFixedSize(true);

        //JIKA ADA TIPE TERLARIS, TEXT EMPTY HILANG
        if (tipeTerlarisAdapter.getItemCount() > 0){
            tipeTerlarisEmpty.setVisibility(View.GONE);
        }

        //DATA TRANSAKSI TERAKHIR LIST
        transaksiTerakhirItems = new ArrayList<>();
        for (int i=0; i<5; i++){
            transaksiTerakhirItems.add(new TransaksiTerakhirItem(
                    "Rp 1.000.000",
                    "#INV001",
                    "Lunas",
                    "20 Jan 2022 16:50"));
        }

        //SET ADAPTER TRANSAKSI TERAKHIR
        transaksiTerakhirAdapter = new TransaksiTerakhirAdapter(transaksiTerakhirItems, this);
        rvTransaksiTerakhir.setLayoutManager(new LinearLayoutManager(this));
        rvTransaksiTerakhir.setAdapter(transaksiTerakhirAdapter);
        rvTransaksiTerakhir.setHasFixedSize(true);

        //JIKA ADA TRANSAKSI TERAKHIR, TEXT EMPTY HILANG
        if (transaksiTerakhirAdapter.getItemCount() > 0){
            transaksiEmpty.setVisibility(View.GONE);
        }

    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_rangkuman_laporan);
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

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_lihat_detail_produk:
                Intent detailProduk = new Intent(this, PenjualanPerProdukActivity.class);
                startActivity(detailProduk);
                break;
            case R.id.btn_lihat_detail_kategori:
                Intent detailKategori = new Intent(this, PenjualanPerKategoriActivity.class);
                startActivity(detailKategori);
                break;
            case R.id.btn_lihat_detail_tipe:
                Intent detailTipe = new Intent(this, PenjualanPerTipeActivity.class);
                startActivity(detailTipe);
                break;
            case R.id.btn_lihat_detail_total_transaksi:
            case R.id.btn_lihat_detail_transaksi_terakhir:
                Intent detailRiwayatTransaksi = new Intent(this, DetailRiwayatTransaksiActivity.class);
                startActivity(detailRiwayatTransaksi);
                break;
        }
    }
}