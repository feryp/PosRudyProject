package com.example.posrudyproject.ui.penjualan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.keranjang.model.KeranjangItem;
import com.example.posrudyproject.ui.penjualan.adapter.StrukPenjualanAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class StrukPenjualanActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    RecyclerView rvPesananStruk;
    StrukPenjualanAdapter adapter;
    List<KeranjangItem> keranjangItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_struk_penjualan);

        //INIT VIEW
        initComponent();

        initToolbar();

        //Transaksi List
        keranjangItems = new ArrayList<>();
        for (int i=0; i<5; i++){
            keranjangItems.add(new KeranjangItem(
                    "",
                    "CUTLINE",
                    "6456474564",
                    "SP633846-0011",
                    "Mandarin Fade/Coral Matte - RP Optics Multilaser Red",
                    "Rp 1.000.000",
                    "2",
                    "Rp. 2.000.000",
                    ""
            ));
        }

        //Setup adapter
        adapter = new StrukPenjualanAdapter(keranjangItems, this);
        rvPesananStruk.setLayoutManager(new LinearLayoutManager(this));
        rvPesananStruk.setAdapter(adapter);
        rvPesananStruk.setHasFixedSize(true);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_print){
                //FUNCTION INTENT
                return true;
            }

            return false;
        });
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_struk);
        rvPesananStruk = findViewById(R.id.rv_item_pesanan_struk);
    }
}