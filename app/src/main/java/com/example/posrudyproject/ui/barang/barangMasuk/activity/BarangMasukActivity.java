package com.example.posrudyproject.ui.barang.barangMasuk.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.barang.barangMasuk.adapter.BarangMasukAdapter;
import com.example.posrudyproject.ui.barang.barangMasuk.model.BarangMasukItem;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class BarangMasukActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    ConstraintLayout layoutEmpty;
    SearchView cariBarang;
    RecyclerView rvBarangMasuk;
    BarangMasukAdapter adapter;
    List<BarangMasukItem> barangMasukItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang_masuk);

        //INIT VIEW
        initComponent();

        initToolbar();

        //Barang Masuk List
        barangMasukItems = new ArrayList<>();
        for (int i=0; i<50; i++){
            barangMasukItems.add(new BarangMasukItem(
                    "CUTLINE",
                    "SP633846-0011",
                    "Mandarin Fade/Coral Matte - RP Optics Multilaser Red",
                    "10-08-2021, 15:55",
                    "200 Pcs"
            ));
        }

        //Setup adapter
        adapter = new BarangMasukAdapter(barangMasukItems, this);
        rvBarangMasuk.setLayoutManager(new LinearLayoutManager(this));
        rvBarangMasuk.setAdapter(adapter);
        rvBarangMasuk.setHasFixedSize(true);

        //Jika ada list item ilustrasi hilang
        if (adapter.getItemCount() > 0){
            layoutEmpty.setVisibility(View.GONE);
        }

    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_barang_masuk);
        layoutEmpty = findViewById(R.id.layout_ilustrasi_empty_barang_masuk);
        cariBarang = findViewById(R.id.search_barang_masuk);
        rvBarangMasuk = findViewById(R.id.rv_barang_masuk);
    }
}