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
import com.example.posrudyproject.ui.laporan.adapter.RiwayatTransaksiAdapter;
import com.example.posrudyproject.ui.laporan.adapter.RiwayatTransaksiPelangganAdapter;
import com.example.posrudyproject.ui.laporan.model.RiwayatTransaksiItem;
import com.example.posrudyproject.ui.laporan.model.SubRiwayatTransaksiItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.ArrayList;
import java.util.List;

public class DetailRiwayatTransaksiActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    RecyclerView rvRiwayatTransaksi;
    private ConstraintLayout btnPilihPeriode;
    private AppCompatTextView mSelectedPeriod;

    RiwayatTransaksiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat_transaksi);

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

        //Setup Adapter
        adapter = new RiwayatTransaksiAdapter(buildItemList(), this);
        rvRiwayatTransaksi.setLayoutManager(new LinearLayoutManager(this));
        rvRiwayatTransaksi.setAdapter(adapter);
        rvRiwayatTransaksi.setHasFixedSize(true);
    }


    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_detail_riwayat_transaksi_laporan);
        btnPilihPeriode = findViewById(R.id.btn_pilih_periode);
        mSelectedPeriod = findViewById(R.id.tv_selected_period);
        rvRiwayatTransaksi = findViewById(R.id.rv_riwayat_transaksi_laporan);
    }

    private List<RiwayatTransaksiItem> buildItemList() {
        List<RiwayatTransaksiItem> riwayatTransaksiItems = new ArrayList<>();
        for (int i=0; i<15; i++){
            RiwayatTransaksiItem item = new RiwayatTransaksiItem(
                    "Sabtu, 07 Aug 2021",
                    "Rp 9.000.000", buildSubItemList());
            riwayatTransaksiItems.add(item);
        }
        return riwayatTransaksiItems;
    }

    private List<SubRiwayatTransaksiItem> buildSubItemList() {
        List<SubRiwayatTransaksiItem> subRiwayatTransaksiItems = new ArrayList<>();
        for (int i=0; i<3; i++){
            SubRiwayatTransaksiItem subItem = new SubRiwayatTransaksiItem(
                    "Rp 3.000.000",
                    "#INV001",
                    "Tunai",
                    "10:09");
            subRiwayatTransaksiItems.add(subItem);
        }
        return subRiwayatTransaksiItems;
    }
}