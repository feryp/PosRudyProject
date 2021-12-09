package com.example.posrudyproject.ui.penjualan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.keranjang.model.KeranjangItem;
import com.example.posrudyproject.ui.penjualan.adapter.TransaksiSuksesAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class TransaksiSuksesActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialToolbar mToolbar;
    MaterialButton btnCetakStruk, btnBuatPesananBaru;
    RecyclerView rvTransaksi;
    TransaksiSuksesAdapter adapter;
    List<KeranjangItem> keranjangItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_sukses);

        //INIT VIEW
        initComponent();

        initToolbar();

        //SET LISTENER
        btnCetakStruk.setOnClickListener(this);
        btnBuatPesananBaru.setOnClickListener(this);

        //Transaksi List
        keranjangItems = new ArrayList<>();
        for (int i=0; i<5; i++){
            keranjangItems.add(new KeranjangItem(
                    0,
                    "CUTLINE",
                    "SP633846-0011",
                    "Mandarin Fade/Coral Matte - RP Optics Multilaser Red",
                    "Rp 1.000.000",
                    "2",
                    "Rp. 2.000.000",
                    ""
            ));
        }

        //Setup adapter
        adapter = new TransaksiSuksesAdapter(keranjangItems, this);
        rvTransaksi.setLayoutManager(new LinearLayoutManager(this));
        rvTransaksi.setAdapter(adapter);
        rvTransaksi.setHasFixedSize(true);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_transaksi_sukses);
        btnCetakStruk = findViewById(R.id.btn_cetak_struk);
        btnBuatPesananBaru = findViewById(R.id.btn_buat_pesanan_baru);
        rvTransaksi = findViewById(R.id.rv_item_pesanan_transaksi_selesai);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_cetak_struk:
                Intent cetakStruk = new Intent(this, StrukPenjualanActivity.class);
                startActivity(cetakStruk);
                break;

            case R.id.btn_buat_pesanan_baru:
                Intent buatPesananBaru = new Intent(this, KategoriActivity.class);
                startActivity(buatPesananBaru);
                break;
        }
    }
}