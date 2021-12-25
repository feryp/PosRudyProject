package com.example.posrudyproject.ui.penyimpanan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.pelanggan.activity.TambahPelangganActivity;
import com.example.posrudyproject.ui.penyimpanan.adapter.KategoriPenyimpananAdapter;
import com.example.posrudyproject.ui.penyimpanan.adapter.ProdukTersediaAdapter;
import com.example.posrudyproject.ui.penyimpanan.model.KategoriPenyimpananItem;
import com.example.posrudyproject.ui.penyimpanan.model.ProdukTersediaItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class PenyimpananActivity extends AppCompatActivity implements OnItemClickListener, View.OnClickListener{

    MaterialToolbar mToolbar;
    MaterialButton btnPemindahanBarang;
    RecyclerView rvKategoriPenyimpanan, rvProdukTersedia;
    KategoriPenyimpananAdapter katPenyimpananAdapter;
    List<KategoriPenyimpananItem> kategoriPenyimpananItems;
    ProdukTersediaAdapter proTersediaAdapter;
    List<ProdukTersediaItem> produkTersediaItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyimpanan);

        //INIT VIEW
        initComponent();

        initToolbar();

        //Kategori Penyimpanan
        kategoriPenyimpananItems = new ArrayList<>();
        kategoriPenyimpananItems.add(new KategoriPenyimpananItem(0, "100","Barang Masuk"));
        kategoriPenyimpananItems.add(new KategoriPenyimpananItem(1, "100","Barang Keluar"));
        kategoriPenyimpananItems.add(new KategoriPenyimpananItem(2, "100","Barang Pindah"));

        //Setup Adapter Kategori Penyimpanan
        katPenyimpananAdapter = new KategoriPenyimpananAdapter(kategoriPenyimpananItems, this);
        rvKategoriPenyimpanan.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvKategoriPenyimpanan.setAdapter(katPenyimpananAdapter);
        rvKategoriPenyimpanan.setHasFixedSize(true);


        //Produk Tersedia
        produkTersediaItems = new ArrayList<>();
        for (int i=0; i<10; i++){
            produkTersediaItems.add(new ProdukTersediaItem(
                    R.drawable.im_example,
                    "CUTLINE",
                    "SP633846-0011",
                    "Mandarin Fade/Coral Matte - RP Optics Multilaser Red",
                    "Rp 1.000.000",
                    "100 Pcs"
            ));
        }

        //Setup Adapter Produk Tersedia
        proTersediaAdapter = new ProdukTersediaAdapter(produkTersediaItems, this);
        rvProdukTersedia.setLayoutManager(new LinearLayoutManager(this));
        rvProdukTersedia.setAdapter(proTersediaAdapter);
        rvProdukTersedia.setHasFixedSize(true);

        //SET LISTENER
        btnPemindahanBarang.setOnClickListener(this);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_penyimpanan);
        btnPemindahanBarang = findViewById(R.id.btn_pemindahan_barang);
        rvKategoriPenyimpanan = findViewById(R.id.rv_kategori_penyimpanan);
        rvProdukTersedia = findViewById(R.id.rv_produk_tersedia);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Toast.makeText(this, "Pilih " + kategoriPenyimpananItems.get(position).getKatBarangPenyimpanan(), Toast.LENGTH_SHORT).show();
        Intent pilihKategori = new Intent(this, RiwayatPenyimpananActivity.class);
        startActivity(pilihKategori);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, "Pimindahan Barang", Toast.LENGTH_SHORT).show();
        Intent pemindahanBarang = new Intent(this, PemindahanBarangActivity.class);
        startActivity(pemindahanBarang);
    }
}