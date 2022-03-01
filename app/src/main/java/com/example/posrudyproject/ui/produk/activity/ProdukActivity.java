package com.example.posrudyproject.ui.produk.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.filter.fragment.BotSheetFilterProdukFragment;
import com.example.posrudyproject.ui.filter.fragment.BotSheetFilterTipeFragment;
import com.example.posrudyproject.ui.penyimpanan.adapter.ProdukTersediaAdapter;
import com.example.posrudyproject.ui.penyimpanan.model.ProdukTersediaItem;
import com.example.posrudyproject.ui.produk.adapter.ProdukAdapter;
import com.example.posrudyproject.ui.produk.model.ProdukItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class ProdukActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialToolbar mToolbar;
    SearchView searchView;
    AppCompatImageButton btnBarcode;
    RecyclerView rvProduk;
    MaterialButton btnCustomBarang;

    List<ProdukItem> produkItems;
    ProdukAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk);

        //INIT VIEW
        initComponent();

        initToolbar();

        //Produk Tersedia
        produkItems = new ArrayList<>();
        for (int i=0; i<10; i++){
            produkItems.add(new ProdukItem(
                    "",
                    "CUTLINE",
                    "SP633846-0011",
                    "Mandarin Fade/Coral Matte - RP Optics Multilaser Red",
                    "100 Pcs"
            ));
        }

        //Setup Adapter Produk Tersedia
        adapter = new ProdukAdapter(produkItems, this);
        rvProduk.setLayoutManager(new LinearLayoutManager(this));
        rvProduk.setAdapter(adapter);
        rvProduk.setHasFixedSize(true);

        //SET LISTENER BUTTON
        btnCustomBarang.setOnClickListener(this);
        btnBarcode.setOnClickListener(this);
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_produk);
        searchView = findViewById(R.id.search_barang_produk);
        btnBarcode = findViewById(R.id.btn_barcode_produk);
        btnCustomBarang = findViewById(R.id.btn_custom_barang);
        rvProduk = findViewById(R.id.rv_produk);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_filter){
                BotSheetFilterProdukFragment botSheetProduk = new BotSheetFilterProdukFragment();
                botSheetProduk.setCancelable(false);
                botSheetProduk.show(getSupportFragmentManager(), botSheetProduk.getTag());
                return true;
            }
            return false;
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if (view == btnCustomBarang){
           Intent customBarang = new Intent(this, CustomBarangActivity.class);
           startActivity(customBarang);
        } else if (view == btnBarcode){
            //FUNCTION
        }
    }
}