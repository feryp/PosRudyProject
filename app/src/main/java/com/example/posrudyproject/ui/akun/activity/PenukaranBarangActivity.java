package com.example.posrudyproject.ui.akun.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.akun.adapter.PenukaranBarangAdapter;
import com.example.posrudyproject.ui.akun.model.PenukaranBarangItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class PenukaranBarangActivity extends AppCompatActivity implements View.OnClickListener{

    MaterialToolbar mToolbar;
    RecyclerView rvPenukaranBarang;
    TextInputEditText etAlasanPenukaran;
    AppCompatTextView tvTotalHarga;
    MaterialButton btnTukar;

    List<PenukaranBarangItem> penukaranBarangItems;
    PenukaranBarangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penukaran_barang);

        //INIT VIEW
        initComponent();

        initToolbar();

        //Penukaran Barang List
        penukaranBarangItems = new ArrayList<>();
        for (int i=0; i<10; i++){
            penukaranBarangItems.add(new PenukaranBarangItem(
                    i % 2 == 0,
                    "CUTLINE",
                    "SP633846-0011",
                    "Mandarin Fade/Coral Matte - RP Optics Multilaser Red",
                    "1"
            ));
        }

        //Setup Adapter Produk Tersedia
        adapter = new PenukaranBarangAdapter(penukaranBarangItems, this);
        rvPenukaranBarang.setLayoutManager(new LinearLayoutManager(this));
        rvPenukaranBarang.setAdapter(adapter);
        rvPenukaranBarang.setHasFixedSize(true);

        //SET LISTENER
        btnTukar.setOnClickListener(this);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_penukaran_barang);
        rvPenukaranBarang = findViewById(R.id.rv_list_penukaran_barang);
        etAlasanPenukaran = findViewById(R.id.et_alasan_penukaran);
        tvTotalHarga = findViewById(R.id.tv_total_harga_penukaran_barang);
        btnTukar = findViewById(R.id.btn_tukar_barang);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, "Tukar Barang", Toast.LENGTH_SHORT).show();
    }
}