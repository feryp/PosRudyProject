package com.example.posrudyproject.ui.rekapKas.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.rekapKas.adapter.DetailKasAdapter;
import com.example.posrudyproject.ui.rekapKas.model.KasKeluarItem;
import com.example.posrudyproject.ui.rekapKas.model.KasMasukItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class DetailKasActivity extends AppCompatActivity{

    MaterialToolbar mToolbar;
    RecyclerView rvDetailKas;
    ConstraintLayout layoutEmpty;
    MaterialButton btnTutupKasir;
    DetailKasAdapter adapter;
    ArrayList<Object> listKas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kas);

        //INIT VIEW
        initComponent();

        initToolbar();

        //Kas List
        loadData();

        //Setup adapter
        adapter = new DetailKasAdapter(listKas);
        rvDetailKas.setLayoutManager(new LinearLayoutManager(this));
        rvDetailKas.setAdapter(adapter);
        rvDetailKas.setHasFixedSize(true);

        //Jika ada list item ilustrasi hilang
        if (adapter.getItemCount() > 0){
            layoutEmpty.setVisibility(View.GONE);
        }

        btnTutupKasir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTutupKasir();
            }
        });
    }

    void loadData() {
        for (int i=0; i<5; i++){
            KasMasukItem itemMasuk = new KasMasukItem();
            itemMasuk.setNominalKasMasuk("+Rp 1.000.000");
            itemMasuk.setWaktuKasMasuk("Kamis, 20 Jan 20212 | 14:16 ");
            itemMasuk.setPenjualKasMasuk("Alex Parkinson");
            itemMasuk.setCatatanKasMasuk("Barang Terjual");
            listKas.add(itemMasuk);

            KasKeluarItem itemKeluar = new KasKeluarItem();
            itemKeluar.setNominalKasKeluar("-Rp 1.000.000");
            itemKeluar.setWaktuKasKeluar("Jumat, 21 Jan 20212 | 20:26 ");
            itemKeluar.setPenjualKasKeluar("John Doe");
            itemKeluar.setCatatanKasKeluar("Beli Produk");
            listKas.add(itemKeluar);
        }
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_detail_kas);
        rvDetailKas = findViewById(R.id.rv_detail_kas);
        layoutEmpty = findViewById(R.id.layout_ilustrasi_empty_detail_detail_kas);
        btnTutupKasir = findViewById(R.id.btn_tutup_kasir);
    }

    private void dialogTutupKasir() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_tutup_kasir, null);

        //init view
        final TextInputEditText etTunai = mView.findViewById(R.id.et_tunai_diterima);
        final TextInputEditText etNonTunai = mView.findViewById(R.id.et_non_tunai_diterima);
        MaterialButton btnTutupKasir = mView.findViewById(R.id.btn_tutup_kasir_dialog);
        MaterialButton btnCancel = mView.findViewById(R.id.btn_cancel_dialog);

        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_rounded_white);
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        btnTutupKasir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailKasActivity.this, "Tutup Kasir", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

}