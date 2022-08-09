package com.example.posrudyproject.ui.ubahHarga.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.keranjang.activity.KeranjangActivity;
import com.example.posrudyproject.ui.keranjang.model.KeranjangItem;
import com.example.posrudyproject.ui.ubahHarga.adapter.UbahHargaAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;
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
        Bundle extras = getIntent().getExtras();

        //Barang List
        keranjangItems = new ArrayList<>();
        for (int i = 0; i<((List<KeranjangItem>) extras.getSerializable("itemForBuy")).size(); i++){
            if (Integer.parseInt(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang()) != 0) {
                keranjangItems.add(new KeranjangItem(
                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getImBarang(),
                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getTipeBarang(),
                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getSkuCode(),
                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getArtikelBarang(),
                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getNamaBarang(),
                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getHargaBarang(),
                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getHarga_baru(),
                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getHarga_baru_remark(),
                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang(),
                        String.valueOf(Double.valueOf(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getHarga_baru().replace(".","")) * Double.valueOf(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang())),
                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang()
                ));
            }
        }

        //Setup Adapter
        adapter = new UbahHargaAdapter(keranjangItems, this);
        rvPotonganHarga.setLayoutManager(new LinearLayoutManager(this));
        rvPotonganHarga.setAdapter(adapter);
        rvPotonganHarga.setHasFixedSize(true);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
        btnSimpanHarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent potonganHarga = new Intent(UbahHargaActivity.this, KeranjangActivity.class);
                potonganHarga.putExtra("itemForBuyAfterPotong", (Serializable) keranjangItems);
                startActivity(potonganHarga);
            }
        });
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_potongan_harga);
        btnSimpanHarga = findViewById(R.id.btn_simpan_potongan_harga);
        rvPotonganHarga = findViewById(R.id.rv_potongan_harga);
    }
}