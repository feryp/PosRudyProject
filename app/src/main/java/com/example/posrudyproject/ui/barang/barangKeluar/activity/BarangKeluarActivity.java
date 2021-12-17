package com.example.posrudyproject.ui.barang.barangKeluar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.barang.barangKeluar.adapter.BarangKeluarAdapter;
import com.example.posrudyproject.ui.barang.barangKeluar.model.BarangKeluarItem;
import com.example.posrudyproject.ui.barang.barangMasuk.adapter.BarangMasukAdapter;
import com.example.posrudyproject.ui.barang.barangMasuk.model.BarangMasukItem;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class BarangKeluarActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    ConstraintLayout layoutEmpty;
    SearchView cariBarang;
    RecyclerView rvBarangKeluar;
    BarangKeluarAdapter adapter;
    List<BarangKeluarItem> barangKeluarItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang_keluar);

        //INIT VIEW
        initComponent();

        initToolbar();

        //Barang Keluar List
        barangKeluarItems = new ArrayList<>();
        for (int i=0; i<50; i++){
            barangKeluarItems.add(new BarangKeluarItem(
                    "CUTLINE",
                    "SP633846-0011",
                    "Mandarin Fade/Coral Matte - RP Optics Multilaser Red",
                    "10-08-2021, 15:55",
                    "200 Pcs"
            ));
        }

        //Setup adapter
        adapter = new BarangKeluarAdapter(barangKeluarItems, this);
        rvBarangKeluar.setLayoutManager(new LinearLayoutManager(this));
        rvBarangKeluar.setAdapter(adapter);
        rvBarangKeluar.setHasFixedSize(true);

        //Jika ada list item ilustrasi hilang
        if (adapter.getItemCount() > 0){
            layoutEmpty.setVisibility(View.GONE);
        }
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_barang_keluar);
        layoutEmpty = findViewById(R.id.layout_ilustrasi_empty_barang_keluar);
        cariBarang = findViewById(R.id.search_barang_keluar);
        rvBarangKeluar = findViewById(R.id.rv_barang_keluar);
    }
}