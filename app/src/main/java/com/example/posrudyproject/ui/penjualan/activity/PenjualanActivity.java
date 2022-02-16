package com.example.posrudyproject.ui.penjualan.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.posrudyproject.ui.filter.fragment.BotSheetFilterTipeFragment;
import com.example.posrudyproject.ui.keranjang.activity.KeranjangActivity;
import com.example.posrudyproject.ui.main.MainActivity;
import com.example.posrudyproject.ui.penjualan.adapter.PenjualanAdapter;
import com.example.posrudyproject.ui.penjualan.model.PenjualanItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class PenjualanActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialToolbar mToolbar;
    SearchView searchView;
    AppCompatImageButton btnBarcode;
    MaterialButton btnMasukKeranjang;
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

        //SET LISTENER
        btnBarcode.setOnClickListener(this);
        btnMasukKeranjang.setOnClickListener(this);

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
        rvPenjualan.setHasFixedSize(true);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_filter){
                BotSheetFilterTipeFragment botSheetTipe = new BotSheetFilterTipeFragment();
                botSheetTipe.setCancelable(false);
                botSheetTipe.show(getSupportFragmentManager(), botSheetTipe.getTag());
                return true;
            }
            return false;
        });
    }


    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_penjualan);
        searchView = findViewById(R.id.search_barang_penjualan);
        btnBarcode = findViewById(R.id.btn_barcode_penjualan);
        btnMasukKeranjang = findViewById(R.id.btn_masuk_keranjang);
        rvPenjualan = findViewById(R.id.rv_penjualan);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if (view == btnBarcode){
            if (ContextCompat.checkSelfPermission(PenjualanActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(PenjualanActivity.this, Manifest.permission.CAMERA)){
                    startScan();
                } else {
                    ActivityCompat.requestPermissions(PenjualanActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
                }
            } else {
                startScan();
            }
        } else if (view == btnMasukKeranjang){
            Intent masukKeranjang = new Intent(this, KeranjangActivity.class);
            startActivity(masukKeranjang);
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