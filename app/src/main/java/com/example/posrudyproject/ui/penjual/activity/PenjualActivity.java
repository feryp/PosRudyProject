package com.example.posrudyproject.ui.penjual.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.keranjang.activity.KeranjangActivity;
import com.example.posrudyproject.ui.pelanggan.activity.TambahPelangganActivity;
import com.example.posrudyproject.ui.penjual.adapter.PenjualAdapter;
import com.example.posrudyproject.ui.penjual.fragment.BotSheetTokoTujuanFragment;
import com.example.posrudyproject.ui.penjual.model.PenjualItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class PenjualActivity extends AppCompatActivity implements OnItemClickListener, View.OnClickListener {

    MaterialToolbar mToolbar;
    SearchView searchView;
    RecyclerView rvPenjual;
    MaterialButton btnPindahPenjual;
    PenjualAdapter adapter;
    List<PenjualItem> penjualItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjual);

        //INIT VIEW
        initComponent();

        initToolbar();

        //SET LISTENER
        btnPindahPenjual.setOnClickListener(this);

        //Penjual List
        penjualItems = new ArrayList<>();
        penjualItems.add(new PenjualItem(R.drawable.im_foto,"Alex Parkinson","Penjual"));
        penjualItems.add(new PenjualItem(R.drawable.im_foto,"John Doe","Penjual"));
        penjualItems.add(new PenjualItem(R.drawable.im_foto,"Udin","Penjual"));
        penjualItems.add(new PenjualItem(R.drawable.im_foto,"Syamsul Bahri","Penjual"));
        penjualItems.add(new PenjualItem(R.drawable.im_foto,"Hari Awaludin","Penjual"));
        penjualItems.add(new PenjualItem(R.drawable.im_foto,"Sulaeman","Penjual"));
        penjualItems.add(new PenjualItem(0,"Felix Parker","Penjual"));

        //Setup adapter
        adapter = new PenjualAdapter(penjualItems, this);
        rvPenjual.setLayoutManager(new GridLayoutManager(this, 2));
        rvPenjual.setAdapter(adapter);
        rvPenjual.setHasFixedSize(true);


    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        //init
        mToolbar = findViewById(R.id.toolbar_penjual);
        searchView = findViewById(R.id.search_penjual);
        rvPenjual = findViewById(R.id.rv_penjual);
        btnPindahPenjual = findViewById(R.id.btn_pindahkan_penjual);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Toast.makeText(this, "Pilih " + penjualItems.get(position).getNamaPenjual(), Toast.LENGTH_SHORT).show();
        Intent pilihPenjual = new Intent(this, KeranjangActivity.class);
        startActivity(pilihPenjual);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, "Pindahkan Penjual", Toast.LENGTH_SHORT).show();
        //function pindahkan karyawan dengan action klik salah satu penjual/karyawan dahulu,
        //lalu pilih toko tujuan.

        BotSheetTokoTujuanFragment botSheetToko = new BotSheetTokoTujuanFragment();
        botSheetToko.show(getSupportFragmentManager(), botSheetToko.getTag());
    }
}