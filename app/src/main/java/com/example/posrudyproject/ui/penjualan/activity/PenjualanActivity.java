package com.example.posrudyproject.ui.penjualan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penjualan.adapter.PenjualanAdapter;
import com.example.posrudyproject.ui.penjualan.model.PenjualanItem;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class PenjualanActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    SearchView searchView;
    AppCompatImageButton btnBarcode;
    RecyclerView rvPenjualan;
    PenjualanAdapter adapter;
    List<PenjualanItem> penjualanItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);

        //INIT VIEW
        initComponent();

        initToolbar();

        //Penjualan List
        penjualanItems = new ArrayList<>();
        for (int i=0; i<100;i++){
            penjualanItems.add(new PenjualanItem(
                    R.drawable.im_example,
                    "CUTLINE",
                    "SP633846-0011",
                    "Mandarin Fade/Coral Matte - RP Optics Multilaser Red",
                    "Rp 1.000.000",
                    "0"));
        }


        //Setup Adapter
        adapter = new PenjualanAdapter(penjualanItems, this);
        rvPenjualan.setLayoutManager(new GridLayoutManager(this, 2));
        rvPenjualan.setAdapter(adapter);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_filter){
                //FUNCTION INTENT
                return true;
            }
            return false;
        });
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_penjualan);
        searchView = findViewById(R.id.search_barang);
        btnBarcode = findViewById(R.id.btn_barcode);
        rvPenjualan = findViewById(R.id.rv_penjualan);
    }
}