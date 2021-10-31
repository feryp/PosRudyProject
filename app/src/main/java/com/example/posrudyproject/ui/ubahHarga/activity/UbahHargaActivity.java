package com.example.posrudyproject.ui.ubahHarga.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.keranjang.model.KeranjangItem;
import com.example.posrudyproject.ui.ubahHarga.adapter.UbahHargaAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class UbahHargaActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    MaterialButton btnSimpanHarga;
    RecyclerView rvPotonganHarga;
    UbahHargaAdapter adapter;
    List<KeranjangItem> keranjangItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_harga);

        //INIT VIEW
        initComponent();

        initToolbar();

        //Barang List
        keranjangItems = new ArrayList<>();
        for (int i=0; i<20; i++){
            keranjangItems.add(new KeranjangItem(
                    R.drawable.im_example,
                    "CUTLINE",
                    "SP633846-0011",
                    "Mandarin Fade/Coral Matte - RP Optics Multilaser Red",
                    "Rp 1.000.000",
                    "",
                    "",
                    ""
            ));
        }

        //Setup Adapter
        adapter = new UbahHargaAdapter(keranjangItems, this);
        rvPotonganHarga.setLayoutManager(new LinearLayoutManager(this));
        rvPotonganHarga.setAdapter(adapter);
        rvPotonganHarga.setHasFixedSize(true);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_potongan_harga);
        btnSimpanHarga = findViewById(R.id.btn_simpan_potongan_harga);
        rvPotonganHarga = findViewById(R.id.rv_potongan_harga);
    }
}