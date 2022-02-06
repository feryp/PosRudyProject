package com.example.posrudyproject.ui.laporan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.View;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.adapter.RiwayatTransaksiPenjualAdapter;
import com.example.posrudyproject.ui.laporan.model.RiwayatTransaksiPenjualItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.ArrayList;
import java.util.List;

public class DetailLaporanPenjualActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    RecyclerView rvRiwayatTransaksi;
    private ConstraintLayout btnPilihPeriode;
    private AppCompatTextView mSelectedPeriod;

    List<RiwayatTransaksiPenjualItem> items;
    RiwayatTransaksiPenjualAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan_penjual);

        //INIT VIEW
        initComponent();

        initToolbar();

        //Material Date Picker
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Pilih Periode");
        final MaterialDatePicker materialDatePicker = builder.build();

        btnPilihPeriode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                mSelectedPeriod.setText(materialDatePicker.getHeaderText()); //SET PICKER
            }
        });


        // Removes blinks
        ((SimpleItemAnimator) rvRiwayatTransaksi.getItemAnimator()).setSupportsChangeAnimations(false);

        //DATA RIWAYAT TRANSAKSI PENJUAL
        items = new ArrayList<>();
        for (int i=0; i<10; i++){
            items.add(new RiwayatTransaksiPenjualItem(
                    "20 Jan 2022, 16:48",
                    "Rp 4.000.000",
                    "Toko A",
                    "5"
            ));
        }

        //SET ADAPTER
        adapter = new RiwayatTransaksiPenjualAdapter(items, this);
        rvRiwayatTransaksi.setLayoutManager(new LinearLayoutManager(this));
        rvRiwayatTransaksi.setAdapter(adapter);
        rvRiwayatTransaksi.setHasFixedSize(true);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_detail_penjual_laporan);
        btnPilihPeriode = findViewById(R.id.btn_pilih_periode);
        mSelectedPeriod = findViewById(R.id.tv_selected_period);
        rvRiwayatTransaksi = findViewById(R.id.rv_riwayat_transaksi_penjual_laporan);
    }
}