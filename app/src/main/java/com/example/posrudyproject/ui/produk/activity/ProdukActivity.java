package com.example.posrudyproject.ui.produk.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.barcode.ScannerActivity;
import com.example.posrudyproject.ui.filter.fragment.BotSheetFilterProdukFragment;
import com.example.posrudyproject.ui.filter.fragment.BotSheetFilterTipeFragment;
import com.example.posrudyproject.ui.penjualan.activity.PenjualanActivity;
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
                    "634343234",
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
            if (ContextCompat.checkSelfPermission(ProdukActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(ProdukActivity.this, Manifest.permission.CAMERA)){
                    startScan();
                } else {
                    ActivityCompat.requestPermissions(ProdukActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
                }
            } else {
                startScan();
            }
        }
    }

    private void startScan() {
        Intent intent = new Intent(getApplicationContext(), ScannerActivity.class);
        startActivityForResult(intent, 20);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 20){
            if (resultCode == RESULT_OK && data != null){
                String code = data.getStringExtra("result");
                //SET CODE
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startScan();
            } else {
                Toast.makeText(this, "Gagal membuka kamera!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}