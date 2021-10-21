package com.example.posrudyproject.ui.penjualan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.keranjang.activity.KeranjangActivity;
import com.example.posrudyproject.ui.penjualan.adapter.KategoriAdapter;
import com.example.posrudyproject.ui.penjualan.model.KategoriItem;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class KategoriActivity extends AppCompatActivity implements OnItemClickListener {

    MaterialToolbar mToolbar;
    SearchView searchView;
    AppCompatImageButton btnBarcode;
    RecyclerView rvKategori;
    KategoriAdapter adapter;
    List<KategoriItem> kategoriItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        //INIT VIEW
        initComponent();

        initToolbar();

        //Kategori List
        kategoriItems = new ArrayList<>();
        kategoriItems.add(new KategoriItem("EYEWEAR"));
        kategoriItems.add(new KategoriItem("HEALMETS"));
        kategoriItems.add(new KategoriItem("SPAREPART"));
        kategoriItems.add(new KategoriItem("LENSES"));
        kategoriItems.add(new KategoriItem("APPAREAL"));
        kategoriItems.add(new KategoriItem("BIKE"));
        kategoriItems.add(new KategoriItem("ACCESSORIES"));

        //Setup Adapter
        adapter = new KategoriAdapter(kategoriItems,this);
        rvKategori.setLayoutManager(new LinearLayoutManager(this));
        rvKategori.setAdapter(adapter);


    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_keranjang) {
                Intent keranjang = new Intent(this, KeranjangActivity.class);
                startActivity(keranjang);
                return true;
            }
            return false;
        });
    }

    private void initComponent() {
        // init
        mToolbar = findViewById(R.id.toolbar_kategori);
        searchView = findViewById(R.id.search_kategori);
        btnBarcode = findViewById(R.id.btn_barcode);
        rvKategori = findViewById(R.id.rv_kategori);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Toast.makeText(this, "Pilih " + kategoriItems.get(position).getNamaKetegori(), Toast.LENGTH_SHORT).show();
        Intent kategori = new Intent(this, PenjualanActivity.class);
        startActivity(kategori);
    }
}