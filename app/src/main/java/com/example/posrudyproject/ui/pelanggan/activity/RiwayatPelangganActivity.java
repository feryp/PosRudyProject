package com.example.posrudyproject.ui.pelanggan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.pelanggan.adapter.PelangganAdapter;
import com.example.posrudyproject.ui.pelanggan.adapter.RiwayatPelangganAdapter;
import com.example.posrudyproject.ui.pelanggan.model.PelangganItem;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class RiwayatPelangganActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    RecyclerView rvRiwayat;
    RiwayatPelangganAdapter adapter;
    List<PelangganItem> pelangganItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_pelanggan);

        //INIT VIEW
        initComponent();

        initToolbar();

        //Riwayat Pelanggan List
        pelangganItems = new ArrayList<>();
        for (int i=0; i<100; i++){
            pelangganItems.add(new PelangganItem(
                    "Ahmad Muzaki",
                    "(0812364589)",
                    "ahmad.muzaki@gmail.com",
                    "Jln. Kelapa Dua, Link. Sukajadi, Gg. Cendana 2",
                    5,
                    "11 Nov 2021",
                    "Rp. 50.000"
            ));
        }

        //Setup adapter
        adapter = new RiwayatPelangganAdapter(pelangganItems, this);
        rvRiwayat.setLayoutManager(new LinearLayoutManager(this));
        rvRiwayat.setAdapter(adapter);
        rvRiwayat.setHasFixedSize(true);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        //init
        mToolbar = findViewById(R.id.toolbar_riwayat_pelanggan);
        rvRiwayat = findViewById(R.id.rv_riwayat_pelanggan);
    }
}