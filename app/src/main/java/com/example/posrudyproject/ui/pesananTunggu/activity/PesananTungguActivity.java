package com.example.posrudyproject.ui.pesananTunggu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.pesananTunggu.adapter.PesananTungguAdapter;
import com.example.posrudyproject.ui.pesananTunggu.model.BarangPesananTungguItem;
import com.example.posrudyproject.ui.pesananTunggu.model.PesananTungguItem;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class PesananTungguActivity extends AppCompatActivity implements OnItemClickListener {

    MaterialToolbar mToolbar;
    RecyclerView rvPesananTunggu;
    PesananTungguAdapter pesananTungguAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_tunggu);

        //INIT VIEW
        initComponent();

        initToolbar();


        //Setup Adapter
        pesananTungguAdapter = new PesananTungguAdapter(buildItemList(), this);
        rvPesananTunggu.setLayoutManager(new LinearLayoutManager(this));
        rvPesananTunggu.setAdapter(pesananTungguAdapter);
        rvPesananTunggu.setHasFixedSize(true);
    }

    private List<PesananTungguItem> buildItemList() {
        List<PesananTungguItem> pesananTungguItems = new ArrayList<>();
        for (int i=0; i<20; i++){
            PesananTungguItem item = new PesananTungguItem(
                    "Pesanan Tunggu ",
                    "31 Okt 2021",
                    "Rp 9.503.000",
                    "Masih mencari barang yg ingin dibeli",
                    "Bambang", buildSubItemList());
            pesananTungguItems.add(item);
        }

        return pesananTungguItems;

    }

    private List<BarangPesananTungguItem> buildSubItemList() {
        List<BarangPesananTungguItem> barangPesananTungguItems = new ArrayList<>();
        for (int i=0; i<2; i++){
            BarangPesananTungguItem subItem = new BarangPesananTungguItem(2,"Mandarin Fade/Coral Matte - RP Optics Multilaser Red");
            barangPesananTungguItems.add(subItem);
        }
        return barangPesananTungguItems;
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_delete){
                //FUNCTION DELETE

                return true;
            }

            return false;
        });
    }

    private void initComponent() {
        // init
        mToolbar = findViewById(R.id.toolbar_pesanan_tunggu);
        rvPesananTunggu = findViewById(R.id.rv_pesanan_tunggu);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Toast.makeText(this, "Pilih " + buildItemList().get(position).getNoPesanan(), Toast.LENGTH_SHORT).show();
    }
}