package com.example.posrudyproject.ui.penjualan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.filter.fragment.BotSheetFilterTipeFragment;
import com.example.posrudyproject.ui.keranjang.activity.KeranjangActivity;
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
                botSheetTipe.show(getSupportFragmentManager(), botSheetTipe.getTag());
                return true;
            }
            return false;
        });
    }


    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_penjualan);
        searchView = findViewById(R.id.search_barang);
        btnBarcode = findViewById(R.id.btn_barcode);
        btnMasukKeranjang = findViewById(R.id.btn_masuk_keranjang);
        rvPenjualan = findViewById(R.id.rv_penjualan);
    }

    @Override
    public void onClick(View view) {
        Intent masukKeranjang = new Intent(this, KeranjangActivity.class);
        startActivity(masukKeranjang);
    }
}