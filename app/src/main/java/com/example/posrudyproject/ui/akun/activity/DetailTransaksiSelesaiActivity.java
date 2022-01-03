package com.example.posrudyproject.ui.akun.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.keranjang.model.KeranjangItem;
import com.example.posrudyproject.ui.penjualan.activity.KategoriActivity;
import com.example.posrudyproject.ui.penjualan.activity.StrukPenjualanActivity;
import com.example.posrudyproject.ui.penjualan.adapter.TransaksiSuksesAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class DetailTransaksiSelesaiActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialToolbar mToolbar;
    MaterialButton btnCetakStruk, btnPenukaranBarang;
    RecyclerView rvDetailTransaksi;
    TransaksiSuksesAdapter adapter;
    List<KeranjangItem> keranjangItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi_selesai);

        //INIT VIEW
        initComponent();

        initToolbar();

        //SET LISTENER
        btnCetakStruk.setOnClickListener(this);
        btnPenukaranBarang.setOnClickListener(this);

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
        rvDetailTransaksi.setLayoutManager(new LinearLayoutManager(this));
        rvDetailTransaksi.setAdapter(adapter);
        rvDetailTransaksi.setHasFixedSize(true);


    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_detail_transaksi);
        btnCetakStruk = findViewById(R.id.btn_cetak_struk);
        btnPenukaranBarang = findViewById(R.id.btn_penukaran_barang);
        rvDetailTransaksi = findViewById(R.id.rv_detail_penjualan_transaksi);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_cetak_struk:
                Intent cetakStruk = new Intent(this, StrukPenjualanActivity.class);
                startActivity(cetakStruk);
                break;

            case R.id.btn_penukaran_barang:
                //FUNCTION BUTTON
                break;
        }
    }
}