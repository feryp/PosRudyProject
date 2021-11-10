package com.example.posrudyproject.ui.pelanggan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.keranjang.activity.KeranjangActivity;
import com.example.posrudyproject.ui.pelanggan.adapter.PelangganAdapter;
import com.example.posrudyproject.ui.pelanggan.model.PelangganItem;
import com.example.posrudyproject.ui.penjualan.activity.PenjualanActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class PelangganActivity extends AppCompatActivity implements OnItemClickListener, View.OnClickListener {

    MaterialToolbar mToolbar;
    SearchView searchView;
    RecyclerView rvPelanggan;
    MaterialButton btnTambahPelanggan;
    PelangganAdapter adapter;
    List<PelangganItem> pelangganItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelanggan);

        //INIT VIEW
        initComponent();

        initToolbar();

        //SET LISTENER
        btnTambahPelanggan.setOnClickListener(this);

        //Pelanggan List
        pelangganItems = new ArrayList<>();
        for (int i=0; i<100; i++){
            pelangganItems.add(new PelangganItem(
                    "Ahmad Muzaki",
                    "0812364589",
                    "",
                    "",
                    0,
                    "",
                    0
            ));
        }

        //Setup adapter
        adapter = new PelangganAdapter(pelangganItems, this);
        rvPelanggan.setLayoutManager(new LinearLayoutManager(this));
        rvPelanggan.setAdapter(adapter);
        rvPelanggan.setHasFixedSize(true);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_riwayat_pelanggan){
                return true;
            }
            return false;
        });
    }

    private void initComponent() {
        //init
        mToolbar = findViewById(R.id.toolbar_pelanggan);
        searchView = findViewById(R.id.search_pelanggan);
        rvPelanggan = findViewById(R.id.rv_pelanggan);
        btnTambahPelanggan = findViewById(R.id.btn_tambah_pelanggan);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onItemClickListener(View view, int position) {
        switch (view.getId()){
            case R.id.item_pelanggan:
                Toast.makeText(this, "Pilih " + pelangganItems.get(position).getNamaPelanggan(), Toast.LENGTH_SHORT).show();
                Intent tambahPelanggan = new Intent(this, KeranjangActivity.class);
                startActivity(tambahPelanggan);
                break;
            case R.id.btn_edit_pelanggan:
                Toast.makeText(this, "Ubah " + pelangganItems.get(position).getNamaPelanggan(), Toast.LENGTH_SHORT).show();
                Intent editPelanggan = new Intent(this, TambahPelangganActivity.class);
                startActivity(editPelanggan);
                break;
        }

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, "Tambah Pelanggan", Toast.LENGTH_SHORT).show();
        Intent tambahPelanggan = new Intent(this, TambahPelangganActivity.class);
        startActivity(tambahPelanggan);
    }
}