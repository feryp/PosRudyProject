package com.example.posrudyproject.ui.notifikasi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.adapter.RiwayatTransaksiPenjualAdapter;
import com.example.posrudyproject.ui.notifikasi.adapter.NotifikasiAdapter;
import com.example.posrudyproject.ui.notifikasi.model.NotifikasiItem;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class NotifikasiActivity extends AppCompatActivity implements OnItemClickListener {

    MaterialToolbar mToolbar;
    RecyclerView rvNotifikasi;

    List<NotifikasiItem> notifikasiItems;
    NotifikasiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);

        //INIT VIEW
        initComponent();

        initToolbar();

        //NOTIFIKASI LIST
        notifikasiItems = new ArrayList<>();
        for (int i=0; i<100; i++){
            notifikasiItems.add(new NotifikasiItem(
                    "Persediaan Stok",
                    "Barang A sudah mencapai batas minimum, segera restock kembali!",
                    "20-Feb-20212 09:12"
            ));
        }

        //SET ADAPTER
        adapter = new NotifikasiAdapter(notifikasiItems, this);
        rvNotifikasi.setLayoutManager(new LinearLayoutManager(this));
        rvNotifikasi.setAdapter(adapter);
        rvNotifikasi.setHasFixedSize(true);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_notifikasi);
        rvNotifikasi = findViewById(R.id.rv_notifikasi);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Toast.makeText(this, notifikasiItems.get(position).getJudulNotif(), Toast.LENGTH_SHORT).show();
    }
}