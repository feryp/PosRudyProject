package com.example.posrudyproject.ui.penyimpanan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penyimpanan.adapter.BarangPindahAdapter;
import com.example.posrudyproject.ui.penyimpanan.model.BarangPindahItem;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class DetailRiwayatActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    RecyclerView rvBarangPindah;
    BarangPindahAdapter adapter;
    List<BarangPindahItem> barangPindahItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat);

        //INIT VIEW
        initComponent();

        initToolbar();

        //Barang Pindah
        barangPindahItems = new ArrayList<>();
        for (int i=0; i<5; i++){
            barangPindahItems.add(new BarangPindahItem(
                    R.drawable.im_example,
                    "CUTLINE",
                    "SP633846-0011",
                    "Mandarin Fade/Coral Matte - RP Optics Multilaser Red",
                    "5 Pcs"
            ));
        }

        //Setup Adapter Barang Pindah
        adapter = new BarangPindahAdapter(barangPindahItems, this);
        rvBarangPindah.setLayoutManager(new LinearLayoutManager(this));
        rvBarangPindah.setAdapter(adapter);
        rvBarangPindah.setHasFixedSize(true);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_detail_riwayat_barang_pindah);
        rvBarangPindah = findViewById(R.id.rv_detail_barang_pindah);
    }
}