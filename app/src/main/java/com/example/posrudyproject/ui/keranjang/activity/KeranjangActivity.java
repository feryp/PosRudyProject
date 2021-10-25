package com.example.posrudyproject.ui.keranjang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.keranjang.adapter.KeranjangAdapter;
import com.example.posrudyproject.ui.keranjang.model.KeranjangItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class KeranjangActivity extends AppCompatActivity implements View.OnClickListener {

    SearchView searchView;
    AppCompatImageButton btnBarcode;
    MaterialToolbar mToolbar;
    MaterialButton btnPotonganHarga, btnSimpanPesanan;
    RecyclerView rvKeranjang;
    KeranjangAdapter adapter;
    List<KeranjangItem> keranjangItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        //INIT VIEW
        initComponent();

        initToolbar();

        //SET LISTENER
        btnPotonganHarga.setOnClickListener(this);
        btnSimpanPesanan.setOnClickListener(this);

        //SETTING BUTTON
        btnPotonganHarga.setPaintFlags(btnPotonganHarga.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btnSimpanPesanan.setPaintFlags(btnSimpanPesanan.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        //Keranjang List
        keranjangItems = new ArrayList<>();
        for (int i=0; i<5; i++){
            keranjangItems.add(new KeranjangItem(
                    R.drawable.im_example,
                    "CUTLINE",
                    "SP633846-0011",
                    "Mandarin Fade/Coral Matte - RP Optics Multilaser Red",
                    "Rp 1.000.000",
                    "2",
                    "Rp 2.000.000",
                    "2"
            ));
        }

        //Setup Adapter
        adapter = new KeranjangAdapter(keranjangItems, this);
        rvKeranjang.setLayoutManager(new LinearLayoutManager(this));
        rvKeranjang.setAdapter(adapter);
        rvKeranjang.setHasFixedSize(true);

    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_filter){

                return true;
            }

            return false;
        });
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_keranjang);
        searchView = findViewById(R.id.search_barang);
        btnBarcode = findViewById(R.id.btn_barcode);
        btnPotonganHarga = findViewById(R.id.btn_potongan_harga);
        btnSimpanPesanan = findViewById(R.id.btn_simpan_pesanan);
        rvKeranjang = findViewById(R.id.rv_keranjang);
    }

    @Override
    public void onClick(View view) {

    }
}